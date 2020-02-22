package org.echowiki.core.expression;

public class EchoListExpression extends AbstractEchoExpression {
    private static final String[] DEFINED_EXPRESSIONS = {"li", "nli"};

    EchoListExpression(String expString, String expression, String rawValue, String arguments) {
        super(expString, expression, rawValue, arguments);
    }

    @Override
    protected void hookElement(Element el) {

    }
}
