package org.echowiki.core.domain;

import java.util.Comparator;
import java.util.List;

public interface Document extends Tree<Document>, Comparable<Document> {

    Comparator<Document> DEFAULT_COMPARATOR = Comparator.comparing(Document::getTitle);

    /**
     * returns the title of the {@link Document}
     *
     * @return
     */
    String getTitle();

    /**
     * returns the category in which {@link Document} presents.
     *
     * @return
     */
    List<Category> getCategories();

    /**
     * is the {@link Document} HEAD?
     *
     * @return
     */
    boolean isTrunk();

    /**
     * returns all the {@link Topic} list of the {@link Document}
     *
     * @return
     */
    List<Topic> getTopics();

    @Override
    default int compareTo(Document o) {
        return DEFAULT_COMPARATOR.compare(this, o);
    }
}
