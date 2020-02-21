package org.echowiki.core.expression;

public class LiteralExpression implements Expression {

    String value;

    public LiteralExpression(String value) {
        this.value = value;
    }

    @Override
    public String expression() {
        return null;
    }

    @Override
    public String arguments() {
        return null;
    }

    @Override
    public String expressionString() {
        return value;
    }

    @Override
    public Expression innerExpression() {
        return null;
    }

    @Override
    public String value() {
        return value;
    }
}
