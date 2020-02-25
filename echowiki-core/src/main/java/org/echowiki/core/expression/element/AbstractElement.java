package org.echowiki.core.expression.element;

import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractElement implements Element {

    private final List<Attribute> attributes = new ArrayList<>();

    public abstract Scope type();

    @Override
    public void addValue(String key, String value) {
        checkArgument(Strings.isNotBlank(key) && Strings.isNotBlank(value));
        Attribute attribute = getAttribute(key);
        if (attribute == null) {
            attributes.add(Elements.newAttribute(key));
            attribute = getAttribute(key);
        }
        attribute.addValue(value);
    }

    @Override
    public List<Attribute> attributes() {
        return new ArrayList<>(attributes);
    }

    @Override
    public Attribute getAttribute(String key) {
        checkNotNull(key);
        for (Attribute a : attributes)
            if (a.key().equals(key))
                return a;
        return null;
    }

    @Override
    public Attribute getAttribute(WIKI wiki) {
        checkNotNull(wiki);
        for (Attribute a : attributes)
            if (a.wiki().equals(wiki))
                return a;
        return null;
    }
}
