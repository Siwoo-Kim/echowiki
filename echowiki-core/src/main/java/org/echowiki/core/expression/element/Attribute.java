package org.echowiki.core.expression.element;

/**
 * The class represents the meta data of the {@link Element}
 *
 * @see Element
 */
public interface Attribute {

    /**
     * returns the type of given {@link Attribute}
     *
     * @return
     */
    AttributeType type();

    /**
     * returns the key of the {@link Attribute}
     *
     * @return
     */
    String key();

    /**
     * returns the value of the {@link Attribute}
     *
     * @return
     */
    String value();

}
