package org.echowiki.core.expression;


import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.function.Predicate;

@Builder
@AllArgsConstructor
class ExpressionProvider {

    final Predicate<String> checkIdentifier;
    final Initializer initializer;

    @FunctionalInterface
    interface Initializer {
        Expression provide(String expString, String expression, String rawValue, String arguments);
    }
}
