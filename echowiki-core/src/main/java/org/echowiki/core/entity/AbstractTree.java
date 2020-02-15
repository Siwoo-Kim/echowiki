package org.echowiki.core.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.echowiki.core.domain.Tree;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@SuperBuilder
public abstract class AbstractTree<E extends Comparable<E>> implements Tree<E> {

    /**
     * @return
     * @implSpec should return original parent {@link Tree<E>}
     */
    abstract Tree<?> parent();

    /**
     * @return
     * @implSpec should return original children collections {@link List}
     */
    abstract List children();

    /**
     * @return
     * @implSpec should set the given {@link Tree<E>} to parent.
     */
    abstract void parent(Tree<? extends E> parent);

    @Override
    public boolean isRoot() {
        return getParent() == null;
    }

    @Override
    public boolean isLeaf() {
        return getChildren() == null || getChildren().isEmpty();
    }

    @Override
    public Tree<E> getRoot() {
        Tree<E> node = getParent();
        while (!node.isRoot())
            node = node.getRoot();
        return node;
    }

    public Tree<E> getParent() {
        return (Tree<E>) parent();
    }

    @Override
    public void setParent(Tree<E> parent) {
        if (parent == null)
            parent(null);
        else {
            this.parent(parent);
            parent.addChild(this);
        }
    }

    @Override
    public void addChild(Tree<E> child) {
        checkNotNull(child);
        if (!children().contains(child))
            children().add(child);
        if (!Objects.equals(child.getParent(), this))
            child.setParent(this);
    }

    @Override
    public void removeChild(Tree<E> child) {
        if (child == null) return;
        children().remove(child);
        if (Objects.equals(child.getParent(), this))
            parent(null);
    }

    @Override
    public Iterator<Tree<E>> iterator() {
        return getChildren().iterator();
    }
}
