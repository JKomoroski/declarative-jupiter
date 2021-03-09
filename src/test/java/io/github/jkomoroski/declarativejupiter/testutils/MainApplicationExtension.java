package io.github.jkomoroski.declarativejupiter.testutils;

import io.github.jkomoroski.declarativejupiter.DummyResource1;
import io.github.jkomoroski.declarativejupiter.DummyResource2;
import io.github.jkomoroski.declarativejupiter.Main;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.support.TypeBasedParameterResolver;

public class MainApplicationExtension extends TypeBasedParameterResolver<Main> implements AfterEachCallback {

    @Override
    public Main resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final ExtensionContext.Store store = extensionContext.getStore(ExtensionContext.Namespace.GLOBAL);
        final DummyResource1 dr1 = store.get(DummyResource1.class, DummyResource1.class);
        final DummyResource2 dr2 = store.get(DummyResource2.class, DummyResource2.class);

        return store.getOrComputeIfAbsent(Main.class, k -> new Main(dr1, dr2), Main.class);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        final Main main = context.getStore(ExtensionContext.Namespace.GLOBAL).get(Main.class, Main.class);
        main.exit();
    }
}
