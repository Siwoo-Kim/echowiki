package org.echowiki.core.entity;

import lombok.*;
import org.echowiki.core.domain.EventTime;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Embeddable
public class EntityEventTime implements EventTime {

    private LocalDateTime creation;

    private LocalDateTime update;

    private LocalDateTime deletion;

}
