package org.echowiki.core.domain;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

public interface EventTime {

    /**
     * returns the creation date of target entity.
     *
     * @invarinat {@code getCreation() != null}
     * @return
     */
    LocalDateTime getCreation();

    /**
     * returns the deletion date of target entity.
     *
     * @return
     */
    @Nullable LocalDateTime getDeletion();

    /**
     * returns the update date of target entity.
     *
     * @return
     */
    @Nullable LocalDateTime getUpdate();

}

