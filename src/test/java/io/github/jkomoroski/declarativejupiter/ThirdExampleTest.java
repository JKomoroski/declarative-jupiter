package io.github.jkomoroski.declarativejupiter;

import static java.util.function.Predicate.not;

import io.github.jkomoroski.declarativejupiter.testutils.DeclarativeJupiterUtilities;
import io.github.jkomoroski.declarativejupiter.testutils.DummyResource1Extension;
import io.github.jkomoroski.declarativejupiter.testutils.DummyResource2Extension;
import io.github.jkomoroski.declarativejupiter.testutils.MainApplicationExtension;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.junitpioneer.jupiter.WritesSystemProperty;

@SetSystemProperty(key = "key", value = "value")
@ExtendWith({DummyResource1Extension.class, DummyResource2Extension.class, MainApplicationExtension.class})
class ThirdExampleTest {

    private final Main application;
    private final DummyResource1 resource1;
    private final DummyResource2 resource2;

    ThirdExampleTest(Main application, DummyResource1 resource1, DummyResource2 resource2) {
        this.application = application;
        this.resource1 = resource1;
        this.resource2 = resource2;
    }

    @Test
    void testApplicationStartup() {
        Assertions.assertDoesNotThrow(application::run, "Expected No Exception When Running...");
    }

    @Test
    void testRequiredSystemProperty() {
        Assertions.assertEquals("value", System.getProperty("key"), "Expected System Property key=value");
    }

    @Test
    @WritesSystemProperty
    void testThrowOnStartUp(DummyResource1 r1, DummyResource2 r2) {
        System.clearProperty("key");
        Assertions.assertThrows(IllegalStateException.class, () -> new Main(r1, r2), "application fails to start without prop");
    }

    @Test
    void testFirstHundredThousandFizzBuzzDoesNotThrow() {
        final String[] args = IntStream.range(0, 100_000)
                .mapToObj(String::valueOf)
                .toArray(String[]::new);

        Assertions.assertDoesNotThrow(() -> new Main(resource1, resource2, args).run());
    }

    @Test
    void testFirstMillionFizzBuzzNumbersAreNotDivisibleByThreeOrFive() {
        IntStream.range(0, 1_000_000)
                .mapToObj(Main::toFizzBuzz)
                .filter(DeclarativeJupiterUtilities::isDecimalNumber)
                .map(Integer::parseInt)
                .filter(i -> (i % 3 == 0) || (i % 5 == 0))
                .findAny()
                .ifPresent(m -> Assertions.fail("Expected none of first million to be divisible by 3 or 5. Found=" + m));
    }

    @Test
    void testFirstMillionFizzBuzzWords() {
        IntStream.range(0, 1_000_000)
                .mapToObj(Main::toFizzBuzz)
                .filter(not(DeclarativeJupiterUtilities::isDecimalNumber))
                .filter(s -> !("Fizz".equals(s) || "Buzz".equals(s) || "FizzBuzz".equals(s)))
                .findAny()
                .ifPresent(m -> Assertions.fail("Expected none of first million to be Fizz or Buzz or FizzBuzz. Found=" + m));
    }

    @Test
    void testApplicationHasRunningState() {
        application.run();
        Assertions.assertTrue(application.isRunning(), "Application Should be running");
    }

    @Test
    void testStaticMethods() {

        Assertions.assertTrue(Main.isFizz(3));
        Assertions.assertFalse(Main.isFizz(5));

        Assertions.assertTrue(Main.isBuzz(5));
        Assertions.assertFalse(Main.isBuzz(3));

        Assertions.assertEquals(100, Main.defaultIntegerList().size());

        List<Integer> integerList = List.of(1, 2, 3, 4, 5);
        Assertions.assertEquals(integerList, Main.convertArgumentsToIntegerList("1", "2", "3", "4", "5"));

        Assertions.assertEquals("Fizz", Main.toFizzBuzz(3));
        Assertions.assertEquals("Buzz", Main.toFizzBuzz(5));
        Assertions.assertEquals("FizzBuzz", Main.toFizzBuzz(15));
        Assertions.assertEquals("1", Main.toFizzBuzz(1));

    }

}
