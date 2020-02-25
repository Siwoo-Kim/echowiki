package org.echowiki.core.expression.element;

public class ScopeElement extends AbstractElement implements Element {

    @Override
    public Scope type() {
        return Scope.SECTION;
    }

}
