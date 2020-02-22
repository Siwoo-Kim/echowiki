package org.echowiki.core.expression;


public class EchoLinkDocumentExpression extends AbstractEchoExpression {

    public static final String[] DEFINED_EXPRESSIONS = {"@"};

    EchoLinkDocumentExpression(String expString, String expression, String rawValue, String arguments) {
        super(expString, expression, rawValue, arguments);
    }

    @Override
    protected void hookElement(Element el) {

    }
}
