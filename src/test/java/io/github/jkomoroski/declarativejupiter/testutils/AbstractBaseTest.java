package io.github.jkomoroski.declarativejupiter.testutils;

import io.github.jkomoroski.declarativejupiter.DummyResource1;
import io.github.jkomoroski.declarativejupiter.DummyResource2;
import io.github.jkomoroski.declarativejupiter.Main;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractBaseTest {

    protected Main application;
    protected DummyResource1 resource1;
    protected DummyResource2 resource2;

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

}
