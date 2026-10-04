package com.example.bookstore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

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
        ApplicationModules.of(BookStoreApplication.class).verify();
    }

}
