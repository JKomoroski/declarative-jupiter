package io.github.jkomoroski.declarativejupiter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String...args) {
        final DummyResource1 dummyResource1 = new DummyResource1();
        final DummyResource2 dummyResource2 = new DummyResource2();
        final int exitCode = new Main(dummyResource1, dummyResource2, args).run();

        System.exit(exitCode);
    }

    private final String[] args;
    private final DummyResource1 resource1;
    private final DummyResource2 resource2;
    private boolean isRunning = true;

    public Main(DummyResource1 resource1, DummyResource2 resource2, String...args) {
        this.resource1 = resource1;
        this.resource2 = resource2;
        this.args = args;
        final String requiredProperty = System.getProperty("key");

        if (requiredProperty == null || requiredProperty.isEmpty() || requiredProperty.isBlank()) {
            throw new IllegalStateException("Required property 'key' not set!");
        }
    }

    public int run() {
        try {

            var integers = args.length == 0
                    ? defaultIntegerList()
                    : convertArgumentsToIntegerList(args);

            integers.stream()
                    .map(Main::toFizzBuzz)
                    .forEach(System.out::println);

            return 0;
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage() + ". Cannot parse number formatting for this argument.");
            return 1;
        }
    }

    public void exit() {
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }


    public static List<Integer> convertArgumentsToIntegerList(String... args) throws NumberFormatException {
        return Arrays.stream(args)
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
    }

    public static List<Integer> defaultIntegerList() {
        return IntStream.range(1, 101)
                .sequential()
                .boxed()
                .collect(Collectors.toList());
    }

    public static String toFizzBuzz(Integer integer) {

        String fizz = isFizz(integer) ? "Fizz" : "";
        String buzz = isBuzz(integer) ? "Buzz" : "";
        String output = fizz + buzz;

        return output.isEmpty()
                ? String.valueOf(integer.intValue())
                : output;
    }

    public static boolean isFizz(Integer integer) {
        return integer % 3 == 0;
    }

    public static boolean isBuzz(Integer integer) {
        return integer % 5 == 0;
    }

}
