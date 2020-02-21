package org.echowiki.core.expression;

import com.sun.istack.Nullable;

public class EchoExpressionFactory {

    private static final String TEXT_EXPRESSION = "!";
    private static final String TEXT_SIZE_EXPRESSION = "!size";
    private static final String TEXT_COLOR_EXPRESSION = "!color";
    private static final String TEXT_BG_COLOR_EXPRESSION = "!bgcolor";
    private static final String LINK_DOCUMENT_EXPRESSION = "@";
    private static final String LIST_SIMPLE_EXPRESSION = "li";
    private static final String LIST_NUMBER_EXPRESSION = "nli";
    private static final String NOTE_EXPRESSION = "+";

    public static Expression newInstance(String expString, String expression, @Nullable String value, @Nullable String arguments) {
        expression = expression.trim();
        Expression instance = null;
        switch (expression) {
            case TEXT_EXPRESSION:
                instance = new EchoTextExpression(expString, expression, value, arguments);
                break;
            case TEXT_SIZE_EXPRESSION:
                instance = new EchoTextExpression(expString, expression, value, arguments);
                break;
            case TEXT_COLOR_EXPRESSION:
                instance = new EchoTextExpression(expString, expression, value, arguments);
                break;
            case TEXT_BG_COLOR_EXPRESSION:
                instance = new EchoTextExpression(expString, expression, value, arguments);
                break;
            case LINK_DOCUMENT_EXPRESSION:
                instance = new EchoLinkDocumentExpression(expString, expression, value, arguments);
                break;
            case LIST_SIMPLE_EXPRESSION:
                instance = new EchoListExpression(expString, expression, value, arguments);
                break;
            case LIST_NUMBER_EXPRESSION:
                instance = new EchoListExpression(expString, expression, value, arguments);
                break;
            case NOTE_EXPRESSION:
                instance = new EchoNoteExpression(expString, expression, value, arguments);
                break;
        }
        return instance;
    }
}
