package org.echowiki.core.expression;

import org.echowiki.core.expression.element.Element;
import org.echowiki.core.expression.element.WIKI;

/**
 * The class represents the paragraph of the wiki page.
 * <p>
 * == {p:value} ==
 * == {/p} ==
 * <p>
 * == topic1 ==
 * == {/} ==
 */
public class ParagraphScopeExpression extends AbstractScopeExpression {

    WIKI WIKI_PARAGRAPH = WIKI.WIKI_PARAGRAPH;
    private final String[] IDENTIFIERS = new String[]{"", "p"};

    ParagraphScopeExpression(String expString, String expression, String rawValue, String arguments, String wrapper) {
        super(expString, expression, rawValue, arguments, wrapper);
    }

    @Override
    void hookElement(Element el) {
        el.addValue(WIKI_PARAGRAPH.key(), "title=" + value());
        el.addValue(WIKI_PARAGRAPH.key(), "depth=" + value());
    }

    @Override
    String[] identifiers() {
        return IDENTIFIERS.clone();
    }
}
