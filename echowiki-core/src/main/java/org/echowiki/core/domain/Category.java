package org.echowiki.core.domain;

import org.apache.logging.log4j.util.Strings;
import org.echowiki.core.entity.EntityDocument;

import java.util.Comparator;
import java.util.List;

/**
 * The class {@link Category} represents a group of documents that help users find document easily.
 * For example, category:science contains detailed fields of science such as category:biology, category:chemistry.
 *
 * {@invarinat}
 *  All documents need to be included in at least one category.
 *  Document must be included in 0 or 1 category.
 *  An category might have zero to many documents.
 *  Category name should be unique in one {@link NameSpace}.
 *  One document does not exist simultaneously in the parent category and category.
 */
public interface Category extends Comparable<Category> {

    Comparator<Category> DEFAULT_COMPARATOR = Comparator.comparing(Category::getName);

    /**
     * returns the name of {@link Category}
     *
     * @return
     */
    String getName();

    /**
     * returns {@link NameSpace}
     *
     * @return
     */
    NameSpace getNameSpace();

    /**
     * returns the parents of {@link Category}
     *
     * @return
     */
    List<? extends Category> getParents();

    /**
     * returns the children of {@link Category}
     *
     * @return
     */
    List<? extends Category> getChildren();

    /**
     * add given {@link Category} as child.
     *
     * @throws IllegalArgumentException
     *  {@code child == null || child.getName().isEmpty()}
     *  {@code child == this}
     * @return
     */
    void addChild(Category child);

    /**
     * remove given {@link Category} as child.
     *
     * @throws IllegalArgumentException
     *  {@code child == null || child.getName().isEmpty()}
     *  {@code child == this}
     * @return
     */
    void removeChild(Category child);

    /**
     * add given {@link Category} as parent.
     *
     * @throws IllegalArgumentException
     *  {@code parent == null || parent.getName().isEmpty()}
     *  {@code parent == this}
     * @return
     */
    void addParent(Category parent);

    /**
     * remove given {@link Category} as parent.
     *
     * @throws IllegalArgumentException
     *  {@code parent == null || parent.getName().isEmpty()}
     *  {@code parent == this}
     * @return
     */
    void removeParent(Category parent);

    /**
     * does category have given category as parent?
     *
     * @return
     */
    boolean hasParent(Category parent);

    /**
     * does category have given category as child?
     *
     * @return
     */
    boolean hasChild(Category child);

    /**
     * returns {@link Document} list belonging to that {@link Category}
     *
     * @return
     */
    List<Document> getDocuments();

    /**
     * returns {@link EventTime} of the {@link Category}
     *
     * @return
     */
    EventTime getEventTime();

    @Override
    default int compareTo(Category category) {
        assert category != null && Strings.isNotBlank(category.getName());
        return DEFAULT_COMPARATOR.compare(this, category);
    }

    void addDocument(Document document);

}
