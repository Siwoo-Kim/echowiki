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

    default Tree<E> getRoot() {
        Tree<E> node = getParent();
        while (!node.isRoot())
            node = node.getRoot();
        return node;
    }

    @Nullable Tree<E> getParent();

    void setParent(@Nullable Tree<E> parent);

    List<Tree<E>> getChildren();

    void addChild(Tree<E> child);

    void removeChild(Tree<E> child);
}
