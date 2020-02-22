package org.echowiki.core.expression;

import lombok.EqualsAndHashCode;
import org.echowiki.core.expression.meta.AttributeType;
import org.echowiki.core.expression.meta.ElementType;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

public class SimpleElement implements Element {

    private final Map<AttributeType, Set<Attribute>> attributes;
    private ElementType type;

    public static SimpleElement newElement(ElementType type) {
        return new SimpleElement(type, new HashMap<>());
    }

    public static SimpleAttribute newAttribute(AttributeType type, String key, String value) {
        return new SimpleAttribute(type, key, value);
    }

    SimpleElement(ElementType type, Map<AttributeType, Set<Attribute>> attributes) {
        this.attributes = attributes;
        this.type = type;
    }

    @Override
    public ElementType type() {
        return type;
    }

    @Override
    public void addAttribute(Attribute attribute) {
        AttributeType type = attribute.type();
        if (!attributes.containsKey(type))
            attributes.put(type, new HashSet<>());
        attributes.get(type).add(attribute);
    }

    @Override
    public Map<AttributeType, Set<Attribute>> attributes() {
        return new HashMap<>(attributes);
    }

    @Override
    public List<Attribute> getAttributes() {
        return attributes.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<Attribute> attributeOf(AttributeType type) {
        return new ArrayList<>(attributes.get(type));
    }

    @EqualsAndHashCode(of = {"type", "key"})
    private static class SimpleAttribute implements Element.Attribute {
        private final AttributeType type;
        private final String key;
        private final String value;

        SimpleAttribute(AttributeType type, String key, String value) {
            checkNotNull(type);
            this.type = type;
            this.key = key;
            this.value = value;
        }

        @Override
        public AttributeType type() {
            return type;
        }

        @Override
        public String key() {
            return key;
        }

        @Override
        public String value() {
            return value;
        }
    }
}
