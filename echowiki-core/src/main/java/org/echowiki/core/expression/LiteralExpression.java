package org.echowiki.core.expression;

import org.echowiki.core.expression.meta.AttributeType;
import org.echowiki.core.expression.meta.ElementType;

public class LiteralExpression extends AbstractExpression {

    private static final ElementType TYPE = ElementType.COMMON;
    private static final String ATTRIBUTE_KEY = "literal";

    LiteralExpression(String expString, String expression, String rawValue, String arguments) {
        super(expString, expression, rawValue, arguments);
    }

    @Override
    public String value() {
        return super.expressionString();
    }

    @Override
    protected void hookElement(Element el) {
        el.addAttribute(SimpleElement.newAttribute(AttributeType.LITERAL, ATTRIBUTE_KEY, value()));
    }

    @Override
    protected ElementType getElementType() {
        return TYPE;
    }

}
