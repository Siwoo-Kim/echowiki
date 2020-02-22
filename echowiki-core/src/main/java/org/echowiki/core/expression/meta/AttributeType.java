package org.echowiki.core.expression.meta;

import static org.echowiki.core.expression.meta.ElementType.*;

public enum AttributeType {
    PARAGRAPH(SCOPE), ESCAPE(SCOPE), HTML(SCOPE), TABLE(SCOPE),
    TEXT(ECHO), STYLE(ECHO), LINK(ECHO), NOTE(ECHO), LIST(ECHO), LITERAL(COMMON);

    ElementType elementType;

    AttributeType(ElementType elementType) {
        this.elementType = elementType;
    }
}
