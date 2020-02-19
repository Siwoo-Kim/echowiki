package org.echowiki.core.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.echowiki.core.domain.Topic;
import org.echowiki.core.domain.Tree;

import java.util.*;
import java.util.function.Consumer;

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

    public List<Tree<E>> getChildren() {
        return new ArrayList<>(children());
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

    public List<E> getDescendants(Traversal traversal) {
        if (isLeaf())
            return Collections.emptyList();
        List<E> trees = new ArrayList<>();
        //@todo refactoring required
        switch (traversal) {
            case LEVEL: {
                levelTravel(tree -> trees.add(tree));
                break;
            }
            default: throw new UnsupportedOperationException();
        }
        return trees;
    }

    private void levelTravel(Consumer<E> callback) {
        int height = height(this);
        for (int i=1; i<=height; i++)
            levelTravel(this, i, callback);
    }

    private int height(Tree<E> tree) {
        if (tree == null) return 0;
        else {
            int maxHeight = 0;
            for (Tree<E> c: tree.getChildren())
                maxHeight = Math.max(maxHeight, height(c));
            return maxHeight + 1;
        }
    }

    private void levelTravel(Tree<E> tree, int height, Consumer<E> callback) {
        if (tree == null) return;
        if (height == 1) callback.accept((E) tree);
        else if (height > 1) {
            for (Tree<E> c: tree.getChildren())
                levelTravel(c, height-1, callback);
        }
    }

    @Override
    public Iterator<Tree<E>> iterator() {
        return getChildren().iterator();
    }
}
