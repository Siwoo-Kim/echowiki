package org.echowiki.core.domain;

import javax.transaction.TransactionManager;
import java.util.Comparator;

/**
 * The class {@link Category} represents an group of the documents.
 * {@link Category} can be belonged to another category, if the category has same context.
 *
 */
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
