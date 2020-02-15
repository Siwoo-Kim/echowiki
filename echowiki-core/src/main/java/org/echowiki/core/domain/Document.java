package org.echowiki.core.domain;

import java.util.List;

public interface Document extends Tree<Document>, Comparable<Document> {

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
    Category getCategory();

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

}
