package org.echowiki.core.expression;

import com.google.common.base.MoreObjects;
import com.sun.istack.Nullable;
import org.apache.logging.log4j.util.Strings;
import org.echowiki.core.expression.element.Element;
import org.echowiki.core.expression.element.Elements;
import org.echowiki.core.expression.element.Scope;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@inheritDoc}
 * <p>
 * The class {@link AbstractEchoExpression} provides an ability of observing nested {@link Expression}'s evaluation point for it's subclass.
 * Note that the class only supports observation for nested {@link Expression} which also extends {@link AbstractExpression}.
 * <p>
 * To get notification from the evaluation event of nested {@link Expression}, override {@link #propertyChange(PropertyChangeEvent)} method.
 */
public abstract class AbstractExpression implements Expression, PropertyChangeListener {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private final String expression;
    private final String arguments;
    private final String expressionString;
    private final String value;
    private Expression innerExpression;

    AbstractExpression(String expString, String expression, String rawValue, String arguments) {
        checkArgument(Strings.isNotBlank(expString));
        this.expressionString = expString;
        this.expression = expression;
        this.value = rawValue;
        this.arguments = arguments;
    }

    /**
     * add given {@link Expression} as internal expression.
     *
     * @param expression
     */
    public void addExpression(Expression expression) {
        addListener(expression);
        this.innerExpression = expression;
    }

    void addListener(Expression expression) {
        if (expression instanceof AbstractExpression) {
            AbstractExpression instance = (AbstractExpression) expression;
            instance.support.addPropertyChangeListener(this);
        } else {
            throw new UnsupportedOperationException(String.format("Unsupported registering listener for class [%s]", expression.getClass()));
        }
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
        return expressionString;
    }

    @Override
    public Expression innerExpression() {
        return innerExpression;
    }

    @Override
    public String value() {
        return value;
    }

    /**
     * Evaluate from the nested expression to outer.
     *
     * @return
     */
    @Override
    public Element evaluate() {
        Element el;
        if (innerExpression != null)
            el = innerExpression.evaluate();    //from nested
        else
            el = Elements.newElement(getElementType());
        hookElement(el);
        support.firePropertyChange(new PropertyChangeEvent(this, expression, null, el));
        return el;
    }

    @Nullable
    String getLiteral() {
        Expression e = this;
        while (e != null && !(e instanceof LiteralExpression))
            e = e.innerExpression();
        if (e != null)
            return e.expressionString();
        else
            return null;
    }

    /**
     * @param el
     * @implSpec subclass should setup it's attributes in given {@link Element}.
     */
    abstract void hookElement(Element el);

    /**
     * @return
     * @implSpec subclass should define it's {@link Scope}.
     */
    abstract Scope getElementType();

    /**
     * returns id of the expression
     *
     * @return
     */
    abstract String[] identifiers();

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .omitNullValues()
                .add("exp", expression)
                .add("args", arguments)
                .add("value", value)
                .toString();
    }
}
