/**
 * <b>Catalog</b> — la vetrina: quali libri sono in vendita e a che prezzo di listino.
 *
 * <p>Sottodominio di <em>supporto</em>: l'aggregato {@code Book} ha poche invarianti e
 * resta volutamente semplice. Lo sforzo di modellazione va investito nel Core Domain
 * ({@code order}), non qui.
 *
 * <p><b>Confini:</b> espone agli altri moduli una sola interfaccia pubblica, usata da
 * {@code order} per ottenere il prezzo corrente di un libro. L'aggregato {@code Book} e
 * il suo repository restano interni.
 *
 * <p><b>Nota:</b> modulo ancora da implementare (Task T5 / Step 7).
 */
@ApplicationModule(
        displayName = "Catalog"

        // Catalog è una foglia: non deve dipendere da nessun altro modulo.
        // Da attivare quando il modulo avrà contenuto:
        //
        //   allowedDependencies = { }
)
package com.example.bookstore.catalog;

import org.springframework.modulith.ApplicationModule;
