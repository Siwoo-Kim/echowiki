package org.echowiki.core.expression.element;

public class LineElement extends AbstractElement implements Element {

    @Override
    public Scope type() {
        return Scope.LINE;
    }

}
