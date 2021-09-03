package gr.codelearn.eshop.domain;

import gr.codelearn.eshop.base.DBConnectionPool;
import gr.codelearn.eshop.base.extension.DatabaseManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A customer")
class CustomerTest {

    @Test
    @DisplayName("with no email should not be possible")
    void createCustomerWithNoEmailShouldNotBePossible(){
        System.out.println("createCustomerWithNoEmailShouldNotBePossible");
        assertThrows(NullPointerException.class, () -> Customer.builder(null, CustomerCategory.BUSINESS).build(), "The customer creation did not throw a null pointer exception");
    }

    @Test
    @DisplayName("with no customer category should not be possible")
    void createCustomerWithNoCustomerCategoryShouldNotBePossible(){
        System.out.println("createCustomerWithNoCustomerCategoryShouldNotBePossible");
        assertThrows(NullPointerException.class, () -> Customer.builder("john@gmail.com", null).build(), "The customer creation did not throw a null pointer exception");
    }

    @Test
    @DisplayName("with all values inserted should be possible")
    void createCustomerWithAllValues(){
        System.out.println("createCustomerWithAllValues");
        assertDoesNotThrow(() -> Customer.builder("john@gmail.com", CustomerCategory.GOVERNMENT).setFirstname("John").setLastname("D.").setAddress("JUnit 53").setAge(34).build());
    }

}