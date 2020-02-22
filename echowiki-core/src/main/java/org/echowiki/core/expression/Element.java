package org.echowiki.core.expression;

import org.echowiki.core.expression.meta.AttributeType;
import org.echowiki.core.expression.meta.ElementType;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface Element {

    ElementType type();

    void addAttribute(Attribute attribute);

    Map<AttributeType, Set<Attribute>> attributes();

    List<Attribute> getAttributes();

    List<Attribute> attributeOf(AttributeType type);

    interface Attribute {

        AttributeType type();

        String key();

        String value();
    }

}
