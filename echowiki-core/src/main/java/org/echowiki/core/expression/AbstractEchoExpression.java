package org.echowiki.core.expression;

import org.echowiki.core.expression.element.Scope;

public abstract class AbstractEchoExpression extends AbstractExpression {

    public AbstractEchoExpression(String expString, String expression, String rawValue, String arguments) {
        super(expString, expression, rawValue, arguments);
    }

    @Override
    Scope getElementType() {
        return Scope.LINE;
    }
}
