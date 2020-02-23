package org.echowiki.core.expression;

import java.util.List;

public interface ScopeExpression extends Expression {

    int depth();

    List<Expression> getExpressionInScope();

    boolean closed();

}
