package org.echowiki.core.expression;

public class RegularScopeExpression implements ScopeExpression {
    public RegularScopeExpression(String expString) {
    }

    @Override
    public int depth() {
        return 0;
    }

    @Override
    public boolean closed() {
        return false;
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
        return null;
    }

    @Override
    public void addExpression(Expression innerExpression) {

    }

    @Override
    public Expression innerExpression() {
        return null;
    }

    @Override
    public String value() {
        return null;
    }

    @Override
    public Element evaluate() {
        return null;
    }
}
