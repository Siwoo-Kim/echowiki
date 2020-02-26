package org.echowiki.core.expression.element;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

@EqualsAndHashCode(of = "key")
@ToString(of = "key")
public class SimpleAttribute implements Attribute {
    private static final String KEY_VALUE_SEPARATOR = "=";
    private final String key;
    private final Map<String, String> values = new HashMap<>();

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
        int indexOfKey = value.indexOf(KEY_VALUE_SEPARATOR);
        if (indexOfKey == -1)
            throw new IllegalArgumentException();
        String key = value.substring(0, indexOfKey);
        values.put(key, value);
    }

    @Override
    public List<String> values() {
        return new ArrayList<>(values.values());
    }
}
