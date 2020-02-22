package org.echowiki.core.expression;

import org.echowiki.core.expression.element.AttributeKey;
import org.echowiki.core.expression.element.Element;
import org.echowiki.core.expression.element.ElementType;
import org.echowiki.core.expression.element.SimpleAttribute;

public class LiteralExpression extends AbstractExpression {

    private static final AttributeKey WIKI_LITERAL = AttributeKey.WIKI_LITERAL;

    LiteralExpression(String expString, String expression, String rawValue, String arguments) {
        super(expString, expression, rawValue, arguments);
    }

    @Override
    public String value() {
        return super.expressionString();
    }

    @Override
    protected void hookElement(Element el) {
        el.addAttribute(new SimpleAttribute(WIKI_LITERAL.type(), WIKI_LITERAL.key(), value()));
    }

    @Override
    protected ElementType getElementType() {
        return WIKI_LITERAL.type().type();
    }

}
