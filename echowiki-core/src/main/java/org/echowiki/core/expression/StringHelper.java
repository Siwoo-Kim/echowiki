package org.echowiki.core.expression;

import org.apache.logging.log4j.util.Strings;

public final class StringHelper {

    public static final String NEW_LINE = "\n";

    private StringHelper() {};

    public static String lastLineOf(String str) {
        if (Strings.isBlank(str)) return str;
        String[] lines = str.split(NEW_LINE);
        return lines[lines.length-1];
    }
}
