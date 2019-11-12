package am.calculator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @Disabled may be declared without providing a reason; however, the JUnit team recommends that developers provide
 * a short explanation for why a test class or test method has been disabled.
 */
@Disabled("disabled until bug #123 will be fixed")
class DisabledTestDemo {

    @Test
    void this_test_will_not_run() {
    }

}

class DisabledMethodDemo {

    @Disabled("disabled until bug #111 will be fixed")
    @Test
    void this_test_will_not_run() {
    }

}