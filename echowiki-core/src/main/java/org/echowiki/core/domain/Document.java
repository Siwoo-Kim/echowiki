package org.echowiki.core.domain;

import java.util.Comparator;
import java.util.List;

/**
 * The class {@link Document} represents the document of the wiki app.
 * Think of a document as a page that describes a particular concept.
 *
 * There are different types of documents for different purposes.
 * In the application, similar documents are grouped together
 * under the name of {@link Category}.
 *
 * Please note that it is in-case-sensitive when comparing documents.
 *
 * Document also has Trunk and Root concept to rollback for specific revision.
 * Trunk is the current body of the documentation.
 * Root is the original document.
 *
 * Please note that if the document for the specific title exists then at least one trunk exists.
 * Please note that if the document for the specific title exists then at least one root exists.
 *
 * {@in-variant: if {@code doc1.title().equals(doc2.title)} then two documents have same root and trunk}
 * {@in-variant: if {@code doc.title()} exists then documents which has same title has at least one trunk}
 * {@in-variant: if {@code doc.title()} exists then documents which has same title has at least one root}
 *
 */
public interface Document extends Tree<Document>, Comparable<Document> {

    Comparator<Document> DEFAULT_COMPARATOR = Comparator.comparing(Document::getTitle);

    /**
     * returns the title of the {@link Document}
     *
     * @return
     */
    String getTitle();

    /**
     * returns the category in which {@link Document} presents.
     *
     * @return
     */
    List<Category> getCategories();

    /**
     * is the {@link Document} Trunk?
     *
     * @return
     */
    boolean isTrunk();

    /**
     * returns all the {@link Topic} list of the {@link Document}
     *
     * @return
     */
    List<Topic> getTopics();

    @Override
    default int compareTo(Document o) {
        return DEFAULT_COMPARATOR.compare(this, o);
    }
}
