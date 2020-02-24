package org.echowiki.core.expression;

import org.echowiki.core.expression.element.Element;

/**
 * The class represents the stub expression {@link StubScopeExpression}.
 * The stub expression does not included in the topics in the document and also recognize as the orphan scope.
 *
 */
public class StubScopeExpression extends AbstractScopeExpression implements ScopeExpression {

    StubScopeExpression(String expString) {
        super(expString);
    }

    @Override
    void hookElement(Element el) {

    }
}
