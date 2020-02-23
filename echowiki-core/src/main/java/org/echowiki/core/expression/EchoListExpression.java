package org.echowiki.core.expression;

import org.echowiki.core.expression.element.*;

/**
 * {li:Output Message}   => * Output Message
 * {li:Output Message}   => * Output Message
 * {li:Output Message}   => * Output Message
 *
 * {nli:Output Message} => 1. Output Message
 * {nli:Output Message} => 2. Output Message
 * {nli:Output Message} => 3. Output Message
 */
//@todo better way to specifying line elements.
public abstract class EchoListExpression extends AbstractEchoExpression {

    EchoListExpression(String expString, String expression, String rawValue, String arguments) {
        super(expString, expression, rawValue, arguments);
    }

    static AbstractEchoExpression newInstance(String expString, String expression, String rawValue, String arguments) {
        switch (expression) {
            case EchoRegularListExpression.DEFINED_EXPRESSION:
                return new EchoRegularListExpression(expString, expression, rawValue, arguments);
            case EchoNumberListExpression.DEFINED_EXPRESION:
                return new EchoNumberListExpression(expString, expression, rawValue, arguments);
        }
        throw new MalformedExpressionException(String.format("Unknown Expression [%s]", expString));
    }

    @Override
    protected void hookElement(Element el) {
        el.addAttribute(attribute());
    }

    abstract Attribute attribute();


    private static class EchoRegularListExpression extends EchoListExpression {
        public static final String ATTRIBUTE_VALUE = "regular";
        private static final AttributeKey LIST_REGULAR = AttributeKey.WIKI_LIST_REGULAR;
        private static final String DEFINED_EXPRESSION = "li";

        public EchoRegularListExpression(String expString, String expression, String rawValue, String arguments) {
            super(expString, expression, rawValue, arguments);
        }

        @Override
        Attribute attribute() {
            return new SimpleAttribute(LIST_REGULAR.type(), LIST_REGULAR.key(), ATTRIBUTE_VALUE);
        }
    }

    private static class EchoNumberListExpression extends EchoListExpression {
        public static final String ATTRIBUTE_VALUE = "number";
        private static final AttributeKey LIST_NUMBER = AttributeKey.WIKI_LIST_NUMBER;
        private static final String DEFINED_EXPRESION = "nli";

        EchoNumberListExpression(String expString, String expression, String rawValue, String arguments) {
            super(expString, expression, rawValue, arguments);
        }

        @Override
        Attribute attribute() {
            return new SimpleAttribute(LIST_NUMBER.type(), LIST_NUMBER.key(), ATTRIBUTE_VALUE);
        }
    }
}
