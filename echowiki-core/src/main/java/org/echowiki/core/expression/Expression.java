package org.echowiki.core.expression;

import com.sun.istack.Nullable;

/**
 * The interface represents Expression.
 * Expression has an format of {expression(arg1, agr2..):values} and also might contains nested expression {@code innerExpression}.
 * Notify that the sequence of evaluation is from the nested to the outer expressions.
 *
 */
public interface Expression {

    /**
     * returns the arguments of the expression from the given {@code expressionString}
     * eg) {!color(orange):This is value} => !color
     *
     * @return
     */
    @Nullable
    String expression();

    /**
     * returns the arguments of the expression from the given {@code expressionString}
     * eg) {!color(orange):This is value} => orange
     *
     * @return
     */
    @Nullable
    String arguments();

    /**
     * returns the original {@link Expression} string.
     *
     * @return
     */
    String expressionString();

    /**
     * returns the inner {@link Expression} of the expression.
     * eg) {!color(orange):{!:'This is bold text'}} => inner {@link Expression} of "{!:'This is bold text'}"
     *
     * @return
     */
    @Nullable
    Expression innerExpression();

    /**
     * returns the value of the {@link Expression}.
     *
     * eg) {!color(orange):This is value} => This is value
     * eg) {!color(orange):{!:'This is bold text'}} => "{!:'This is bold text'}"
     *
     * @return
     */
    @Nullable
    String value();

    /**
     * returns the evaluated element from the {@link Expression}
     *
     * @return
     */
    Element evaluate();

}
