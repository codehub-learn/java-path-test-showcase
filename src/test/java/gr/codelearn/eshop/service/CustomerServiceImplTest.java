package gr.codelearn.eshop.service;

import gr.codelearn.eshop.base.DBConnectionPool;
import gr.codelearn.eshop.base.extension.DatabaseManager;
import gr.codelearn.eshop.domain.Customer;
import gr.codelearn.eshop.domain.CustomerCategory;
import gr.codelearn.eshop.exception.InvalidObjectValuesException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DatabaseManager.class)
class CustomerServiceImplTest {

    @Test
    @DisplayName("")
    void registerCustomerExample() throws InvalidObjectValuesException {
        CustomerService customerService = new CustomerServiceImpl();
        Customer customer = Customer.builder("john@gmail.com", CustomerCategory.BUSINESS).build();
        customerService.register(customer);
    }

}