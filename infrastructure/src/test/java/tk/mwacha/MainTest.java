package tk.mwacha;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.AbstractEnvironment;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testMain() {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "test");

        Assertions.assertNotNull(new Main());
        Main.main(new String[]{});
    }
}