package org.echowiki.core.expression;

import org.echowiki.core.expression.element.AttributeKey;
import org.echowiki.core.expression.element.AttributeType;
import org.echowiki.core.expression.element.Element;
import org.echowiki.core.expression.element.SimpleAttribute;

/**
 * {+(linking message): Note Message}
 */
public class EchoNoteExpression extends AbstractEchoExpression {
    private static final String[] DEFINED_EXPRESSIONS = {"+"};
    private static final AttributeKey WIKI_NOTE = AttributeKey.WIKI_NOTE;

    EchoNoteExpression(String expString, String expression, String rawValue, String arguments) {
        super(expString, expression, rawValue, arguments);
    }

    @Override
    protected void hookElement(Element el) {
        el.addAttribute(new SimpleAttribute(WIKI_NOTE.type(), WIKI_NOTE.key(), arguments()));
    }
}
