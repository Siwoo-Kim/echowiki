package org.echowiki.core.expression;

import org.echowiki.core.expression.element.Element;
import org.echowiki.core.expression.element.WIKI;

/**
 * {+(linking message): Note Message}
 */
public class EchoNoteExpression extends AbstractEchoExpression {
    private static final String[] IDENTIFIER = {"+"};
    private static final WIKI WIKI_NOTE = WIKI.WIKI_NOTE;

    EchoNoteExpression(String expString, String expression, String rawValue, String arguments) {
        super(expString, expression, rawValue, arguments);
    }

    @Override
    protected void hookElement(Element el) {
        el.addValue(WIKI_NOTE.key(), "message=" + arguments());
        el.addValue(WIKI_NOTE.key(), "note=" + getLiteral());
    }

    @Override
    String[] identifiers() {
        return IDENTIFIER;
    }
}
