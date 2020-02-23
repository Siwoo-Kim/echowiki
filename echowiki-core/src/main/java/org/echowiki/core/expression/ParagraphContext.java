package org.echowiki.core.expression;

import org.echowiki.core.expression.element.Element;

import java.util.List;

public interface ParagraphContext extends Iterable<Element> {

    Expression getContext();

    List<Element> getElements();

    String encodedString();

    String decodedString();

    String[] encodedLines();

    Element indexAt(int index);

    Element indexAt(String index);

}
