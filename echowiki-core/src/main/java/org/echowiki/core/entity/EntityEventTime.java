package org.echowiki.core.entity;

import org.echowiki.core.domain.EventTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class EntityEventTime implements EventTime {

    @Column(name = "created")
    private LocalDateTime creation;

    @Column(name = "updated")
    private LocalDateTime update;

    @Column(name = "deleted")
    private LocalDateTime deletion;

    EntityEventTime() {
        this.creation = LocalDateTime.now();
    }

    static EntityEventTime newEventTime() {
        EntityEventTime eventTime = new EntityEventTime();
        eventTime.creation = LocalDateTime.now();
        return eventTime;
    }

    public LocalDateTime getCreation() {
        return creation;
    }

    public LocalDateTime getUpdate() {
        return update;
    }

    public LocalDateTime getDeletion() {
        return deletion;
    }
}
