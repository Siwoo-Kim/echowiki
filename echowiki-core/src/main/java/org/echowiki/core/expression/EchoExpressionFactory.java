package org.echowiki.core.expression;

import com.sun.istack.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class EchoExpressionFactory {

    private static final Map<String, EchoExpressionProvider> PROVIDER_TABLE;

    static {
        PROVIDER_TABLE = new HashMap<>();
        PROVIDER_TABLE.put("!", EchoTextExpression::newInstance);
        PROVIDER_TABLE.put("!size", EchoTextExpression::newInstance);
        PROVIDER_TABLE.put("!color", EchoTextExpression::newInstance);
        PROVIDER_TABLE.put("!bgcolor", EchoTextExpression::newInstance);
        PROVIDER_TABLE.put("@", EchoLinkDocumentExpression::new);
        PROVIDER_TABLE.put("li", EchoListExpression::newInstance);
        PROVIDER_TABLE.put("nli", EchoListExpression::newInstance);
        PROVIDER_TABLE.put("+", EchoNoteExpression::new);
    }

    public static Expression newInstance(String expString, String expression, @Nullable String value, @Nullable String arguments) {
        if (expression == null)
            return new LiteralExpression(expString, null, null, null);
        expression = expression.trim();
        Expression instance = null;
        EchoExpressionProvider provider = PROVIDER_TABLE.get(expression);
        return provider.provide(expString, expression, value, arguments);
    }

    @FunctionalInterface
    private interface EchoExpressionProvider {
        AbstractEchoExpression provide(String expString, String expression, String value, String arguments);
    }
}
