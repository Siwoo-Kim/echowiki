package org.echowiki.core.expression.element;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@ToString(of = {"type", "attributes"})
@EqualsAndHashCode(of = {"attributes", "type"})
public class SimpleElement implements Element {

    private final Map<AttributeType, Set<Attribute>> attributes;
    private final ElementType type;

    public SimpleElement(ElementType type) {
        this.attributes = new HashMap<>();
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
    public List<Attribute> attributes(AttributeType type) {
        checkNotNull(type);
        return new ArrayList<>(attributes.get(type));
    }

}
