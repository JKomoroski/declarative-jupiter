package io.github.jkomoroski.declarativejupiter.testutils;

import io.github.jkomoroski.declarativejupiter.DummyResource1;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.support.TypeBasedParameterResolver;

public class DummyResource1Extension extends TypeBasedParameterResolver<DummyResource1> implements BeforeEachCallback,
        AfterEachCallback {

    @Override
    public DummyResource1 resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return extensionContext.getStore(ExtensionContext.Namespace.GLOBAL)
                .getOrComputeIfAbsent(DummyResource1.class, k -> new DummyResource1(), DummyResource1.class);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        final DummyResource1 dummyResource1 = context.getStore(ExtensionContext.Namespace.GLOBAL)
                .get(DummyResource1.class, DummyResource1.class);
        dummyResource1.init();
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        final DummyResource1 dummyResource1 = context.getStore(ExtensionContext.Namespace.GLOBAL)
                .get(DummyResource1.class, DummyResource1.class);
        dummyResource1.close();
    }
}
