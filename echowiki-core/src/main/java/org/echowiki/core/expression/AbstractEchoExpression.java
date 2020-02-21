package org.echowiki.core.expression;

public class AbstractEchoExpression implements Expression {

    String expString;
    String expression;
    Expression innerExpression;
    String arguments;
    String rawValue;

    AbstractEchoExpression(String expString, String expression, String rawValue, String arguments) {
        this.expString = expString;
        this.expression = expression;
        this.rawValue = rawValue;
        this.arguments = arguments;
    }

    @Override
    public String expression() {
        return expression;
    }

    @Override
    public String arguments() {
        return arguments;
    }

    @Override
    public String expressionString() {
        return expString;
    }

    @Override
    public Expression innerExpression() {
        return innerExpression;
    }

    @Override
    public String value() {
        return rawValue;
    }

}
