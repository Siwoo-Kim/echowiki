package org.echowiki.core.expression.scope;

import javafx.beans.InvalidationListener;
import org.echowiki.core.expression.Expression;

import java.util.List;
import java.util.Observable;

public class ParagraphExpression implements ScopeExpression {

    private static final String OPEN = "<p>";
    private static final String CLOSE = "</p>";

    private String opening;
    private String closing;
    private String heading;

    @Override
    public List<Expression> innerExpression() {
        return null;
    }

    @Override
    public List<Expression> children() {
        return null;
    }

    @Override
    public String open() {
        return OPEN;
    }

    @Override
    public String close() {
        return CLOSE;
    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
