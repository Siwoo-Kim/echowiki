package org.echowiki.core.expression;


import java.util.HashMap;
import java.util.Map;

public final class ScopeExpressionFactory {

    private static final Map<String, ScopeExpressionProvider> PROVIDER_TABLE;
    private static final ScopeExpressionProvider DEFAULT_PROVIDER = ParagraphScopeExpression::new;

    private ScopeExpressionFactory() {}

    static {
        PROVIDER_TABLE = new HashMap<>();
        PROVIDER_TABLE.put("p", ParagraphScopeExpression::new);
    }

    public static ScopeExpression newInstance(String expString, String exp, String arguments, String value, String wrapper) {
        if (wrapper == null)
            return new RegularScopeExpression(expString);
        ScopeExpressionProvider provider;
        if (!EchoExpressionParser.isEchoExpression(exp))
            provider = DEFAULT_PROVIDER;
        else
            provider = PROVIDER_TABLE.get(exp);
        return provider.provide(expString, exp, arguments, value, wrapper);
    }

    @FunctionalInterface
    private interface ScopeExpressionProvider {
        ScopeExpression provide(String expString, String expression, String value, String arguments, String wrapper);
    }
}
