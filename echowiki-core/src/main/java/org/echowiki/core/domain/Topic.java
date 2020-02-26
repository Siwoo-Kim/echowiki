package org.echowiki.core.domain;


import java.util.Comparator;

import static com.google.common.base.Preconditions.checkNotNull;

public interface Topic extends Tree<Topic>, Comparable<Topic> {

    Comparator<Topic> DEFAULT_COMPARATOR = Comparator.comparing(Topic::getIndex);
    String FIRST_INDEX = "1";
    String INDEX_SEPARATOR = "-";

    /**
     * returns the first index of topic.
     *
     * @return
     */
    static String firstIndex() {
        return FIRST_INDEX;
    }

    /**
     * returns next index of current index.
     * eg) 1 => 2, 2 => 3, 2-1 => 2-2, 2-1-15 => 2-1-16
     *
     * @return
     */
    default String nextIndex() {
        int separatorIndex = getIndex().lastIndexOf(INDEX_SEPARATOR);
        if (separatorIndex == -1)
            return Integer.toString(Integer.parseInt(getIndex()) + 1);
        else {
            int nextIndex = Integer.parseInt(getIndex().substring(separatorIndex+1)) + 1;
            return getIndex().substring(0, separatorIndex) + nextIndex;
        }
    }

    /**
     * returns the head of the topic.
     *
     * @return
     */
    String getHeading();

    /**
     * return the index of {@link Topic} in the {@link Document}
     *
     * @return
     */
    String getIndex();

    /**
     * returns the owner {@link Document} of the topic
     *
     * @return
     */
    Document getDocument();

    @Override
    default int compareTo(Topic o) {
        return DEFAULT_COMPARATOR.compare(this, o);
    }
}
