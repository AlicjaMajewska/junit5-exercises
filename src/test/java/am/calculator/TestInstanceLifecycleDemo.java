package am.calculator;

import org.junit.jupiter.api.*;

class TestInstanceLifecycleDemo {

    TestInstanceLifecycleDemo() {
        System.out.println("run a constructor - TestInstance.Lifecycle.PER_METHOD");
    }

    /**
     * In order to allow individual test methods to be executed in isolation and to avoid unexpected side effects
     * due to mutable test instance state, JUnit creates a new instance of each test class before executing each test method.
     * <p>
     * Please note that the test class will still be instantiated if a given test method is disabled via a condition
     * (e.g., @Disabled, @DisabledOnOs, etc.) even when the "per-method" test instance lifecycle mode is active.
     * <p>
     * If you would prefer that JUnit Jupiter execute all test methods on the same test instance, annotate your test
     * class with @TestInstance(Lifecycle.PER_CLASS)
     * <p>
     * The "per-class" mode has some additional benefits over the default "per-method" mode.
     * Specifically, with the "per-class" mode it becomes possible to declare @BeforeAll and
     *
     * @AfterAll on non-static methods as well as on interface default methods.
     * The "per-class" mode therefore also makes it possible to use @BeforeAll and @AfterAll methods in @Nested test classes.
     */


    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class NestedClassWithLifecyclePerClass {

        NestedClassWithLifecyclePerClass() {
            System.out.println("run a constructor - TestInstance.Lifecycle.PER_CLASS");
        }

        @BeforeAll
        void setUpAll() {
            System.out.println("Set up all ONLY ONCE");
        }

        @Test
        void test1() {
            System.out.println("test nr 1 nested");
        }

        @Test
        void test2() {
            System.out.println("test nr 2 nested");
        }

        @AfterAll
        void tearDownAll() {
            System.out.println("Tear down all ONLY ONCE");
        }
    }


    @BeforeAll
    static void setUpAll() {
        System.out.println("Set up all ONLY ONCE");
    }

    @Test
    void test1() {
        System.out.println("test nr 1");
    }

    @Test
    void test2() {
        System.out.println("test nr 2");
    }

    @Test
    @Disabled("shows that disabled tests also creates new test instance")
    void disabledTest() {
        System.out.println("disabled test");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Tear down all ONLY ONCE");
    }

}
