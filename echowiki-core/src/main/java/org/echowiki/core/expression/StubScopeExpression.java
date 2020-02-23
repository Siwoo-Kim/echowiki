package org.echowiki.core.expression;

import org.echowiki.core.expression.element.Element;

public class StubScopeExpression extends AbstractScopeExpression implements ScopeExpression {

    StubScopeExpression(String expString) {
        super(expString);
    }

    @Override
    void hookElement(Element el) {

    }
}
