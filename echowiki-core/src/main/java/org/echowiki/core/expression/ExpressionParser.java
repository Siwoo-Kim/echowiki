package org.echowiki.core.expression;

public interface ExpressionParser {

    /**
     * parse given string and returns the {@link Expression}
     *
     * @param expression
     * @return
     */
    Expression parse(String expression);

    /**
     * is the given string a expression?
     * @param expression
     * @return
     */
    boolean isExpression(String expression);

    /**
     * is the given string contains one or more expressions?
     *
     * @param string
     * @return
     */
    boolean hasExpression(String string);

}
