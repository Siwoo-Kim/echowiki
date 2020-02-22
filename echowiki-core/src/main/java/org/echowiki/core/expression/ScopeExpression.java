package org.echowiki.core.expression;

public interface ScopeExpression extends Expression {

    int depth();

    boolean closed();

}
