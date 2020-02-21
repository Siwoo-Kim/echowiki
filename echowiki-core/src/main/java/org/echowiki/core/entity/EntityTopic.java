package org.echowiki.core.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.echowiki.core.domain.*;
import org.echowiki.core.meta.Persistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity(name = "Topic")
@Table(name = "topic")
@Builder
public class EntityTopic extends AbstractTree<Topic> implements Topic, Auditable, Persistable {

    @Id @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "topic_index")
    private String index;

    @NotNull
    private String heading;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private EntityTopic parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private List<EntityTopic> children = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "document_id")
    private EntityDocument document;

    private EntityEventTime eventTime;

    private String paragraph;

    @Override
    Tree<?> parent() {
        return parent;
    }

    @Override
    List children() {
        return children;
    }

    @Override
    void parent(Tree<? extends Topic> parent) {
        this.parent = (EntityTopic) parent;
    }

    @Override
    public String toString() {
        return "EntityTopic{" +
                "id=" + id +
                ", index='" + index + '\'' +
                ", heading='" + heading + '\'' +
                ", parent=" + parent +
                ", document=" + document +
                '}';
    }

    @Override
    public String getHeading() {
        return heading;
    }

    @Override
    public String getIndex() {
        return index;
    }

    @Override
    public Document getDocument() {
        return document;
    }

    @Override
    public EventTime getEventTime() {
        return eventTime;
    }
}
