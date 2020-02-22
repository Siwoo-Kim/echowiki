package org.echowiki.core.expression;

import org.echowiki.core.expression.meta.AttributeType;

public class EchoLinkDocumentExpression extends AbstractEchoExpression {

    public static final String[] DEFINED_EXPRESSIONS = {"@"};

    EchoLinkDocumentExpression(String expString, String expression, String rawValue, String arguments) {
        super(expString, expression, rawValue, arguments);
    }

    @Override
    protected void hookElement(Element el) {
        el.addAttribute(SimpleElement.newAttribute(AttributeType.LINK, "wiki-link", arguments()));
    }
}
