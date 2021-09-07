package gr.codelearn.eshop.base.resolver;

import gr.codelearn.eshop.domain.Customer;
import gr.codelearn.eshop.domain.CustomerCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class CustomerParameterResolver implements ParameterResolver {

    public CustomerParameterResolver() {
        // Our data could also be cached if we want through the constructor
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        return Objects.equals(parameter.getParameterizedType().getTypeName(), "java.util.List<gr.codelearn.eshop.domain.Customer>");
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        List<Customer> customerList = new ArrayList<>();

        Customer c1 = Customer.builder("aaaa@gmail.com", CustomerCategory.INDIVIDUAL).setFirstname("John").setAge(20).build();
        Customer c2 = Customer.builder("bbbb@gmail.com", CustomerCategory.GOVERNMENT).setFirstname("Kostas").setAge(30).build();
        Customer c3 = Customer.builder("cccc@gmail.com", CustomerCategory.INDIVIDUAL).setFirstname("Alex").setAge(40).build();
        Customer c4 = Customer.builder("dddd@gmail.com", CustomerCategory.BUSINESS).setFirstname("Nikos").setAge(50).build();

        customerList.add(c1);
        customerList.add(c2);
        customerList.add(c3);
        customerList.add(c4);
        return customerList;
    }
}
