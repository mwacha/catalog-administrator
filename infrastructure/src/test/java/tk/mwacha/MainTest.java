package tk.mwacha;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testMain() {
        Assertions.assertNotNull(new Main());
        Main.main(new String[]{});
    }
}