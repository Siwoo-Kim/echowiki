package org.echowiki.core.expression;

public class EchoLiteralExpression implements Expression {
    private final String literal;

    EchoLiteralExpression(String literal) {
        this.literal = literal;
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
        return literal;
    }

    @Override
    public Expression innerExpression() {
        return null;
    }

    @Override
    public String value() {
        return null;
    }
}
