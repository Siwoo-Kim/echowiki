package org.echowiki.core.expression.element;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The class {@link Element} represents the rendered elements according to the Echo Wiki Expression syntax from the raw string.
 * If the given {@link org.echowiki.core.expression.Expression} is correct with defined syntax,
 * the {@link org.echowiki.core.expression.Expression} will be evaluated in the runtime and adding meta results
 * as the {@link Attribute} into the {@link Element}
 */
public interface Element {

    /**
     * returns the type {@link ElementType} of the given {@link Element}
     *
     * @return
     * @see ElementType
     */
    ElementType type();

    /**
     * @param attribute
     */
    void addAttribute(Attribute attribute);

    /**
     * returns the {@link Map} which is grouped by the {@link AttributeType}
     *
     * @see Attribute
     * @see AttributeType
     * @return
     */
    Map<AttributeType, Set<Attribute>> attributeGroups();

    /**
     * returns all attributes of the given {@link Element}
     *
     * @return
     */
    List<Attribute> attributes();

    /**
     * returns attributes which in the given {@link AttributeType}
     *
     * @param type
     * @return
     */
    List<Attribute> attributes(AttributeType type);

}
