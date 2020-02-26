package org.echowiki.core.domain;

import com.sun.istack.Nullable;

/**
 * The class {@link Revision} represents a commit, or "revision", is an individual change to a document.
 * The class provides the snapshot of the document, every time when user make a change on any document.
 *
 * {@in-varinat1: if first revision is created, then the revision is root and also trunk}
 * {@in-varinat2: if not the {@in-variant1} case and revision is created,
 * then the revision should be trunk and old trunk need to point to new revision}
 *
 */
public interface Revision {

    /**
     * is the trunk {@link Revision}?
     *
     * @return
     */
    boolean isTrunk();

    /**
     * is the root {@link Revision}?
     *
     * @return
     */
    boolean isRoot();

    /**
     * returns next {@link Revision}.
     *
     * @return
     */
    @Nullable Revision getNext();

    /**
     * returns the revision.
     *
     * @return
     */
    String getRevision();

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
