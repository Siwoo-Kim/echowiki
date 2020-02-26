package org.echowiki.core.expression;

import org.echowiki.core.expression.element.WIKI;
import org.echowiki.core.expression.element.Element;
import org.echowiki.core.expression.element.Scope;

/**
 * The class {@link LiteralExpression} represents the literal expression in the wiki syntax.
 *
 * meta data format {literal=[literal value]}
 */
public class LiteralExpression extends AbstractExpression {

    private static final WIKI WIKI_LITERAL = WIKI.WIKI_LITERAL;

    LiteralExpression(String expString, String expression, String rawValue, String arguments) {
        super(expString, expression, rawValue, arguments);
    }

    @Override
    public String value() {
        return super.expressionString();
    }

    @Override
    protected void hookElement(Element el) {
        el.addValue(WIKI_LITERAL.key(), "literal=" + getLiteral());
    }

    @Override
    protected Scope getElementType() {
        return WIKI_LITERAL.type();
    }

    @Override
    String[] identifiers() {
        return new String[0];
    }

}
