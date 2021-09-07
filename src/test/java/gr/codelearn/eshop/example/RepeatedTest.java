package gr.codelearn.eshop.example;

import io.github.artsok.RepeatedIfExceptionsTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RepeatedTest {

    @Test
    @org.junit.jupiter.api.RepeatedTest(10)
    void repeat10Times(){
        assertTrue(ThreadLocalRandom.current().nextBoolean()); // dummy test
    }

    @RepeatedIfExceptionsTest(repeats = 10, exceptions = IOException.class)
    void repeatedTestRunnerIOException() throws IOException {
        if(ThreadLocalRandom.current().nextBoolean()){
            throw new IOException();
        }
    }

    @RepeatedIfExceptionsTest(repeats = 10, minSuccess = 4)
    void repeatedTestRunnerMinSuccess() {
        assertTrue(ThreadLocalRandom.current().nextBoolean()); // dummy test
    }
}
