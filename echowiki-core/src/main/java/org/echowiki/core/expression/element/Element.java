package org.echowiki.core.expression.element;

import java.util.List;

/**
 * The class {@link Element} represents the rendered elements according to the Echo Wiki Expression syntax from the raw string.
 * If the given {@link org.echowiki.core.expression.Expression} is correct with defined syntax,
 * the {@link org.echowiki.core.expression.Expression} will be evaluated in the runtime and adding meta results
 * as the {@link Attribute} into the {@link Element}
 */
public interface Element {

    /**
     * returns the type {@link Scope} of the given {@link Element}
     *
     * @return
     * @see Scope
     */
    Scope type();

    /**
     *
     * @throws IllegalArgumentException if {@code key == null}
     */
    void addValue(String key, String value);


    /**
     * returns all attributes of the given {@link Element}
     *
     * @return
     */
    List<Attribute> attributes();

    /**
     * returns the attribute of the given {@link Attribute} and key.
     *
     * @throws IllegalArgumentException {@code type == null || key == null}
     * @param key
     * @return
     */
    Attribute getAttribute(String key);

    Attribute getAttribute(WIKI wiki);

}
