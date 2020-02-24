package org.echowiki.core.expression.element;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

public class EchoElement implements Element {

    private final Map<AttributeType, Set<Attribute>> attributes;

    public EchoElement(ElementType type) {
        this.attributes = new HashMap<>();
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
    public Map<AttributeType, Set<Attribute>> attributeGroups() {
        return new HashMap<>(attributes);
    }

    @Override
    public List<Attribute> attributes() {
        return attributes.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<Attribute> getAttributes(AttributeType type, String key) {
        checkNotNull(type, key);
        if (!attributes.containsKey(type))
            return null;
        List<Attribute> result = new ArrayList<>();
        for (Attribute attribute: attributes.get(type))
            if (attribute.key().equals(key))
                result.add(attribute);
        return result;
    }

    @Override
    public List<Attribute> attributes(AttributeType type) {
        checkNotNull(type);
        return new ArrayList<>(attributes.get(type));
    }

}
