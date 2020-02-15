package org.echowiki.core.entity;

import lombok.*;
import org.echowiki.core.domain.*;
import org.echowiki.core.meta.Persistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity(name = "Document")
@Table(name = "document")
@Builder
public class EntityDocument extends AbstractTree<Document> implements Document, Auditable, Persistable {

    @Id @GeneratedValue
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private EntityCategory category;

    @OneToOne(mappedBy = "document")
    private EntityRevision revision;

    //private List<Topic> topics;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private EntityDocument parent;

    @OneToMany(mappedBy = "parent")
    private List<EntityDocument> children;

    @Embedded
    private EventTime eventTime;

    @Override
    public boolean isTrunk() {
        return revision.isTrunk();
    }

    @Override
    public List<Topic> getTopics() {
        return null;
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

    public List<Tree<Document>> getChildren() {
        return new ArrayList<>(children);
    }
}
