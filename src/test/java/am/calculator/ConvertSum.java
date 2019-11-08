package am.calculator;


import org.junit.jupiter.params.converter.ConvertWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@ConvertWith(SumConverter.class)
@Retention(RetentionPolicy.RUNTIME)
@interface ConvertSum { }
