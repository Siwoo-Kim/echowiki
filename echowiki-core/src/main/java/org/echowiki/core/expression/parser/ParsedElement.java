package org.echowiki.core.expression.parser;

import java.util.List;

public interface ParsedElement {

    String raw();

    String arguments();

    String value();

    String expression();

    List<ParsedElement> children();

}
