package org.echowiki.core.expression;

import org.apache.logging.log4j.util.Strings;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkElementIndex;

public abstract class AbstractExpressionParser implements ExpressionParser {

    public static final char ESCAPE = '\\';

    boolean isCharEscaped(String expString, int start) {
        checkElementIndex(start, expString.length());
        if (start == 0) return false;
        return expString.charAt(start - 1) == ESCAPE;
    }

    boolean isWrapped(String expString, char openBracket, char closeBracket) {
        if (Strings.isBlank(expString)) return false;
        expString = expString.trim();
        return expString.charAt(0) == openBracket
                && expString.charAt(expString.length() - 1) == closeBracket;
    }

    String stripOffWrapper(String expString, char openBracket, char closeBracket) {
        expString = expString.trim();
        checkArgument(expString.charAt(0) == openBracket);
        checkArgument(expString.charAt(expString.length() - 1) == closeBracket);
        return expString.substring(1, expString.length() - 1);
    }

}
