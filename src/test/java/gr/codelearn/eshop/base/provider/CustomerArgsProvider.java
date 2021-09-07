package gr.codelearn.eshop.base.provider;

import gr.codelearn.eshop.domain.Customer;
import gr.codelearn.eshop.domain.CustomerCategory;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Arrays;
import java.util.stream.Stream;

public class CustomerArgsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(Arguments.of(Customer.builder("aaaa@gmail.com", CustomerCategory.INDIVIDUAL).setFirstname("John").setAge(20).build()),
                Arguments.of(Customer.builder("bbbb@gmail.com", CustomerCategory.GOVERNMENT).setFirstname("Kostas").setAge(30).build()),
                Arguments.of(Customer.builder("cccc@gmail.com", CustomerCategory.INDIVIDUAL).setFirstname("Alex").setAge(40).build()),
                Arguments.of(Customer.builder("dddd@gmail.com", CustomerCategory.BUSINESS).setFirstname("Nikos").setAge(50).build()));
    }
}
