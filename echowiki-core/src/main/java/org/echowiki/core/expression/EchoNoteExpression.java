package org.echowiki.core.expression;

import org.echowiki.core.expression.meta.ElementType;

public class EchoNoteExpression extends AbstractEchoExpression {
    private static final String[] DEFINED_EXPRESSIONS = {"+"};

    EchoNoteExpression(String expString, String expression, String rawValue, String arguments) {
        super(expString, expression, rawValue, arguments);
    }

    @Override
    protected void hookElement(Element el) {

    }
}
