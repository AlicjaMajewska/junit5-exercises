package am.calculator;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

class RepeatedTestsDemo {

    @RepeatedTest(value = 10, name = "checking in progress - {currentRepetition}/{totalRepetitions}")
    void repeatedTest() {
    }

    @RepeatedTest(value = 10, name = RepeatedTest.LONG_DISPLAY_NAME)
    void repeatedTestWithLongDisplayName() {
    }

    @RepeatedTest(value = 10, name = RepeatedTest.TOTAL_REPETITIONS_PLACEHOLDER)
    void repeatedTestWithTotalDisplayName() {
    }

    @RepeatedTest(value = 10, name = RepeatedTest.SHORT_DISPLAY_NAME) // the default
    void repeatedTestWithShortDisplayName() {
    }

    /**
     * In order to retrieve information about the current repetition and the total number of repetitions
     * programmatically, a developer can choose to have an instance of RepetitionInfo injected into a
     * @RepeatedTest, @BeforeEach, or @AfterEach method.
     */
    @RepeatedTest(value = 10)
    void repeatedTestWithShortDisplayName(RepetitionInfo repetitionInfo) {
        System.out.println(repetitionInfo);
    }


}
