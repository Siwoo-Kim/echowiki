package org.echowiki.core.expression.element;

import static org.echowiki.core.expression.element.AttributeType.*;

public enum AttributeKey {
    WIKI_LITERAL(LITERAL, "echo-literal"),
    WIKI_LINK(LINK, "echo-link"),
    WIKI_LIST_REGULAR(LIST, "echo-line"),
    WIKI_LIST_NUMBER(LIST, "echo-line"),
    WIKI_NOTE(NOTE, "echo-note"),
    WIKI_TEXT_SIZE(TEXT, "echo-textsize"),
    WIKI_TEXT_STYLE(TEXT, "echo-text"),
    WIKI_TEXT_COLOR(TEXT, "echo-text-color"),
    WIKI_TEXT_BGCOLOR(TEXT, "echo-text-bgcolor"),
    WIKI_PARAGRAPH(PARAGRAPH, "wiki-p");

    private final AttributeType type;
    private final String key;

    AttributeKey(AttributeType type, String key) {
        this.type = type;
        this.key = key;
    }

    public AttributeType type() {
        return type;
    }

    public String key() {
        return key;
    }
}
