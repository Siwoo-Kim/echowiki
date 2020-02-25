package org.echowiki.core.expression;

import org.echowiki.core.expression.element.*;

/**
 * {@(docId):OutPut Message}
 */
public class EchoLinkDocumentExpression extends AbstractEchoExpression {

    private final WIKI WIKI_LINK = WIKI.WIKI_LINK;
    public static final String[] IDENTIFIERS = {"@"};

    EchoLinkDocumentExpression(String expString, String expression, String rawValue, String arguments) {
        super(expString, expression, rawValue, arguments);
    }

    @Override
    protected void hookElement(Element el) {
        el.addValue(WIKI_LINK.key(), arguments());
    }

    @Override
    String[] identifiers() {
        return IDENTIFIERS.clone();
    }

}
