package org.echowiki.core.expression.parser;

import java.util.List;

public interface ExpressionParser {

    List<ParsedElement> parse(String raw);

}
