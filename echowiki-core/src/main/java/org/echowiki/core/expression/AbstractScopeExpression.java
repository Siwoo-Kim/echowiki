package org.echowiki.core.expression;


import org.echowiki.core.expression.element.Element;
import org.echowiki.core.expression.element.Scope;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public abstract class AbstractScopeExpression extends AbstractExpression implements ScopeExpression {

    public static final Scope type = Scope.SECTION;
    private static final String OPENING_BRACKET = "==";
    private static final String CLOSING_BRACKET = "==";
    private final List<Expression> echoExpressions = new ArrayList<>();
    private final int depth;
    private final boolean closed;
    private int listIndex = EchoListExpression.FIRST_ITEM;

    AbstractScopeExpression(String expString) {
        super(expString, null, null, null);
        depth = 0;
        closed = false;
    }

    AbstractScopeExpression(String expString, String expression, String rawValue, String arguments, String wrapper) {
        super(expString, expression, rawValue, arguments);
        checkArgument(wrapper.startsWith(OPENING_BRACKET)
                && wrapper.endsWith(CLOSING_BRACKET));

        String lastLine = StringHelper.lastLineOf(expString).trim();
        if (lastLine != null)
            closed = lastLine.startsWith(OPENING_BRACKET)
                    && lastLine.endsWith(CLOSING_BRACKET);
        else
            closed = false;
        int depth = wrapper.split(" ")[0].length();
        this.depth = depth - 2;
    }

    public List<Expression> getExpressionInScope() {
        return new ArrayList<>(echoExpressions);
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
    Scope getElementType() {
        return type;
    }

    @Override
    public void addExpression(Expression expression) {
        if (expression instanceof AbstractScopeExpression)
            super.addExpression(expression);
        else if (!echoExpressions.contains(expression)) {
            echoExpressions.add(expression);
            addListener(expression);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Expression exp = (Expression) evt.getSource();
        if (exp instanceof EchoListExpression) {
            Element el = (Element) evt.getNewValue();
            EchoListExpression listExp = (EchoListExpression) exp;
            this.listIndex = listExp.handleIndexOfList(el, listIndex);
        }
    }
}
