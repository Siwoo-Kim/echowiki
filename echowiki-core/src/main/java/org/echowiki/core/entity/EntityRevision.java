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
import org.omg.CORBA.FREE_MEM;

import javax.persistence.*;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.nio.charset.StandardCharsets.UTF_8;


@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity(name = "Revision")
@Table(name = "revision")
public class EntityRevision  implements Revision, Auditable, Persistable {

    private static final String TRUNK_VERSION = "rev{1}";
    private static final String REVISION_FORMAT = "rev{%d}";
    private static final int FIRST_REVISION = 1;

    @Id @GeneratedValue
    private Long id;

    private String version;

    @OneToOne
    @JoinColumn(name = "next_id")
    private EntityRevision next;

    @OneToOne
    @JoinColumn(name = "document_id")
    private EntityDocument document;

    @Column(name = "commit_by")
    private String commitBy;

    @Embedded
    private EntityEventTime eventTime;

    public static EntityRevision newMasterInstance(Document document, String commitBy) {
        checkNotNull(document);
        checkArgument(!Strings.isNullOrEmpty(commitBy));
        return new EntityRevision(null,
                String.format(REVISION_FORMAT, FIRST_REVISION),
                null,
                (EntityDocument) document,
                commitBy,
                EntityEventTime.newInstance());
    }

    /**
     * {@invariant} {@code trunk.isTrunk() == true}
     *
     * @param trunk
     * @param document
     * @param commitBy
     * @return
     */
    public static Revision fromTrunk(Revision trunk, Document document, String commitBy) {
        checkNotNull(trunk);
        checkArgument(!Strings.isNullOrEmpty(commitBy));
        checkArgument(trunk.isTrunk(), String.format("Given [%s] is not trunk.", trunk));
        int trunkIndex = getVersionIndex(trunk.getVersion());
        EntityRevision entity = (EntityRevision) trunk;

        EntityRevision instance = new EntityRevision(null,
                String.format(REVISION_FORMAT, trunkIndex + 1),
                null,
                (EntityDocument) document,
                commitBy,
                EntityEventTime.newInstance());
        entity.next = instance;
        return instance;
    }

    private static int getVersionIndex(String version) {
        checkArgument(!Strings.isNullOrEmpty(version));
        return Integer.parseInt(version.substring(version.indexOf("{")+1, version.indexOf("}")));
    }

    @Override
    public boolean isHead() {
        return next == null;
    }

    @Override
    public boolean isTrunk() {
        return TRUNK_VERSION.equals(version);
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String commitBy() {
        return commitBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityRevision that = (EntityRevision) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(version, that.version) &&
                Objects.equals(next, that.next) &&
                Objects.equals(commitBy, that.commitBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, next, commitBy);
    }

    @Override
    public String toString() {
        return "EntityRevision{" +
                "id=" + id +
                ", version='" + version + '\'' +
                ", master=" + next +
                ", commitBy='" + commitBy + '\'' +
                '}';
    }
}
