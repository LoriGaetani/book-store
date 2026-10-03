/**
 * <b>Order</b> — ciclo di vita dell'ordine del cliente: composizione, piazzamento,
 * pagamento, spedizione, annullamento.
 *
 * <p>È il <em>Core Domain</em> del sistema: qui risiede la complessità di business
 * (invarianti dell'aggregato {@code Order}, calcolo del totale, transizioni di stato).
 *
 * <p><b>Confini:</b> questo package radice contiene <em>solo</em> ciò che gli altri moduli
 * devono vedere — oggi il solo evento di dominio. Il modello di dominio sta in
 * {@code order.domain} e l'orchestrazione dei casi d'uso in {@code order.application}:
 * entrambi sono interni e inaccessibili dagli altri moduli.
 *
 * <p><b>Integrazione:</b> comunica verso l'esterno pubblicando {@code OrderPlacedEvent}
 * (asincrono). Non espone né il repository né le entità di dominio.
 */
@ApplicationModule(
        displayName = "Order"

        // Da attivare allo Step 7, quando `catalog` esisterà e `order` ne userà l'API
        // pubblica per ottenere il prezzo di listino:
        // Serve a dire a Modulith che il test di verifica delle dipendenze deve controllare che
        // Order posso dipendere solo da Catalog. Senza controlla solo la presenza di cicli e accesso
        // a package interni altrui.
        //   allowedDependencies = { "catalog" }
)
package com.example.bookstore.order;

import org.springframework.modulith.ApplicationModule;
