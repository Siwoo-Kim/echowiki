package org.echowiki.core.expression;

import org.echowiki.core.expression.meta.AttributeType;

//@todo better way to specifying line elements.
public abstract class EchoListExpression extends AbstractEchoExpression {
    private static final AttributeType type = AttributeType.LIST;

    EchoListExpression(String expString, String expression, String rawValue, String arguments) {
        super(expString, expression, rawValue, arguments);
    }

    static AbstractEchoExpression newInstance(String expString, String expression, String rawValue, String arguments) {
        switch (expression) {
            case "li":
                return new EchoRegularListExpression(expString, expression, rawValue, arguments);
            case "nli":
                return new EchoNumberListExpression(expString, expression, rawValue, arguments);
        }
        throw new MalformedExpressionException(String.format("Unknown Expression [%s]", expString));
    }

    @Override
    protected void hookElement(Element el) {
        el.addAttribute(attribute());
    }

    abstract Element.Attribute attribute();

    private static class EchoRegularListExpression extends EchoListExpression {
        private static final String DEFINED_EXPRESSION = "li";

        public EchoRegularListExpression(String expString, String expression, String rawValue, String arguments) {
            super(expString, expression, rawValue, arguments);
        }

        @Override
        Element.Attribute attribute() {
            return SimpleElement.newAttribute(type, "echo-line", "regular");
        }
    }

    private static class EchoNumberListExpression extends EchoListExpression {
        private static final String DEFINED_EXPRESION = "nli";

        EchoNumberListExpression(String expString, String expression, String rawValue, String arguments) {
            super(expString, expression, rawValue, arguments);
        }

        @Override
        Element.Attribute attribute() {
            return SimpleElement.newAttribute(type, "echo-line", "number");
        }
    }
}
