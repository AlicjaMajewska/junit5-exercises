package am.calculator;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DependencyInjectionForConstructorsAndMethodsDemo {

    /**
     * ParameterResolver defines the API for test extensions that wish to dynamically resolve parameters at runtime.
     * If a test class constructor, a test method, or a lifecycle method (see Test Classes and Methods) accepts
     * a parameter, the parameter must be resolved at runtime by a registered ParameterResolver.
     */


    /**
     * TestInfoParameterResolver: if a constructor or method parameter is of type TestInfo, the TestInfoParameterResolver
     * will supply an instance of TestInfo corresponding to the current container or test as the value for the parameter.
     * The TestInfo can then be used to retrieve information about the current container or test such as the display name,
     * the test class, the test method, and associated tags. The display name is either a technical name, such as the name
     * of the test class or test method, or a custom name configured via @DisplayName.
     */
    @Nested
    @DisplayName("TestInfo Demo")
    class TestInfoDemo {

        TestInfoDemo(TestInfo testInfo) {
            System.out.println(testInfo);
            assertEquals("TestInfo Demo", testInfo.getDisplayName());
        }

        @BeforeEach
        void init(TestInfo testInfo) {
            System.out.println(testInfo);
            String displayName = testInfo.getDisplayName();
            assertTrue(displayName.equals("TEST 1") || displayName.equals("test2()"));
        }

        @Test
        @DisplayName("TEST 1")
        @Tag("my-tag")
        void test1(TestInfo testInfo) {
            System.out.println(testInfo);
            assertEquals("TEST 1", testInfo.getDisplayName());
            assertTrue(testInfo.getTags().contains("my-tag"));
        }

        @Test
        void test2() {
        }
    }


    /**
     * RepetitionInfoParameterResolver: if a method parameter in a @RepeatedTest, @BeforeEach, or @AfterEach method
     * is of type RepetitionInfo, the RepetitionInfoParameterResolver will supply an instance of RepetitionInfo.
     * RepetitionInfo can then be used to retrieve information about the current repetition and the total number
     * of repetitions for the corresponding @RepeatedTest. Note, however, that RepetitionInfoParameterResolver is not
     * registered outside the context of a @RepeatedTest. See Repeated Test Examples.
     */

    @Nested
    class RepeatedTestsInfoDemo {

        RepeatedTestsInfoDemo(RepetitionInfo repetitionInfo) {
            System.out.println(repetitionInfo);
        }

        @RepeatedTest(value = 10, name = RepeatedTest.LONG_DISPLAY_NAME)
        void repeatedTestWithLongDisplayName() {
        }

        @RepeatedTest(value = 10, name = RepeatedTest.TOTAL_REPETITIONS_PLACEHOLDER)
        void repeatedTestWithTotalDisplayName(RepetitionInfo repetitionInfo) {
            System.out.println(repetitionInfo);
        }

        @RepeatedTest(value = 10) // the default
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


    /**
     * TestReporterParameterResolver: if a constructor or method parameter is of type TestReporter,
     * the TestReporterParameterResolver will supply an instance of TestReporter. The TestReporter
     * can be used to publish additional data about the current test run. The data can be consumed via the
     * reportingEntryPublished() method in a TestExecutionListener, allowing it to be viewed in IDEs or included in reports.
     *
     * In JUnit Jupiter you should use TestReporter where you used to print information to stdout or stderr in JUnit 4.
     * Using @RunWith(JUnitPlatform.class) will output all reported entries to stdout.
     * In addition, some IDEs print report entries to stdout or display them in the user interface for test results.
     */
    @Nested
    class TestReporterDemo {

        @Test
        void reportSingleValue(TestReporter testReporter) {
            testReporter.publishEntry("a status message");
        }

        @Test
        void reportKeyValuePair(TestReporter testReporter) {
            testReporter.publishEntry("a key", "a value");
        }

        @Test
        void reportMultipleKeyValuePairs(TestReporter testReporter) {
            Map<String, String> values = new HashMap<>();
            values.put("user name", "dk38");
            values.put("award year", "1974");

            testReporter.publishEntry(values);
        }

    }


    /**
     * Other parameter resolvers must be explicitly enabled by registering appropriate extensions via @ExtendWith.
     */
}

