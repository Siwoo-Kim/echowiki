package org.echowiki.core.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.echowiki.core.check.Check;
import org.echowiki.core.domain.Category;
import org.echowiki.core.domain.Commit;
import org.echowiki.core.domain.Document;
import org.echowiki.core.domain.EventTime;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity(name = "Document") @Table(name = "document")
public class EntityDocument implements Document, Check<Document> {

    @Column(unique = true)
    String name;
    @Basic(fetch = FetchType.LAZY)
    String content;
    @ManyToOne(targetEntity = EntityCategory.class)
    @JoinColumn(name = "category_id")
    Category category;
    @OneToOne(targetEntity = EntityCommit.class,
            mappedBy = "document")
    Commit commit;

    @Id
    @GeneratedValue
    private Long id;
    @Embedded
    private EntityEventTime eventTime;

    EntityDocument(String name, String content, Category category, Commit commit) {
        this.name = name;
        this.content = content;
        this.category = category;
        if (category != null)
            category.addDocument(this);
        ((EntityCommit) commit).document = this;
        this.commit = commit;
        eventTime = EntityEventTime.newEventTime();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public Commit getCommit() {
        return commit;
    }

    @Override
    public boolean isLoner() {
        return category == null;
    }

    @Override
    public EventTime getEventTime() {
        return eventTime;
    }

    @Override
    public List<Document> getLinks() {
        checkArgument(isLoner(), String.format("Loner Document [%s] doesn't have linked documents.", getName()));
        List<Document> documents = category.getDocuments();
        assert documents.contains(this);
        documents.remove(this);
        return documents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityDocument document = (EntityDocument) o;
        return Objects.equals(id, document.id) &&
                Objects.equals(name, document.name) &&
                Objects.equals(commit, document.commit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, commit);
    }
}
