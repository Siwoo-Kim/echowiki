package org.echowiki.core.expression;

import org.echowiki.core.expression.element.*;

/**
 * {@(docId):OutPut Message}
 */
public class EchoLinkDocumentExpression extends AbstractEchoExpression {

    private final AttributeKey WIKI_LINK = AttributeKey.WIKI_LINK;
    public static final String[] DEFINED_EXPRESSIONS = {"@"};

    EchoLinkDocumentExpression(String expString, String expression, String rawValue, String arguments) {
        super(expString, expression, rawValue, arguments);
    }

    @Override
    protected void hookElement(Element el) {
        el.addAttribute(new SimpleAttribute(WIKI_LINK.type(),
                WIKI_LINK.key(),
                arguments()));
    }
}
