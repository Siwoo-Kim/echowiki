package org.echowiki.core.expression;

import org.echowiki.core.expression.meta.AttributeType;

public class ParagraphScopeExpression extends AbstractScopeExpression {

    ParagraphScopeExpression(String expString, String expression, String rawValue, String arguments, String wrapper) {
        super(expString, expression, rawValue, arguments, wrapper);
    }

    @Override
    void hookElement(Element el) {
        el.addAttribute(SimpleElement.newAttribute(AttributeType.PARAGRAPH,
                "paragraph-title", getLiteral()));
    }
}
