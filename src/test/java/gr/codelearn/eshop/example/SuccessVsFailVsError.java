package gr.codelearn.eshop.example;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SuccessVsFailVsError {

    @Test
    void successTestExample(){
        assertTrue(true);
    }

    @Test
    void failTestExample(){
        assertTrue(false);
    }

    @Test
    void errorTestExample(){
        String name = null;
        assertEquals("John", name.toLowerCase());
    }
}
