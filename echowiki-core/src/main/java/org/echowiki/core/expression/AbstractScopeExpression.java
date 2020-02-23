package org.echowiki.core.expression;


import org.echowiki.core.expression.element.ElementType;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public abstract class AbstractScopeExpression extends AbstractExpression implements ScopeExpression {

    public static final ElementType type = ElementType.SCOPE;
    private static final String OPENING_BRACKET = "==";
    private static final String CLOSING_BRACKET = "==";
    private final List<Expression> expressions = new ArrayList<>();
    private final int depth;
    private final boolean closed;

    AbstractScopeExpression(String expString, String expression, String rawValue, String arguments, String wrapper) {
        super(expString, expression, rawValue, arguments);
        checkArgument(wrapper.startsWith(OPENING_BRACKET)
                && wrapper.endsWith(CLOSING_BRACKET));

        String lastLine = StringHelper.lastLineOf(expString);
        if (lastLine != null)
            closed = lastLine.startsWith(OPENING_BRACKET)
                    && lastLine.endsWith(CLOSING_BRACKET);
        else
            closed = false;
        int depth = wrapper.split(" ")[0].length();
        this.depth = depth - 2;
    }

    @Override
    public boolean closed() {
        return closed;
    }

    @Override
    public int depth() {
        return depth;
    }

    @Override
    ElementType getElementType() {
        return type;
    }

    @Override
    public void addExpression(Expression expression) {
        super.addExpression(expression);
        if (!expressions.contains(expression))
            expressions.add(expression);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
