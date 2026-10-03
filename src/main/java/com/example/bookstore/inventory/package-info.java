/**
 * <b>Inventory</b> — il magazzino: quante copie fisiche di ogni libro sono disponibili,
 * e come vengono scalate quando un ordine viene piazzato.
 *
 * <p>Sottodominio di supporto, ma con un'invariante critica: una giacenza non può
 * scendere sotto zero. L'aggregato {@code Stock} la protegge sollevando
 * {@code OutOfStockDomainException}.
 *
 * <p><b>Integrazione:</b> reagisce in modo <em>asincrono</em> a {@code OrderPlacedEvent}
 * tramite {@code @ApplicationModuleListener}. Non chiama mai i servizi di {@code order}
 * e non conosce l'aggregato {@code Order}: l'unico tipo che importa da quel modulo è
 * l'evento stesso.
 *
 * <p><b>Nota:</b> modulo ancora da implementare (Task T2 / Step 6).
 */
@ApplicationModule(
        displayName = "Inventory"

        // Attenzione: ascoltare un evento di `order` È una dipendenza verso `order`
        // (il tipo dell'evento viene importato). Quando dichiarerai l'elenco, dovrà
        // permettere l'accesso alla sola porzione pubblicata di `order`, non al modulo
        // intero — vedi @NamedInterface. Da affrontare allo Step 8.
)
package com.example.bookstore.inventory;

import org.springframework.modulith.ApplicationModule;
