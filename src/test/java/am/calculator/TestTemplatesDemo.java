package am.calculator;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestTemplatesDemo {

    /**
     * 2.16. Test Templates
     * A @TestTemplate method is not a regular test case but rather a template for test cases.
     * As such, it is designed to be invoked multiple times depending on the number of invocation
     * contexts returned by the registered providers. Thus, it must be used in conjunction with a
     * registered TestTemplateInvocationContextProvider extension. Each invocation of a test template
     * method behaves like the execution of a regular @Test method with full support for the same
     * lifecycle callbacks and extensions. Please refer to Providing Invocation Contexts for Test
     * Templates for usage examples.
     * <p>
     * Repeated Tests and Parameterized Tests are built-in specializations of test templates.
     */

    final List<String> fruits = Lists.list("apple", "banana", "lemon");

    @TestTemplate
    @ExtendWith(MyTestTemplateInvocationContextProvider.class)
    void testTemplate(String fruit) {
        assertTrue(fruits.contains(fruit));
    }


}

class MyTestTemplateInvocationContextProvider implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(
            ExtensionContext context) {

        return Stream.of(invocationContext("apple"), invocationContext("banana"));
    }

    private TestTemplateInvocationContext invocationContext(String parameter) {
        return new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return parameter;
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return Collections.singletonList(new ParameterResolver() {
                    @Override
                    public boolean supportsParameter(ParameterContext parameterContext,
                                                     ExtensionContext extensionContext) {
                        return parameterContext.getParameter().getType().equals(String.class);
                    }

                    @Override
                    public Object resolveParameter(ParameterContext parameterContext,
                                                   ExtensionContext extensionContext) {
                        return parameter;
                    }
                });
            }
        };
    }
}