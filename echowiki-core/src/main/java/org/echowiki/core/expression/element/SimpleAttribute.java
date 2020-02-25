package org.echowiki.core.expression.element;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@EqualsAndHashCode(of = "key")
@ToString(of = "key")
public class SimpleAttribute implements Attribute {
    private final String key;
    private final List<String> values = new ArrayList<>();

    SimpleAttribute(String key) {
        this.key = key;
    }

    @Override
    public WIKI wiki() {
        return WIKI.fromString(key);
    }

    @Override
    public String key() {
        return key;
    }

    public void addValue(String value) {
        checkNotNull(value);
        if (!values.contains(value))
            values.add(value);
    }

    @Override
    public List<String> values() {
        return new ArrayList<>(values);
    }
}
