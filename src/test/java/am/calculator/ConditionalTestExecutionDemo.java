package am.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.condition.JRE.*;
import static org.junit.jupiter.api.condition.OS.LINUX;
import static org.junit.jupiter.api.condition.OS.MAC;
import static org.junit.jupiter.api.condition.OS.WINDOWS;

class ConditionalTestExecutionDemo {

    /**
     * he ExecutionCondition extension API in JUnit Jupiter allows developers to either enable or disable a container
     * or test based on certain conditions programmatically. The simplest example of such a condition is the
     * built-in DisabledCondition
     * <p>
     * Each of the conditional annotations listed in the following sections can only be declared once on a given test
     * interface, test class, or test method. If a conditional annotation is directly present, indirectly present,
     * or meta-present multiple times on a given element, only the first such annotation discovered by JUnit will
     * be used; any additional declarations will be silently ignored. Note, however, that each conditional annotation
     * may be used in conjunction with other conditional annotations in the org.junit.jupiter.api.condition package.
     */
    @DisplayName("operating system conditions")
    @Nested
    class OperationSystemConditions {
        @Test
        @EnabledOnOs({LINUX, MAC})
        void onLinuxOrMac() {
            System.out.println("Only for MAC or LINUX");
        }

        @TestOnMac
        void onMacOnlyCustomAnnotation() {
            System.out.println("Only for MAC");
        }

        @Test
        @DisabledOnOs(WINDOWS)
        void notOnWindows() {
            System.out.println("NOT FOR WINDOWS");
        }
    }

    @DisplayName("Java Runtime Environment Conditions")
    @Nested
    class JavaRuntimeEnvironmentConditions {
        @Test
        @EnabledOnJre(JAVA_8)
        void onlyOnJava8() {
            System.out.println("only on Java 8");
        }

        @Test
        @EnabledOnJre({JAVA_9, JAVA_10})
        void onJava9Or10() {
            System.out.println("only on Java 9 or 10");
        }

        @Test
        @DisabledOnJre(JAVA_11)
        void notOnJava11() {
            System.out.println("not for java 11");
        }
    }

    @DisplayName("System Property Conditions")
    @Nested
    class SystemPropertyConditions {
        @Test
        @DisabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
        void notOn64BitArchitectures() {
            System.out.println("not on 64 bit architectures");
        }

        @Test
        @EnabledIfSystemProperty(named = "ci-server", matches = "true")
        void onlyOnCiServer() {
            System.out.println("only on Ci Server");
        }
    }

    @DisplayName("Environment Variable Conditions")
    @Nested
    class EnvironmentVariableConditions {
        @Test
        @EnabledIfEnvironmentVariable(named = "ENV", matches = "staging-server")
        void onlyOnStagingServer() {
            System.out.println("only on staging server");
        }

        @Test
        @DisabledIfEnvironmentVariable(named = "ENV", matches = ".*development.*")
        void notOnDeveloperWorkstation() {
            System.out.println("not on developer workstation");
        }
    }

    /**
     * Conditional test execution via @EnabledIf and @DisabledIf is deprecated for removal in a future release of JUnit Jupiter.
     * <p>
     * systemEnvironment accessor Operating system environment variable accessor.
     * <p>
     * systemProperty   accessor JVM system property accessor.
     * <p>
     * junitConfigurationParameter accessor Configuration parameter accessor.
     * <p>
     * junitDisplayName String Display name of the test or container.
     * <p>
     * junitTags Set<String> All tags assigned to the test or container.
     * <p>
     * junitUniqueId String Unique ID of the test or container.
     */
    @DisplayName("Script-based Conditions")
    @Nested
    class ScriptBasedConditions {
        @Test // Static JavaScript expression.
        @EnabledIf("2 * 3 == 6")
        void willBeExecuted() {
            // ...
        }

        @RepeatedTest(10) // Dynamic JavaScript expression.
        @DisabledIf("Math.random() < 0.314159")
        void mightNotBeExecuted() {
            // ...
        }

        @Test // Regular expression testing bound system property.
        @DisabledIf("/32/.test(systemProperty.get('os.arch'))")
        void disabledOn32BitArchitectures() {
            assertFalse(System.getProperty("os.arch").contains("32"));
        }

        @Test
        @EnabledIf("'CI' == systemEnvironment.get('ENV')")
        void onlyOnCiServer() {
            assertTrue("CI".equals(System.getenv("ENV")));
        }

        @Test // Multi-line script, custom engine name and custom reason.
        @EnabledIf(value = {
                "load('nashorn:mozilla_compat.js')",
                "importPackage(java.time)",
                "",
                "var today = LocalDate.now()",
                "var tomorrow = today.plusDays(1)",
                "tomorrow.isAfter(today)"
        },
                engine = "nashorn",
                reason = "Self-fulfilling: {result}")
        void theDayAfterTomorrow() {
            LocalDate today = LocalDate.now();
            LocalDate tomorrow = today.plusDays(1);
            assertTrue(tomorrow.isAfter(today));
        }

    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Test
    @EnabledOnOs(MAC)
    @interface TestOnMac {
    }
}
