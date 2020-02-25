package org.echowiki.core.expression;


import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public final class ScopeExpressionFactory {

    private static final Map<String, ScopeExpressionProvider> PROVIDER_TABLE;
    private static final ScopeExpressionProvider DEFAULT_PROVIDER = ParagraphScopeExpression::new;

    private ScopeExpressionFactory() {}

    static {
        PROVIDER_TABLE = new HashMap<>();
        PROVIDER_TABLE.put("p", ParagraphScopeExpression::new);
    }

    /**
     * returns new instance of the {@link StubScopeExpression}.
     *
     * @param expString
     * @return
     */
    public static ScopeExpression stubScope(String expString) {
        checkArgument(Strings.isNotBlank(expString));
        return new StubScopeExpression(expString);
    }

    /**
     * returns the new instance of {@link ScopeExpression}
     *
     * @param expString
     * @param exp
     * @param arguments
     * @param value
     * @param wrapper
     * @return
     */
    public static ScopeExpression newInstance(String expString, String exp, String arguments, String value, String wrapper) {
        if (wrapper == null)
            return new StubScopeExpression(expString);
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
