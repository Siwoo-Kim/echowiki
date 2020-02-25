package org.echowiki.core.expression.element;

public class EchoElement extends AbstractElement implements Element {

    @Override
    public Scope type() {
        return Scope.LINE;
    }

}
