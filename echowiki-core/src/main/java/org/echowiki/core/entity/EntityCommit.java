package org.echowiki.core.entity;

import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.echowiki.core.check.Check;
import org.echowiki.core.domain.Commit;
import org.echowiki.core.domain.Document;

import javax.persistence.*;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Entity(name = "Commit")
@Table(name = "document_commit")
public class EntityCommit implements Commit, Check<Commit> {

    static final int ROOT_INDEX = 1;
    private static final Pattern INDEX_PATTERN = Pattern.compile("r([\\d]+)");

    @Id
    @GeneratedValue
    Long id;

    @Column(name = "commit_index")
    int index;

    @OneToOne(targetEntity = EntityDocument.class,
            optional = false)
    @JoinColumn(name = "document_id")
    Document document;

    @Column(columnDefinition = "bit")
    boolean trunk;

    @OneToOne(targetEntity = EntityCommit.class)
    @JoinColumn(name = "previous_id")
    Commit previous;

    /**
     * init the property of the {@link EntityCommit} as a root.
     */
    public EntityCommit() {
        this(null);
    }

    /**
     * init the property of the {@link EntityCommit}.
     *
     * if given {@code previous} is null, then the instance will be initialized as root,
     * otherwise, the instance will be new trunk.
     *
     * @param oldCommit
     */
    EntityCommit(Commit oldCommit) {
        if (oldCommit == null) {
            this.trunk = true;
            this.index = ROOT_INDEX;
        } else {
            checkRelation(oldCommit);
            EntityCommit previous = (EntityCommit) oldCommit;
            this.previous = previous;
            previous.trunk = false;
            this.index = previous.index + 1;
            this.trunk = true;
        }
    }

    @Override
    public String getIndex() {
        return String.format("r%d", index);
    }

    @Override
    public Document getDocument() {
        return document;
    }

    @Override
    public boolean isTrunk() {
        return trunk;
    }

    @Override
    public boolean isRoot() {
        return previous == null;
    }

    @Override
    public Commit previous() {
        return previous;
    }

    @Override
    public Commit root() {
        Commit c = this, prev = null;
        while (c != null) {
            prev = c;
            c = c.previous();
        }
        return prev;
    }

    @Override
    public <S extends Commit> void checkRelation(S commit) {
        checkArgument(commit instanceof EntityCommit, String.format("The class [%s] is not incompatible for given instance of [%s]",
                getClass().getSimpleName(), commit.getClass()));
        checkNotNull(commit.getDocument(), "For creating next commit, document shouldn't be null.");
        checkArgument(Strings.isNotBlank(commit.getIndex()));
    }

    @Override
    public Commit checkState() {
        checkArgument(index > 0);
        checkNotNull(document);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityCommit commit = (EntityCommit) o;
        return index == commit.index &&
                Objects.equals(id, commit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, index);
    }
}
