package org.echowiki.core.expression;

import org.apache.logging.log4j.util.Strings;

import static com.google.common.base.Preconditions.checkElementIndex;

public final class StringHelper {

    public static final String NEW_LINE = "\n";
    public static final char ESCAPE = '\\';

    private StringHelper() {};

    public static String lastLineOf(String str) {
        if (Strings.isBlank(str)) return str;
        String[] lines = str.split(NEW_LINE);
        return lines[lines.length-1];
    }

    public static boolean isCharEscaped(String expString, int start) {
        checkElementIndex(start, expString.length());
        if (start == 0) return false;
        return expString.charAt(start - 1) == ESCAPE;
    }
}
