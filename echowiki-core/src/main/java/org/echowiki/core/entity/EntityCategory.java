package org.echowiki.core.entity;

import lombok.*;
import org.echowiki.core.domain.Auditable;
import org.echowiki.core.domain.Category;
import org.echowiki.core.domain.Tree;
import org.echowiki.core.meta.Persistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity(name = "Category")
@Table(name = "category")
@Builder
public class EntityCategory extends AbstractTree<Category> implements Category, Persistable, Auditable {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private EntityEventTime eventTime;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private EntityCategory parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private final List<EntityCategory> children = new ArrayList<>();

    @Override
    Tree<? extends Category> parent() {
        return parent;
    }

    @Override
    List children() {
        return children;
    }

    @Override
    void parent(Tree<? extends Category> parent) {
        this.parent = (EntityCategory) parent;
    }

    public List<Tree<Category>> getChildren() {
        return new ArrayList<>(children);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityCategory that = (EntityCategory) o;
        return id.equals(that.id) &&
                title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "EntityCategory{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
