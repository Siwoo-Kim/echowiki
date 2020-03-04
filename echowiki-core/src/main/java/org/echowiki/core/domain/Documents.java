package org.echowiki.core.domain;

/**
 * The class represents a factory for document.
 *
 */
public interface Documents {

    /**
     * returns new {@link Document} which is copied from given {@link Document}.
     *
     * Note that new created {@link Document} will be root.
     * {@link Document} can be other implementations other than {@link org.echowiki.core.entity.EntityDocument}
     *
     * @return root {@link Document}
     */
    Document newDocument(Document document);

    /**
     * returns new {@link Document}.
     *
     * Note that new created {@link Document} will be root.
     *
     * @throws IllegalArgumentException if category doesn't exist.
     * @return root {@link Document}
     */
    Document newDocument(String name, String content, Category category);

    Document newLonerDocument(String name, String content);

    Document commitDocument(String name, String content, Document oldDocument);
}
