package gr.codelearn.eshop.service;

import gr.codelearn.eshop.base.extension.DatabaseManager;
import gr.codelearn.eshop.base.provider.CustomerArgsProvider;
import gr.codelearn.eshop.base.resolver.CustomerParameterResolver;
import gr.codelearn.eshop.domain.Customer;
import gr.codelearn.eshop.domain.CustomerCategory;
import gr.codelearn.eshop.exception.InvalidObjectValuesException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.entry;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DatabaseManager.class)
@ExtendWith(CustomerParameterResolver.class)
@DisplayName("A customer service")
@Slf4j
@Tag("production")
class CustomerServiceImplTest {

    CustomerService customerService;
    List<Customer> customerList;

    @BeforeEach
    void initializeObjects(List<Customer> customers) {
        customerService = new CustomerServiceImpl();
        customerList = customers;
    }

    @Nested
    @DisplayName("should be empty")
    class isEmpty {
        @Test
        @DisplayName("when the application starts")
        void serviceReturnsNoCustomers() {
            List<Customer> customers = customerService.getCustomers();
            assertEquals(0, customers.size(), "The repository should be empty");
        }

        @Test
        @DisplayName("when a NULL is registered")
        void serviceShouldNotRegisterNullCustomer() {
            try {
                Customer customer = null;
                customerService.register(customer);
                fail();
            } catch (NullPointerException | InvalidObjectValuesException e) {
                log.info("Exception thrown.");
            }
            List<Customer> customers = customerService.getCustomers();
            assertEquals(0, customers.size(), "The repository should be empty");
        }

        @Test
        @DisplayName("when a customer with null secondary values is registered")
        void serviceShouldNotRegisterCustomerWithNullSecondaryValues() {
            try {
                Customer customer = Customer.builder("john@gmail.com", CustomerCategory.GOVERNMENT).setFirstname(null).setLastname(null).setAddress(null).setAge(0).build();
                customerService.register(customer);
                fail();
            } catch (NullPointerException | InvalidObjectValuesException e) {
                log.info("Exception thrown.");
            }
            List<Customer> customers = customerService.getCustomers();
            assertEquals(0, customers.size(), "The repository should not be empty");
        }
    }

    @Nested
    @DisplayName("returns")
    class Returns {
        @Test
        @DisplayName("the correct customer (ID & email)")
        void serviceReturnsTheCorrectCustomerByIDOrEmail() {
            Customer customer = Customer.builder("john@gmail.com", CustomerCategory.GOVERNMENT).build();
            try {
                customerService.register(customer);
            } catch (NullPointerException | InvalidObjectValuesException e) {
                log.info("Exception thrown.");
                fail();
            }

            Customer retrievedCustomerByEmail = customerService.getCustomer("john@gmail.com");
            Customer retrievedCustomerByID = customerService.getCustomer(1L);
            assertAll("The retrieved customer is not what is expected",
                    () -> assertEquals(customer, retrievedCustomerByEmail, "The customers should be the same"),
                    () -> assertEquals(customer, retrievedCustomerByID, "The customers should be the same")
            );
        }
    }

    @Nested
    @DisplayName("arranges")
    class Arranges {
        @Test
        @DisplayName("by email (default order)")
        void serviceArrangesByEmail() {
                customerList.forEach(customer -> {
                    try {
                        customerService.register(customer);
                    } catch (InvalidObjectValuesException e) {
                        log.info("Exception thrown.");
                        fail();
                    }
                });
            List<Customer> customersSortedByEmail = customerService.getCustomersSortedByEmail();
            assertEquals(customerList, customersSortedByEmail, "The lists should be in the same order");
        }

        @Test
        @DisplayName("by custom criteria")
        void serviceArrangesByCustomCriteria() {
            customerList.forEach(customer -> {
                try {
                    customerService.register(customer);
                } catch (InvalidObjectValuesException e) {
                    log.info("Exception thrown.");
                    fail();
                }
            });
            List<Customer> customersSortedByAge = customerService.getCustomersSorted(Comparator.comparing(Customer::getAge));
            List<Customer> customersSortedByFirstname = customerService.getCustomersSorted(Comparator.comparing(Customer::getFirstname));
            assertAll("Something went wrong with the ordering",
                    () -> assertThat(customersSortedByAge).isSortedAccordingTo(Comparator.comparing((Customer::getAge))),
                    () -> assertThat(customersSortedByFirstname).isSortedAccordingTo(Comparator.comparing((Customer::getFirstname))));
        }
    }

    @Nested
    @DisplayName("is grouped by")
    class isGrouped {
        @Test
        @DisplayName("customer category")
        void serviceGroupsByCustomerCategory() {
            customerList.forEach(customer -> {
                try {
                    customerService.register(customer);
                } catch (InvalidObjectValuesException e) {
                    log.info("Exception thrown.");
                    fail();
                }
            });
            Customer c1 = customerList.get(0);
            Customer c2 = customerList.get(1);
            Customer c3 = customerList.get(2);
            Customer c4 = customerList.get(3);
            Map<CustomerCategory, List<Customer>> customerCategoryListMap = customerService.groupByCustomerCategory();
            assertAll("Something went wrong with the ordering",
                    () -> assertThat(customerCategoryListMap).contains(entry(CustomerCategory.BUSINESS, List.of(c4))),
                    () -> assertThat(customerCategoryListMap).contains(entry(CustomerCategory.GOVERNMENT, List.of(c2))),
                    () -> assertThat(customerCategoryListMap).contains(entry(CustomerCategory.INDIVIDUAL, Arrays.asList(c1,c3))));
        }
    }

    @Nested
    class Parametrized {
        @ParameterizedTest
        @ValueSource(strings = {"john@gmail.com", "mike@yahoo.gr", "nick@gmail.com"})
        void testIfCustomerRegisters(String email){
            log.info("Running test with email: {}", email);
            Customer customer = Customer.builder(email, CustomerCategory.INDIVIDUAL).build();
            try {
                customerService.register(customer);
            } catch (InvalidObjectValuesException e) {
                log.info("Exception thrown.");
            }
        }

        @ParameterizedTest
        @ArgumentsSource(CustomerArgsProvider.class)
        void testIfCustomerRegistersWithProvider(Customer customer){
            log.info("Customer: {}", customer);
            try {
                customerService.register(customer);
            } catch (InvalidObjectValuesException e) {
                log.info("Exception thrown.");
            }
        }
    }
}

