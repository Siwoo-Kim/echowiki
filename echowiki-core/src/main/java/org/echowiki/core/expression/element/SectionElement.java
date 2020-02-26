package org.echowiki.core.expression.element;

public class SectionElement extends AbstractElement implements Element {

    @Override
    public Scope type() {
        return Scope.SECTION;
    }

}
