package org.echowiki.core.entity;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.echowiki.core.domain.Auditable;
import org.echowiki.core.domain.Document;
import org.echowiki.core.domain.Revision;
import org.echowiki.core.meta.Persistable;

import javax.persistence.*;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity(name = "Revision")
@Table(name = "revision")
public class EntityRevision  implements Revision, Auditable, Persistable {

    private static final Pattern REVISION_PATTERN = Pattern.compile("r(\\d+)");
    private static final String ROOT_VERSION = "r1";
    private static final String REVISION_FORMAT = "r%d";
    private static final int FIRST_REVISION = 1;

    @Id @GeneratedValue
    private Long id;

    private String revision;

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

    private String message;

    public static EntityRevision newRoot(Document document, String commitBy, String message) {
        checkNotNull(document);
        checkArgument(!Strings.isNullOrEmpty(commitBy));
        return new EntityRevision(null,
                ROOT_VERSION,
                null,
                (EntityDocument) document,
                commitBy,
                EntityEventTime.newInstance(), message);
    }

    /**
     * {@invariant} {@code trunk.isTrunk() == true}
     *
     * @param trunk
     * @param document
     * @param commitBy
     * @return
     */
    public static Revision fromTrunk(Revision trunk, Document document, String commitBy, String message) {
        checkNotNull(trunk);
        checkArgument(!Strings.isNullOrEmpty(commitBy));
        checkArgument(trunk.isTrunk(), String.format("Given [%s] is not trunk.", trunk));
        int trunkIndex = getRevisionIndex(trunk.getRevision());
        EntityRevision entity = (EntityRevision) trunk;

        EntityRevision instance = new EntityRevision(null,
                String.format(REVISION_FORMAT, trunkIndex + 1),
                null,
                (EntityDocument) document,
                commitBy,
                EntityEventTime.newInstance(), message);
        entity.next = instance;
        return instance;
    }

    private static int getRevisionIndex(String revision) {
        checkArgument(!Strings.isNullOrEmpty(revision));
        Matcher matcher = REVISION_PATTERN.matcher(revision);
        if (!matcher.find())
            throw new IllegalArgumentException(
                    String.format("Given Revision [%s] has wrong format. Format should be r[digit]", revision));
        return Integer.parseInt(matcher.group(1));
    }

    @Override
    public boolean isTrunk() {
        return next == null;
    }

    @Override
    public boolean isRoot() {
        return ROOT_VERSION.equals(revision);
    }

    @Override
    public String getRevision() {
        return revision;
    }

    @Override
    public String commitBy() {
        return commitBy;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityRevision that = (EntityRevision) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(revision, that.revision);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, revision);
    }

    @Override
    public String toString() {
        return "EntityRevision{" +
                "id=" + id +
                ", version='" + revision + '\'' +
                ", master=" + next +
                ", commitBy='" + commitBy + '\'' +
                '}';
    }
}
