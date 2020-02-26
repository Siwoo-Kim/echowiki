package org.echowiki.core.expression;

import org.echowiki.core.expression.element.Element;

import java.util.List;

/**
 * The interface represents the paragraph of the wiki document.
 * {@link Paragraph} should have at least root {@link ScopeExpression}.
 * {@link Paragraph} might have more than one wiki {@link Expression}.
 * The {@link Paragraph} tracks the element in the encoded string as [%echo[index]] format.
 *
 */
public interface Paragraph extends Iterable<Element> {

    String ECHO_ID_SYMBOL = "[@echo%d]";

    /**
     * returns {@link ScopeExpression} of the {@link Paragraph}.
     *
     * @return
     */
    Element root();

    /**
     * returns wiki {@link Expression} in the paragraphs.
     *
     * @return
     */
    List<Element> getElements();

    String encodedString();

    String rawString();

    String[] encodedLines();

    /**
     * returns the element positioned at given index.
     *
     * @throws IllegalArgumentException if {@code getElements().size() <= index}
     * @param index
     * @return
     */
    Element indexAt(int index);

    Element indexAt(String index);

}
