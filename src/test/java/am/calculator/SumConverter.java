package am.calculator;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

class SumConverter implements ArgumentConverter {

    @Override
    public Object convert(Object input, ParameterContext parameterContext) throws ArgumentConversionException {
        if (input instanceof Sum)
            return input;
        if (input instanceof String)
            try {
                String toParse = (String) input;
                // 1 + 2 = 3
                int first = Integer.parseInt(toParse.substring(0, toParse.indexOf('+')).trim());
                int second = Integer.parseInt(toParse.substring(toParse.indexOf('+') + 1, toParse.indexOf('=')).trim());
                int expectedSum = Integer.parseInt(toParse.substring(toParse.indexOf('=') + 1).trim());

                return Sum.from(first, second, expectedSum);
            } catch (NumberFormatException ex) {
                String message = input
                        + " is no correct string representation of a sum.";
                throw new ArgumentConversionException(message, ex);
            }
        throw new ArgumentConversionException(input + " is no valid point");
    }

}
