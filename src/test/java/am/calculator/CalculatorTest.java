package am.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorTest {

    @Test
    @DisplayName("1 + 2 = 3")
    void add() {
        // given
        Calculator calculator = new Calculator();

        // when
        int result = calculator.add(1, 2);

        // then

        assertThat(result).isEqualTo(3);
    }
}