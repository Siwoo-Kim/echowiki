package org.echowiki.core.expression;

import org.echowiki.core.expression.element.Element;
import org.echowiki.core.expression.element.Elements;
import org.echowiki.core.expression.element.Scope;
import org.echowiki.core.expression.element.WIKI;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTestElement {

    @Test
    public void unitTestLineElement() {
        Element element = Elements.newElement(Scope.LINE);
        element.addValue(WIKI.WIKI_TEXT.key(), "style=bold");
        element.addValue(WIKI.WIKI_TEXT.key(), "size=10");
        assertEquals(element.type(), Scope.LINE);
        assertTrue(element.getAttribute(WIKI.WIKI_TEXT).values().contains("style=bold"));
        assertTrue(element.getAttribute(WIKI.WIKI_TEXT).values().contains("size=10"));
        assertEquals(2, element.getAttribute(WIKI.WIKI_TEXT).values().size());

        element.addValue(WIKI.WIKI_TEXT.key(), "style=italic");
        assertFalse(element.getAttribute(WIKI.WIKI_TEXT).values().contains("style=bold"));
        assertTrue(element.getAttribute(WIKI.WIKI_TEXT).values().contains("style=italic"));
        assertTrue(element.getAttribute(WIKI.WIKI_TEXT).values().contains("size=10"));
        assertEquals(2, element.getAttribute(WIKI.WIKI_TEXT).values().size());
    }

    @Test
    public void unitTestSectionElement() {
        Element element = Elements.newElement(Scope.SECTION);
        element.addValue(WIKI.WIKI_TEXT.key(), "doc=Korea");
        element.addValue(WIKI.WIKI_TEXT.key(), "title=Korea History");
        assertEquals(element.type(), Scope.SECTION);
        assertTrue(element.getAttribute(WIKI.WIKI_TEXT).values().contains("doc=Korea"));
        assertTrue(element.getAttribute(WIKI.WIKI_TEXT).values().contains("title=Korea History"));
        assertEquals(2, element.getAttribute(WIKI.WIKI_TEXT).values().size());

        element.addValue(WIKI.WIKI_TEXT.key(), "title=Korea War");
        assertFalse(element.getAttribute(WIKI.WIKI_TEXT).values().contains("title=Korea History"));
        assertTrue(element.getAttribute(WIKI.WIKI_TEXT).values().contains("title=Korea War"));
        assertTrue(element.getAttribute(WIKI.WIKI_TEXT).values().contains("doc=Korea"));
        assertEquals(2, element.getAttribute(WIKI.WIKI_TEXT).values().size());
    }
}
