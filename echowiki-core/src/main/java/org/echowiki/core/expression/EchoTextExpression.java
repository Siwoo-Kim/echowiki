package org.echowiki.core.expression;

import org.apache.logging.log4j.util.Strings;
import org.echowiki.core.expression.element.*;

import static com.google.common.base.Preconditions.checkArgument;

public abstract class EchoTextExpression extends AbstractEchoExpression {

    private static final AttributeType ATTRIBUTE_TYPE = AttributeType.TEXT;

    EchoTextExpression(String expString, String expression, String rawValue, String arguments) {
        super(expString, expression, rawValue, arguments);
    }

    public static EchoTextExpression newInstance(String expString, String expression, String rawValue, String arguments) {
        checkArgument(Strings.isNotBlank(expression));
        switch (expression) {
            case "!":
                return new TextStyleDecorator(expString, expression, rawValue, arguments);
            case "!size":
                return new TextSizeDecorator(expString, expression, rawValue, arguments);
            case "!color":
            case "!bgcolor":
                return new TextColorDecorator(expString, expression, rawValue, arguments);
        }
        throw new MalformedExpressionException(String.format("Unknown Expresion [%s]", expString));
    }

    @Override
    protected void hookElement(Element el) {
        el.addAttribute(attribute());
    }

    abstract Attribute attribute();

    private static class TextSizeDecorator extends EchoTextExpression {
        private static final String[] DEFINED_EXPRESSIONS = new String[]{"!size"};
        private static final AttributeKey WIKI_TEXT_SIZE = AttributeKey.WIKI_TEXT_SIZE;

        TextSizeDecorator(String expString, String expression, String rawValue, String arguments) {
            super(expString, expression, rawValue, arguments);
        }

        Attribute attribute() {
            return new SimpleAttribute(WIKI_TEXT_SIZE.type(), WIKI_TEXT_SIZE.key(), arguments());
        }
    }

    private static class TextStyleDecorator extends EchoTextExpression {
        private static final AttributeKey WIKI_TEXT_STYLE = AttributeKey.WIKI_TEXT_STYLE;
        private static final char[] EXPRESSIONS_WRAPPER = {'\'', '`', '-', '_'};
        private static final String[] DEFINED_EXPRESSIONS = {"!"};
        private static final String ATTRIBUTE_KEY = "text-style";
        private static final String[] ATTRIBUTE_VALUES = {"bold", "italic", "del", "underline"};

        TextStyleDecorator(String expString, String expression, String rawValue, String arguments) {
            super(expString, expression, rawValue, arguments);
        }

        Attribute attribute() {
            String value = value();
            char firstChar = value.charAt(0);
            for (int i = 0; i < EXPRESSIONS_WRAPPER.length; i++)
                if (firstChar == EXPRESSIONS_WRAPPER[i])
                    return new SimpleAttribute(WIKI_TEXT_STYLE.type(),
                            WIKI_TEXT_STYLE.key(),
                            ATTRIBUTE_VALUES[i]);
            throw new MalformedExpressionException(
                    String.format("Malformed Expression [%s] for Text styling.", expressionString()));
        }
    }

    private static class TextColorDecorator extends EchoTextExpression {
        private static final AttributeKey[] ATTRIBUTE_KEYS = new AttributeKey[]{AttributeKey.WIKI_TEXT_COLOR, AttributeKey.WIKI_TEXT_BGCOLOR};
        private static final String[] DEFINED_EXPRESSIONS = {"!color", "!bgcolor"};

        TextColorDecorator(String expString, String expression, String rawValue, String arguments) {
            super(expString, expression, rawValue, arguments);
        }

        Attribute attribute() {
            for (int i = 0; i < DEFINED_EXPRESSIONS.length; i++)
                if (expression().equals(DEFINED_EXPRESSIONS[i])) {
                    return new SimpleAttribute(ATTRIBUTE_KEYS[i].type(), ATTRIBUTE_KEYS[i].key(), arguments());
                }
            throw new MalformedExpressionException(
                    String.format("Malformed Expression [%s] for Text coloring.", expressionString()));
        }
    }
}
