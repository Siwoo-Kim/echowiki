package org.echowiki.core.expression.scope;

import org.echowiki.core.expression.Expression;

import java.util.List;
import java.util.Observer;

public interface ScopeExpression extends Expression, Observer {

    List<Expression> innerExpression();

}
