package org.echowiki.core.expression.element;

import java.util.List;

import static org.echowiki.core.expression.element.ElementType.*;

public enum AttributeType {
    PARAGRAPH(SCOPE),
    ESCAPE(SCOPE),
    HTML(SCOPE),
    TABLE(SCOPE),
    TEXT(ECHO),
    STYLE(ECHO),
    LINK(ECHO),
    NOTE(ECHO),
    LIST(ECHO),
    LITERAL(COMMON);

    private final ElementType elementType;

    AttributeType(ElementType elementType) {
        this.elementType = elementType;
    }

    public ElementType type() {
        return elementType;
    }
}
