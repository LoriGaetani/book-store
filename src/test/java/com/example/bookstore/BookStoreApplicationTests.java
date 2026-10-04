package com.example.bookstore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class BookStoreApplicationTests {

    @Test
    void contextLoads() {
    }

    /*
    Se da un modulo provo a introdurre una dipendenza interna di un altro modulo il test non passa.
     */
    @Test
    void verifiesModularStructure() {
        ApplicationModules modules =
                ApplicationModules.of(BookStoreApplication.class);

        modules.verify();

        //Genera automaticamente la documentazione in build/spring-modulith-docs
        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml();
    }

}
