package org.echowiki.core.domain;

import java.time.LocalDateTime;

public interface EventTime {

    LocalDateTime getCreation();

    LocalDateTime getUpdate();

    LocalDateTime getDeletion();

}
