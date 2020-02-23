package org.echowiki.core.expression.element;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.google.common.base.Preconditions.checkNotNull;

@EqualsAndHashCode(of = {"type", "key", "value"})
@ToString(of = {"type", "key", "value"})
public class SimpleAttribute implements Attribute {
    private final AttributeType type;
    private final String key;
    private final String value;

    public SimpleAttribute(AttributeType type, String key, String value) {
        checkNotNull(type, key);
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
