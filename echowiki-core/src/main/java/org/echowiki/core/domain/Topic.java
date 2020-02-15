package org.echowiki.core.domain;


public interface Topic extends Tree<Topic>, Comparable<Topic> {

    /**
     * returns the head of the topic.
     *
     * @return
     */
    String getHead();

    /**
     * return the index of {@link Topic} in the {@link Document}
     *
     * @return
     */
    int index();

    /**
     * returns the owner {@link Document} of the topic
     *
     * @return
     */
    Document getDocument();
}
