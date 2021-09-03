package gr.codelearn.eshop.base;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

// dummy, for demonstration purposes only
public abstract class DBConnectionPool {
    @BeforeAll
    static void beforeAll(){
        System.out.println("hello");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("bye");
    }

    @BeforeEach
    void beforeEach(){
        System.out.println("started method execution");
    }

    @AfterEach
    void afterEach(){
        System.out.println("finished method execution");
    }
}
