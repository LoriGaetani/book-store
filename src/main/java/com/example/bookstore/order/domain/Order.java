package com.example.bookstore.order.domain;

import com.example.bookstore.order.OrderPlacedEvent;
import jakarta.persistence.*;
import org.hibernate.annotations.Audited;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/*
Il punto di partenza non è ricopiare una lista di valori, ma capire cosa dell'oggetto di dominio deve
essere sempre vero in ogni momento.
Mi devo chiedere per ogni campo quali valori sono possibili.
Per ogni operazione che posso fare sull'oggetto, devo chiedermi se l'operazione
può portare l'oggetto in uno stato non valido.

Per proteggere un aggregato come questo dobbiamo proteggere: costruttore, collezioni e setter.
Altrimenti chiunque può creare un oggetto non valido e poi modificarlo in modi che lo rendono non valido.
A differenza di un oggetto come Money che è immutabile e quindi nasce in uno stato valido e basta, qua
devo proteggerlo. Si possono impostare regole strutturali (un campo non null), ma servono anche
regole di transizione (Es. non posso pagare un ordine annullato, se è consegnato non si può spedire,...).
 */

/*
Quando definisco regole di validazione devo chiedermi se la regole infrange una regola di business che
qualcuno andrà a catchare, allora devo lanciare un'eccezione. Se invece devo controllare che un valore
non sia null va bene una IllegalArgumentException della JVM perchè nessuno si metterà a catcharlo.
 */

//Facendo eretidare Order da AbstractAggregateRoot stiamo creando una dipendenza del dominio verso l'infrastruttura, ma
//è comunque accettabile in questo caso.
@Entity
@Table(name = "orders")
public class Order extends AbstractAggregateRoot<Order> {
    @EmbeddedId
    private OrderId id;
    @Embedded
    private CustomerId customerId;
    @Embedded
    private Address address;
    private OrderStatus status;
    @ElementCollection
    @CollectionTable(
            name = "order_items",
            joinColumns = @JoinColumn(name = "order_id")
    )
    private List<OrderItem> items;
    @Embedded
    private Money total;

    /*
    Ho due modi possibile di creare l'oggetto Order. O lo creo vuoto con sono cliente e indirizzo e poi chiamo addItem per aggiungere gli item.
    Questo da il vantaggio che posso controllare quello che arriva dentro l'oggetto, calcolare il totale di volta e volta e poter controllare
    che lo stato sia sempre coerente.
    Se invece passassi gli item nel costruttore li crea chi chiama.
     */

    public Order(CustomerId customerId, Address address/*, List<OrderItem> items*/,String currency) {
        Assert.notNull(customerId, "customerId must not be null");
        Assert.notNull(address, "address must not be null");
        //Assert.notEmpty(items, "items must not be empty");
        this.id = new OrderId();
        this.customerId = customerId;
        this.address = address;
        //this.status = status; non è un parametro. Quando lo creo deve sempre essere CREATED
        this.status = OrderStatus.DRAFT;
        //this.items = items; se facessi così, chiunque potrebbe modificare la lista items dall'esterno dell'oggetto Order, quindi devo fare una copia della lista.
        //this.items = List.copyOf(items); // creo una copia della lista, così chiunque modifichi la lista originale non modifica l'oggetto Order.
        //Anche facendo così sarebbe sbagliato. L'unico modo per proteggere la lista è non esporla mai.
        // Quindi non devo avere un getter per items, ma solo metodi che permettono di aggiungere o rimuovere item, così posso controllare le regole di business.
        this.items = new ArrayList<>();
        //this.total = total; Anche il total non è un parametro, ma deve essere calcolato a partire dagli items. Se lo facessi così, chiunque potrebbe passare un total sbagliato e bypassare le regole di business.
        //this.total = calculateTotal(items);
        this.total = Money.zeroValue(currency); //used because this costructor is used to build an empty object
    }

    /*
     se mettessi un costruttore vuoto come questo pubblico, sarebbe possibile per chiunque creare
    un oggetto Order con tutti campi nulle bypassare le regole di sopra.
     */
    Order() {
    }

    /*//Not used because the object is costructed as empty and then OrderItems are added later.
    private Money calculateTotal(List<OrderItem> items) {
        BigDecimal total =  BigDecimal.ZERO;
        for (OrderItem item : items) {
            total = total.add(item.total().amount());
        }
        return new Money(total, items.get(0).unitPrice.currency());
    }
     */

    /*
    quando aggiungo un getter pubblico mi devo chiedere se effettivamente serve agli altri per
    leggere e fare cose. Se devono fare dei calcoli mi devo chiedere se non dovrebbe essere un metodo di Order
     */
    public Money getTotal() {
        return total;
    }

    // è un metodo dell'oggetto Order. Non di un Service o altro.
    public void addOrderItem(BookId bookId, int quantity, Money unitPrice) {
        //prima di accettare una transazione devo valutare la validità.
        ensureStatusIs(OrderStatus.DRAFT, OrderOperations.ADD_ITEM);

        OrderItem newOrderItem = new OrderItem(bookId, quantity, unitPrice);
        this.items.add(
                newOrderItem
        );
        this.total = this.total.add(newOrderItem.total());
    }

    private void ensureStatusIs(OrderStatus required, OrderOperations operation) {
        if (this.status != required) {
            throw new OrderStatusException(this.id, this.status, operation);
        }
    }

    //l'evento lo deve generare l'aggregato e basta.
    public void  place() {
        //Anche questa è una transazione di stato e deve essere validata. Anche perchè questo metodo può
        //essere chiamato da chiunque.
        ensureStatusIs(OrderStatus.DRAFT, OrderOperations.PLACE);
        if (this.items.isEmpty()) {
            throw new EmptyOrderException(this.id);
        }
        this.status = OrderStatus.CREATED;

        //metodo di AbstractAggregateRoot che fa tutto dietro le quinte.
        registerEvent(new OrderPlacedEvent(
                this.id.id(),
                //Questo permette di creare una stringa immutabile che chi riceve l'oggetto non può modificare.
                items.stream().map(OrderItem::mapToOrderLine).toList()
        ));
    }

}
















