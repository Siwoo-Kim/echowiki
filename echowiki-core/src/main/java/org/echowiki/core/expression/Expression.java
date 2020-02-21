package org.echowiki.core.expression;

import com.sun.istack.Nullable;

public interface Expression {

    @Nullable
    String expression();

    @Nullable
    String arguments();

    String expressionString();

    @Nullable
    Expression innerExpression();

    @Nullable
    String value();

}
