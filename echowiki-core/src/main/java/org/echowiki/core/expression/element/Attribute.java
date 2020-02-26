package org.echowiki.core.expression.element;

import java.util.List;

/**
 * The class represents the meta data of the {@link Element}.
 * {@link Attribute} has its own key to identify its functionality {@link WIKI} in the view.
 * Note that the value in the {@link Attribute} is consists of {name=data} format.
 *
 * Meta data could have more than one values for each key.
 * Each data could be consists of {name=data} format.
 *
 * @see Element
 * @see WIKI defined functionality or functoinalities' grouping for the {@link Attribute}
 */
public interface Attribute {

    /**
     * returns the type of given {@link Attribute}
     *
     * @return
     */
    WIKI wiki();

    /**
     * returns the key of the {@link Attribute}
     *
     * @return
     */
    String key();

    /**
     * Add given value in the {@link Attribute}
     *
     * @param value
     */
    void addValue(String value);

    /**
     * returns all the values in the {@link Attribute}
     *
     * @return
     */
    List<String> values();
}
