package org.echowiki.core.expression;

import org.echowiki.core.expression.element.*;

/**
 * The class represents the link (referer) of the other document.
 *
 * meta data format {doc=[docId],message=[message string]}
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
        el.addValue(WIKI_LINK.key(), "doc=" + arguments());
        el.addValue(WIKI_LINK.key(), "message=" + getLiteral());
    }

    @Override
    String[] identifiers() {
        return IDENTIFIERS.clone();
    }

}
