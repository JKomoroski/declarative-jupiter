package io.github.jkomoroski.declarativejupiter.testutils;

import io.github.jkomoroski.declarativejupiter.DummyResource2;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.support.TypeBasedParameterResolver;

public class DummyResource2Extension extends TypeBasedParameterResolver<DummyResource2> implements BeforeEachCallback, AfterEachCallback {

    @Override
    public DummyResource2 resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(ExtensionContext.Namespace.GLOBAL)
                .getOrComputeIfAbsent(DummyResource2.class, k -> new DummyResource2(), DummyResource2.class);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        final DummyResource2 dummyResource2 = context.getStore(ExtensionContext.Namespace.GLOBAL)
                .get(DummyResource2.class, DummyResource2.class);
        dummyResource2.init();
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        final DummyResource2 dummyResource2 = context.getStore(ExtensionContext.Namespace.GLOBAL)
                .get(DummyResource2.class, DummyResource2.class);
        dummyResource2.close();
    }

}
