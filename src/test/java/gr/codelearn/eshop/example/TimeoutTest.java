package gr.codelearn.eshop.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

public class TimeoutTest {

    @Test
    void testShouldSuccessIn2Seconds(){
        assertTimeout(Duration.of(2, ChronoUnit.SECONDS), () -> Thread.sleep(4000));
    }

    @Test
    void testShouldSuccessIn2SecondsWithReturn(){
        Boolean randomBoolean = assertTimeout(Duration.of(2, ChronoUnit.SECONDS), () -> ThreadLocalRandom.current().nextBoolean());
        assertTrue(randomBoolean);
    }

    @Test
    void testShouldSuccessIn2SecondsPreemptively(){
        assertTimeoutPreemptively(Duration.of(2, ChronoUnit.SECONDS), () -> Thread.sleep(4000));
    }

    @Test
    @Timeout(2)
    void testShouldSuccessIn2SecondsAnnotation() throws InterruptedException {
        Thread.sleep(4000);
        // ...
    }
}
