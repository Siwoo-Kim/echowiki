package org.echowiki.core.domain;

import com.sun.istack.Nullable;

import java.util.Iterator;
import java.util.List;

public interface Tree<E extends Comparable<E>> extends Iterable<Tree<E>> {

    default boolean isRoot() {
        return getParent() == null;
    }

    default boolean isLeaf() {
        return getChildren() == null || getChildren().isEmpty();
    }

    default Tree<? extends E> getRoot() {
        Tree<? extends E> node = getParent();
        while (!node.isRoot())
            node = node.getRoot();
        return node;
    }

    @Nullable Tree<E> getParent();

    void setParent(@Nullable Tree<? extends E> parent);

    List<Tree<E>> getChildren();

    void addChild(Tree<? extends E> child);

    void removeChild(Tree<? extends E> child);

    default Iterator<Tree<E>> iterator() {
        return getChildren().iterator();
    }
}
