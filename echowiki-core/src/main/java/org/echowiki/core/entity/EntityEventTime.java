package org.echowiki.core.entity;

import lombok.*;
import org.echowiki.core.domain.EventTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Embeddable
public class EntityEventTime implements EventTime {

    @Column(name = "created")
    private LocalDateTime creation;

    @Column(name = "updated")
    private LocalDateTime update;

    @Column(name = "deleted")
    private LocalDateTime deletion;

    public static EntityEventTime newInstance() {
        return builder().creation(LocalDateTime.now()).build();
    }
}
