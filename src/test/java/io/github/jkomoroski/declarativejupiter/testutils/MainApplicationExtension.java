package io.github.jkomoroski.declarativejupiter.testutils;

import io.github.jkomoroski.declarativejupiter.DummyResource1;
import io.github.jkomoroski.declarativejupiter.DummyResource2;
import io.github.jkomoroski.declarativejupiter.Main;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.support.TypeBasedParameterResolver;

public class MainApplicationExtension extends TypeBasedParameterResolver<Main> implements AfterAllCallback {

    private static List<Main> instances = new ArrayList<>();

    @Override
    public Main resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        final ExtensionContext.Store globalStore = extensionContext.getStore(Namespace.GLOBAL);
        final var dr1 = globalStore.get(DummyResource1.class, DummyResource1.class);
        final var dr2 = globalStore.get(DummyResource2.class, DummyResource2.class);
        final var main = new Main(dr1, dr2);
        parameterContext.findAnnotation(Started.class)
                .ifPresent(s -> main.run());
        instances.add(main);
        return main;
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        instances.stream()
                .filter(Main::isRunning)
                .forEach(Main::exit);
    }

}
