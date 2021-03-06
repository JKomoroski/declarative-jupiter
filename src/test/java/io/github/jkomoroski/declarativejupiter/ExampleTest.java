package io.github.jkomoroski.declarativejupiter;

import static java.util.function.Predicate.not;

import io.github.jkomoroski.declarativejupiter.testutils.DeclarativeJupiterUtilities;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExampleTest {

    private Main application;
    private DummyResource1 resource1;
    private DummyResource2 resource2;

    @BeforeAll
    static void arrangeEnvironment() {
        System.setProperty("key", "value");
    }

    @BeforeEach
    void setup() {
        resource1 = new DummyResource1();
        resource1.init();

        resource2 = new DummyResource2();
        resource2.init();

        application = new Main(resource1, resource2);
    }

    @AfterEach
    void tearDown() {
        application.exit();
        resource1.close();
        resource2.close();
    }

    @AfterAll
    static void cleanUpEnvironment() {
        System.clearProperty("key");
    }

    @Test
    void testApplicationStartup() {
        Assertions.assertDoesNotThrow(() -> application.run(), "Expected No Exception When Running...");
    }

    @Test
    void testRequiredSystemProperty() {
        Assertions.assertEquals("value", System.getProperty("key"), "Expected System Property key=value");
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
