package org.echowiki.core.expression;

import org.echowiki.core.expression.element.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * {li:Output Message}   => * Output Message
 * {li:Output Message}   => * Output Message
 * {li:Output Message}   => * Output Message
 *
 * {nli:Output Message} => 1. Output Message
 * {nli:Output Message} => 2. Output Message
 * {nli:Output Message} => 3. Output Message
 *
 * {#:Output Message}
 * {#1: Output Message}
 * {#2: Output Message}
 * {#2: Output Message/} => termination
 *
 * {type={number|regular},{index=[Number]}}
 */
//@todo better way to specifying line elements.
public abstract class EchoListExpression extends AbstractEchoExpression {
    private static final List<ExpressionProvider> providers = new ArrayList<>();
    private static final WIKI WIKI_LIST = WIKI.WIKI_LIST;
    public static final String TERMINATION_SYMBOL = "/";
    public static final int FIRST_ITEM = 1;
    public static final String ITEM_VALUE_FORMAT = "item-%d";

    static {
        providers.add(ExpressionProvider.builder()
                .checkIdentifier(e -> e.equals(EchoRegularListExpression.IDENTIFIER))
                .initializer(EchoRegularListExpression::new)
                .build());
        providers.add(ExpressionProvider.builder()
                .checkIdentifier(e -> Arrays.asList(EchoNumberListExpression.IDENTIFIERS).contains(e))
                .initializer(EchoNumberListExpression::new)
                .build());
    }

    EchoListExpression(String expString, String expression, String rawValue, String arguments) {
        super(expString, expression, rawValue, arguments);
    }

    //@todo refactoring
    static AbstractEchoExpression newInstance(String expString, String expression, String rawValue, String arguments) {
        if (expression.equals(EchoRegularListExpression.IDENTIFIER))
            return new EchoRegularListExpression(expString, expression, rawValue, arguments);
        for (String exp: EchoNumberListExpression.IDENTIFIERS) {
            if (exp.equals(expression))
                return new EchoNumberListExpression(expString, expression, rawValue, arguments);
        }

        throw new MalformedExpressionException(String.format("Unknown Expression [%s]", expString));
    }

    @Override
    protected void hookElement(Element el) {
        el.addValue(WIKI_LIST.key(), "type=" + getAttributeValue());
    }

    abstract String getAttributeValue();

    public int handleIndexOfList(Element el, int lastIndex) {
        el.addValue(WIKI_LIST.key(), "index=" + String.format(ITEM_VALUE_FORMAT, lastIndex++));
        if (isTerminated())
            return FIRST_ITEM;
        else
            return lastIndex;
    }

    private boolean isTerminated() {
        return value() != null &&
                !StringHelper.isCharEscaped(value(), value().length()-1) &&
                value().endsWith(TERMINATION_SYMBOL);
    }

    private static class EchoRegularListExpression extends EchoListExpression {
        public static final String ATTRIBUTE_VALUE = "regular";
        private static final String IDENTIFIER = "li";

        public EchoRegularListExpression(String expString, String expression, String rawValue, String arguments) {
            super(expString, expression, rawValue, arguments);
        }

        @Override
        String[] identifiers() {
            return new String[]{IDENTIFIER};
        }

        @Override
        String getAttributeValue() {
            return ATTRIBUTE_VALUE;
        }
    }

    private static class EchoNumberListExpression extends EchoListExpression {
        public static final String ATTRIBUTE_VALUE = "number";
        private static final String[] IDENTIFIERS = new String[]{"#", "nli"};

        EchoNumberListExpression(String expString, String expression, String rawValue, String arguments) {
            super(expString, expression, rawValue, arguments);
        }

        @Override
        String[] identifiers() {
            return IDENTIFIERS.clone();
        }

        @Override
        String getAttributeValue() {
            return ATTRIBUTE_VALUE;
        }
    }
}
