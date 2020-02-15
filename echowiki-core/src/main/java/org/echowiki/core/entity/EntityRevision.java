package org.echowiki.core.entity;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.echowiki.core.domain.Auditable;
import org.echowiki.core.domain.Document;
import org.echowiki.core.domain.EventTime;
import org.echowiki.core.domain.Revision;
import org.echowiki.core.meta.Persistable;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.nio.charset.StandardCharsets.UTF_8;


@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity(name = "Revision")
@Table(name = "revision")
public class EntityRevision  implements Revision, Auditable, Persistable {

    @Id
    private String version;

    @OneToOne
    @JoinColumn(name = "master_version")
    private EntityRevision master;

    @OneToOne
    @JoinColumn(name = "document_id")
    private EntityDocument document;

    @Column(name = "commit_by")
    private String commitBy;

    @Embedded
    private EventTime eventTime;

    public static EntityRevision newMasterInstance(EntityDocument document, String commitBy) {
        checkNotNull(document);
        checkArgument(Strings.isNullOrEmpty(commitBy));
        String version = Hashing.sha256()
                .hashString(document.getId() + commitBy, UTF_8)
                .toString();
        return new EntityRevision(version,
                null,
                document,
                commitBy,
                EntityEventTime.newInstance());
    }

    @Override
    public boolean isTrunk() {
        return master == null;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String commitBy() {
        return commitBy;
    }
}
