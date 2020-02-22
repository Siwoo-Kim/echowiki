package org.echowiki.core.expression;

import org.echowiki.core.expression.meta.AttributeType;

public class EchoNoteExpression extends AbstractEchoExpression {
    private static final String[] DEFINED_EXPRESSIONS = {"+"};

    EchoNoteExpression(String expString, String expression, String rawValue, String arguments) {
        super(expString, expression, rawValue, arguments);
    }

    @Override
    protected void hookElement(Element el) {
        el.addAttribute(SimpleElement.newAttribute(AttributeType.NOTE, "wiki-note", arguments()));
        el.addAttribute(SimpleElement.newAttribute(AttributeType.NOTE, "wiki-note-text", getLiteral()));
    }
}
