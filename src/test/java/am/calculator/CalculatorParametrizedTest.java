package am.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Month;
import java.util.EnumSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalculatorParametrizedTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @ParameterizedTest()
    @ValueSource(ints = {1, 2, 3, 6})
    void testIfPositive(int candidate) {
        assertThat(calculator.isPositive(candidate)).isTrue();
    }

    @ParameterizedTest(name = "tests #{index} is  [{arguments}] not negative?")
    @EnumSource(Month.class)
    void testIfNegative(Month candidate) {
        assertThat(calculator.isNegative(candidate.getValue())).isFalse();
    }

    @ParameterizedTest
    @EnumSource(value = Month.class, names = { "JANUARY", "MARCH" })
    void testWithEnumSourceInclude(Month month) {
        assertTrue(EnumSet.of(Month.JANUARY, Month.MARCH).contains(month));
    }

    @ParameterizedTest
    @EnumSource(value = Month.class, mode = EnumSource.Mode.MATCH_ALL, names = {"^(M|N).+$", "^(MA).+$"})
    void testWithEnumSourceMatchAll(Month month) {
        assertTrue(EnumSet.of(Month.MAY, Month.MARCH).contains(month));
    }

    @ParameterizedTest
    @EnumSource(value = Month.class, mode = EnumSource.Mode.MATCH_ANY, names = {"^(M|N).+$", "^.(M|N).+$"})
    void testWithEnumSourceMatchAny(Month month) {
        assertTrue(EnumSet.of(Month.MAY, Month.MARCH, Month.NOVEMBER).contains(month));
    }


    /**
     * Fallback String-to-Object Conversion
     * In addition to implicit conversion from strings to the target types listed in the above table,
     * JUnit Jupiter also provides a fallback mechanism for automatic conversion from a String to a given target type
     * if the target type declares exactly one suitable factory method or a factory constructor as defined below.
     *
     * factory method: a non-private, static method declared in the target type that accepts a single String argument
     * and returns an instance of the target type. The name of the method can be arbitrary and need not follow any particular convention.
     *
     * factory constructor: a non-private constructor in the target type that accepts a single String argument.
     * Note that the target type must be declared as either a top-level class or as a static nested class.
     */
    @DisplayName("Test With Implicit Fallback Argument Conversion")
    @ParameterizedTest
    @ValueSource(strings = "Casio 573")
    void testWithImplicitFallbackArgumentConversion(CalculatorName calculatorName) {
        assertThat( calculatorName.name()).isEqualTo("Casio 573");
    }

    /**
     * Factory methods within the test class must be static unless the test class is annotated with
     * @TestInstance(Lifecycle.PER_CLASS); whereas, factory methods in external classes must always be static.
     * In addition, such factory methods must not accept any arguments.
     */
    @ParameterizedTest(name = "tests values generated from method - [{arguments}]")
    @MethodSource("generateNumbersOneToTen")
    void testIfPositiveForOneToTen(int candidate) {
        assertThat(calculator.isPositive(candidate)).isTrue();
    }

    private static IntStream generateNumbersOneToTen() {
        return IntStream.rangeClosed(1, 10);
    }

    @ParameterizedTest(name = "tests values generated from method - two arguments - [{arguments}]")
    @MethodSource("generateNumbersFromOneToTenWithDoubled")
    void testIfPositiveForOneToTenWithDoubles(int candidate, int doubled) {
        assertThat(calculator.isPositive(candidate)).isTrue();
        assertThat(calculator.isPositive(doubled)).isTrue();
    }

    private static Stream<Arguments> generateNumbersFromOneToTenWithDoubled() {
        return Stream.of(Arguments.of(1, 2), Arguments.of(2, 4), Arguments.of(3, 6));
    }

    @ParameterizedTest(name = "tests values generated from method from Calculator - [{arguments}]")
    @MethodSource("am.calculator.Calculator#getAllDigits")
    void testIfPositiveForOneToTenWithDoubles(int candidate) {
        assertThat(calculator.isNegative(candidate)).isFalse();
    }

    @ParameterizedTest(name = "tests CSV arguments from source - [{arguments}]")
    @CsvSource({"1,2,3", "0,5,5"})
    void testIfPositiveForArgsFromCsv(int first, int second, int expectedResult) {
        assertThat(calculator.add(first, second)).isEqualTo(expectedResult);
    }

    @ParameterizedTest(name = "tests CSV arguments from file - [{arguments}]")
    @CsvFileSource(resources = "/sums.csv", numLinesToSkip = 1)
    void testIfPositiveForArgsFromCsvFile(int first, int second, int expectedResult) {
        assertThat(calculator.add(first, second)).isEqualTo(expectedResult);
    }

    @ParameterizedTest(name = "tests CSV arguments with converter - [{arguments}]")
    @CsvSource({"1+2=3, 2+4=3"})
    void testIfPositiveForArgsFromCsvWtihConverter(@ConvertWith(SumConverter.class) Sum sumToCheck) {
        assertThat(calculator.add(sumToCheck.first, sumToCheck.second)).isEqualTo(sumToCheck.expectedSum);
    }

    @ParameterizedTest(name = "tests CSV arguments with converter - [{arguments}]")
    @CsvSource({"1+2=3, 2+4=3"})
    void testIfPositiveForArgsFromCsvWithAnnotatedConverter(@ConvertSum Sum sumToCheck) {
        assertThat(calculator.add(sumToCheck.first, sumToCheck.second)).isEqualTo(sumToCheck.expectedSum);
    }

    @ParameterizedTest(name = "tests CSV arguments with ArgumentsAccessor")
    @CsvSource({"RRR,1,2,3,NNN", "0,2,4,6,8"})
    void testIfPositiveForArgsFromCsvWithArgumentsAccessor(ArgumentsAccessor argumentsAccessor) {
        int first = Integer.parseInt((String) argumentsAccessor.get(1));
        int second = Integer.parseInt((String) argumentsAccessor.get(2));
        int expectedSum = Integer.parseInt((String) argumentsAccessor.get(3));

        assertThat(calculator.add(first, second)).isEqualTo(expectedSum);
    }

    @ParameterizedTest(name = "tests CSV arguments with ArgumentsAggregator")
    @CsvSource({"RRR,1,2,3,NNN", "0,2,4,6,8"})
    void testIfPositiveForArgsFromCsvWithArgumentsAggregator(@AggregateWith(SumAggregator.class) Sum sumToCheck) {
        assertThat(calculator.add(sumToCheck.first, sumToCheck.second)).isEqualTo(sumToCheck.expectedSum);
    }

    @ParameterizedTest(name = "tests CSV arguments with ArgumentsAggregator")
    @CsvSource({"RRR,1,2,3,NNN", "0,2,4,6,8"})
    void testIfPositiveForArgsFromCsvWithArgumentsAggregatorAsAnnotation(@AggregateSum Sum sumToCheck) {
        assertThat(calculator.add(sumToCheck.first, sumToCheck.second)).isEqualTo(sumToCheck.expectedSum);
    }


    /**
     * No wait, one more tip: If a source provides more arguments than you have parameters, that’s not a problem.
     * Except when you also need non-parameterized arguments because they must come last and would clash
     * with the parameterized ones, leading to a ParameterResolutionException. You can make that work,
     * by injecting an ArgumentsAccessor into the mix – it eats up the superfluous arguments.
     */

    /**
     *  Specifically, a parameterized test method must declare formal parameters according to the following rules.
     *
     * Zero or more indexed arguments must be declared first.
     *
     * Zero or more aggregators must be declared next.
     *
     * Zero or more arguments supplied by a ParameterResolver must be declared last.
     */

    @ParameterizedTest
    @NullSource // or @NullAndEmptySource for both
    @EmptySource
    @ValueSource(strings = { " ", "   ", "\t", "\n" })
    void nullEmptyAndBlankStrings(String text) {
        assertThat(text).isBlank();
    }
}