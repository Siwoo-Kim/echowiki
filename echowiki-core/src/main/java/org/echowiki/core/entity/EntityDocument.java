package org.echowiki.core.entity;

import lombok.*;
import org.echowiki.core.domain.*;
import org.echowiki.core.meta.Persistable;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity(name = "Document")
@Table(name = "document")
@Builder
public class EntityDocument extends AbstractTree<Document> implements Document, Auditable, Persistable {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @ManyToMany
    @JoinTable(name = "category_document",
            joinColumns = @JoinColumn(name = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JoinColumn(name = "category_id")
    private List<EntityCategory> categories;

    @OneToOne(mappedBy = "document")
    private EntityRevision revision;

    @OneToMany(mappedBy = "document", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<EntityTopic> topics;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private EntityDocument parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<EntityDocument> children;

    @Embedded
    private EventTime eventTime;

    @Override
    public boolean isTrunk() {
        return revision.isHead();
    }

    @Override
    public List<Topic> getTopics() {
        return null;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public List<Category> getCategories() {
        return new ArrayList<>(categories);
    }

    @Override
    Tree<?> parent() {
        return parent;
    }

    @Override
    List children() {
        return children;
    }

    @Override
    void parent(Tree<? extends Document> parent) {
        this.parent = (EntityDocument) parent;
    }

    @Override
    public EventTime getEventTime() {
        return eventTime;
    }
}
