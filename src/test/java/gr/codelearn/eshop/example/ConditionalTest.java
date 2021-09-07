package gr.codelearn.eshop.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConditionalTest {

    @Test
    @EnabledOnOs({OS.WINDOWS, OS.MAC})
    void shouldRunOnWindowsAndMac(){
        assertTrue(true); // dummy test
    }

    @Test
    @DisabledOnOs(OS.LINUX)
    void shouldNotRunOnLinux(){
        assertTrue(true); // dummy test
    }

    @Test
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_11})
    void shouldRunOnJRE8And11(){
        assertTrue(true); // dummy test
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_17)
    void shouldRunOnJRERange(){
        assertTrue(true); // dummy test
    }

    @Test
    @EnabledIfSystemProperty(named = "java.vm.vendor", matches = "Oracle.*")
    void shouldRunOnOracleJRE(){
        assertTrue(true); // dummy test
    }
}
