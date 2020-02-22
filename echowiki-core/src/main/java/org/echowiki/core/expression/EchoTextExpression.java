package org.echowiki.core.expression;

import org.apache.logging.log4j.util.Strings;
import org.echowiki.core.expression.meta.AttributeType;

import static com.google.common.base.Preconditions.checkArgument;

public abstract class EchoTextExpression extends AbstractEchoExpression {

    private static final AttributeType ATTRIBUTE_TYPE = AttributeType.TEXT;

    public static EchoTextExpression newInstance(String expString, String expression, String rawValue, String arguments) {
        checkArgument(Strings.isNotBlank(expression));
        switch (expression) {
            case "!": return new TextStyleDecorator(expString, expression, rawValue, arguments);
            case "!size": return new TextSizeDecorator(expString, expression, rawValue, arguments);
            case "!color":
            case "!bgcolor": return new TextColorDecorator(expString, expression, rawValue, arguments);
        }
        throw new MalformedExpressionException(String.format("Unknown Expresion [%s]", expString));
    }

    EchoTextExpression(String expString, String expression, String rawValue, String arguments) {
        super(expString, expression, rawValue, arguments);
    }

    @Override
    protected void hookElement(Element el) {
        el.addAttribute(attribute());
    }

    abstract Element.Attribute attribute();

    private static class TextSizeDecorator extends EchoTextExpression {
        private static final String[] DEFINED_EXPRESSIONS = new String[]{"!size"};
        private static final String ATTRIBUTE_KEY = "text-size";

        TextSizeDecorator(String expString, String expression, String rawValue, String arguments) {
            super(expString, expression, rawValue, arguments);
        }

        Element.Attribute attribute() {
            assert expression().equals(DEFINED_EXPRESSIONS);
            return SimpleElement.newAttribute(ATTRIBUTE_TYPE, ATTRIBUTE_KEY, arguments());
        }
    }

    private static class TextStyleDecorator extends EchoTextExpression {
        private static final char[] EXPRESSIONS_WRAPPER = {'\'', '`', '-', '_'};
        private static final String[] DEFINED_EXPRESSIONS = {"!"};

        private static final String ATTRIBUTE_KEY = "text-style";
        private static final String[] ATTRIBUTE_VALUES = {"bold", "italic", "del", "underline"};

        TextStyleDecorator(String expString, String expression, String rawValue, String arguments) {
            super(expString, expression, rawValue, arguments);
        }

        Element.Attribute attribute() {
            String value = value();
            char firstChar = value.charAt(0);
            for (int i=0; i<EXPRESSIONS_WRAPPER.length; i++)
                if (firstChar == EXPRESSIONS_WRAPPER[i])
                    return SimpleElement.newAttribute(ATTRIBUTE_TYPE, ATTRIBUTE_KEY, ATTRIBUTE_VALUES[i]);
            assert false;
            return null;
        }
    }

    private static class TextColorDecorator extends EchoTextExpression {
        private static final String[] DEFINED_EXPRESSIONS = {"!color", "!bgcolor"};
        private static final String[] ATTRIBUTE_KEYS = {"text-color", "!bgcolor"};

        TextColorDecorator(String expString, String expression, String rawValue, String arguments) {
            super(expString, expression, rawValue, arguments);
        }

        Element.Attribute attribute() {
            for (int i = 0; i< DEFINED_EXPRESSIONS.length; i++)
                if (expression().equals(DEFINED_EXPRESSIONS[i]))
                    return SimpleElement.newAttribute(ATTRIBUTE_TYPE, ATTRIBUTE_KEYS[i], arguments());
            assert false;
            return null;
        }
    }
}
