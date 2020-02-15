package org.echowiki.core.domain;

import java.util.Comparator;

public interface Category extends Comparable<Category>, Tree<Category> {

    Comparator<Category> DEFAULT_COMPARATOR = Comparator.comparing(Category::getTitle);

    /**
     * returns title of the {@link Category}
     *
     * @return
     */
    String getTitle();

    default int compareTo(Category o) {
        assert o != null;
        return DEFAULT_COMPARATOR.compare(this, o);
    }
}
