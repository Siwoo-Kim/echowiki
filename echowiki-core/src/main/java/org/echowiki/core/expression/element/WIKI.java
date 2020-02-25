package org.echowiki.core.expression.element;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The enum {@link WIKI} represents the functionalities on the view.
 *
 */
public enum WIKI {
    WIKI_LITERAL(Scope.LINE, "wiki-literal"),
    WIKI_LINK(Scope.LINE, "wiki-link"),
    WIKI_LIST(Scope.LINE, "wiki-line"),
    WIKI_NOTE(Scope.LINE, "echo-note"),
    WIKI_TEXT(Scope.LINE, "echo-text"),
    WIKI_PARAGRAPH(Scope.SECTION, "wiki-paragraph");

    private static final Map<String, WIKI> maps = Arrays.stream(WIKI.values())
            .collect(Collectors.toMap(WIKI::key, Function.identity()));

    private final Scope scope;
    private final String key;

    WIKI(Scope scope, String key) {
        this.scope = scope;
        this.key = key;
    }

    public static WIKI fromString(String key) {
        checkNotNull(key);
        for (String k : maps.keySet())
            if (k.equalsIgnoreCase(key))
                return maps.get(k);
        throw new UnsupportedOperationException(String.format("Enum WIKI for [%s]", key));
    }

    public Scope type() {
        return scope;
    }

    public String key() {
        return key;
    }
}
