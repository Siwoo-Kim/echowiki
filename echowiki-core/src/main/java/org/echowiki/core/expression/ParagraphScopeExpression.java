package org.echowiki.core.expression;

import org.echowiki.core.expression.element.AttributeKey;
import org.echowiki.core.expression.element.Element;
import org.echowiki.core.expression.element.SimpleAttribute;

/**
 * The class represents the paragraph of the wiki page.
 * <p>
 * == {p:value} ==
 * == {/p} ==
 */
public class ParagraphScopeExpression extends AbstractScopeExpression {

    AttributeKey WIKI_PARAGRAPH = AttributeKey.WIKI_PARAGRAPH;

    ParagraphScopeExpression(String expString, String expression, String rawValue, String arguments, String wrapper) {
        super(expString, expression, rawValue, arguments, wrapper);
    }

    @Override
    void hookElement(Element el) {
        el.addAttribute(new SimpleAttribute(WIKI_PARAGRAPH.type(), WIKI_PARAGRAPH.key(), "title=" + value()));
        el.addAttribute(new SimpleAttribute(WIKI_PARAGRAPH.type(), WIKI_PARAGRAPH.key(), "depth=" + depth()));
    }
}
