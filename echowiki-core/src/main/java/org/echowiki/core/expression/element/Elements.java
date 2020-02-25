package org.echowiki.core.expression.element;

import static com.google.common.base.Preconditions.checkNotNull;

public final class Elements {

    private Elements() {}

    public static Attribute newAttribute(String key) {
        checkNotNull(key);
        return new SimpleAttribute(key);
    }

    public static Attribute newAttribute(String key, String value) {
        checkNotNull(key, value);
        SimpleAttribute attribute = new SimpleAttribute(key);
        attribute.addValue(value);
        return attribute;
    }

    public static Element newElement(Scope scope) {
        switch (scope) {
            case LINE:
            case COMMON:
                return new EchoElement();
            case SECTION: return new ScopeElement();
        }
        throw new UnsupportedOperationException();
    }
}
