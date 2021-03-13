package io.github.jkomoroski.declarativejupiter.testutils;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.SetSystemProperty;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SetSystemProperty(key = "key", value = "value")
@ExtendWith({DummyResource1Extension.class, DummyResource2Extension.class, MainApplicationExtension.class})
public @interface UseMyExtensions {

}
