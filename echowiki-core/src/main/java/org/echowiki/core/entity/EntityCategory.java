package org.echowiki.core.entity;

import lombok.*;
import org.echowiki.core.domain.Auditable;
import org.echowiki.core.domain.Category;
import org.echowiki.core.domain.Tree;
import org.echowiki.core.meta.Persistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Getter @ToString(of = {"id", "title", "eventTime"})
@EqualsAndHashCode(of = {"id", "title"})
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity(name = "Category") @Table(name = "category") @Builder
public class EntityCategory implements Category, Persistable, Auditable {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private EntityEventTime eventTime;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private EntityCategory parent;

    @OneToMany(mappedBy = "parent")
    private final List<EntityCategory> children = new ArrayList<>();

    @Override
    public void setParent(Tree<? extends Category> parent) {
        if (parent == null)
            this.parent = null;
        else {
            checkArgument(parent instanceof EntityCategory);
            EntityCategory p = (EntityCategory) parent;
            p.addChild(this);
        }
    }

    public EntityCategory getParent() {
        return parent;
    }

    public List<Tree<Category>> getChildren() {
        return new ArrayList<>(children);
    }

    @Override
    public void addChild(Tree<? extends Category> child) {
        checkNotNull(child);
        checkArgument(child instanceof EntityCategory);

        EntityCategory c = (EntityCategory) child;
        if (!children.contains(child))
            children.add(c);
        if (!Objects.equals(c.parent, this))
            c.parent = this;
    }

    @Override
    public void removeChild(Tree<? extends Category> child) {
        if (child == null) return;
        checkArgument(child instanceof EntityCategory);

        EntityCategory c = (EntityCategory) child;
        children.remove(c);
        if (Objects.equals(c.parent, this))
            c.setParent(null);
    }

}
