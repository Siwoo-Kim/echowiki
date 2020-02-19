package org.echowiki.core.domain;

import com.sun.istack.Nullable;

/**
 * Represents the specific commitment for the document.
 *
 */
public interface Revision {

    /**
     * is the head {@link Revision}?
     *
     * @return
     */
    boolean isHead();

    /**
     * is the trunk {@link Revision}?
     *
     * @return
     */
    boolean isTrunk();

    /**
     * returns next {@link Revision}.
     *
     * @return
     */
    @Nullable Revision getNext();

    String getVersion();

    /**
     * returns the one who made commit.
     *
     * @return
     */
    String commitBy();

    /**
     * returns commit message.
     *
     * @return
     */
    String getMessage();

}
