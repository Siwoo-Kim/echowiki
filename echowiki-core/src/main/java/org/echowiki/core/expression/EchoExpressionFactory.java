package org.echowiki.core.expression;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class EchoExpressionFactory {

    private static final List<EchoExpressionResolver> PRECEDENCED_TABLE;

    static {
        PRECEDENCED_TABLE = new ArrayList<>();

        PRECEDENCED_TABLE.add(
                new EchoExpressionResolver("!"::equals, EchoTextExpression::newInstance));
        PRECEDENCED_TABLE.add(
                new EchoExpressionResolver("!size"::equals, EchoTextExpression::newInstance));
        PRECEDENCED_TABLE.add(
                new EchoExpressionResolver("!color"::equals, EchoTextExpression::newInstance));
        PRECEDENCED_TABLE.add(
                new EchoExpressionResolver("!bgcolor"::equals, EchoTextExpression::newInstance));
        PRECEDENCED_TABLE.add(
                new EchoExpressionResolver("@"::equals, EchoLinkDocumentExpression::new));
        PRECEDENCED_TABLE.add(
                new EchoExpressionResolver(
                        (key) -> key.equals("li") || key.startsWith("#") || key.startsWith("nli"),
                        EchoListExpression::newInstance));
        PRECEDENCED_TABLE.add(
                new EchoExpressionResolver("+"::equals, EchoNoteExpression::new));
    }

    private EchoExpressionFactory() {
    }

    public static Expression newInstance(String expString, String expression, @Nullable String value, @Nullable String arguments) {
        if (expression == null)
            return new LiteralExpression(expString, null, null, null);
        expression = expression.trim();
        EchoExpressionProvider provider = null;
        for (EchoExpressionResolver resolver : PRECEDENCED_TABLE) {
            if (resolver.predicate.test(expression)) {
                provider = resolver.provider;
                break;
            }
        }
        if (provider == null)
            throw new MalformedExpressionException(String.format("Unknown expression [%s] in string [%s]", expression, expString));
        return provider.provide(expString, expression, value, arguments);
    }

    @FunctionalInterface
    private interface EchoExpressionProvider {
        AbstractEchoExpression provide(String expString, String expression, String value, String arguments);
    }

    @AllArgsConstructor
    private static class EchoExpressionResolver {
        Predicate<String> predicate;
        EchoExpressionProvider provider;
    }
}
