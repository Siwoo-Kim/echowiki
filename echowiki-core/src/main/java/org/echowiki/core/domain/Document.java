package org.echowiki.core.domain;

import org.apache.logging.log4j.util.Strings;
import org.echowiki.core.check.Check;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 문서의 개발 개념
 *  분류 문서 개념
 *  넘겨주기 개념 (redirection),
 *  분류에 의한 관련 문서 개념
 *
 * 문서의 종류 (Category 에 의해 정의)
 *  - 분류 문서
 *  - 외톨이 문서
 *  - 틀 문서
 *
 * Documents are generally acceptable to think of as a page about one particular concept.
 * Documents that exist alone, not linked to other documents, are called loner documents.
 *
 * The connectivity between documents is determined through {@link Category}.
 * Each time a change occurs in a document, the document creates its new {@link Commit} to track the change.
 *
 * {@invarinat}
 *  Document name should not be null and should be unique.
 *
 */
public interface Document extends Check<Document> {

    /**
     * returns a name of {@link Document}
     *
     * @return
     */
    String getName();

    /**
     * returns a content of {@link Document}
     * @return
     */
    String getContent();

    /**
     * returns a category which current {@link Document} in
     *
     * @return
     */
    Category getCategory();

    /**
     * returns a commit for {@link Document}
     * @return
     */
    Commit getCommit();

    /**
     * is any connection between other documents?
     * @return
     */
    boolean isLoner();

    EventTime getEventTime();

    /**
     * returns connected documents.
     *
     * @return
     */
    List<Document> getLinks();

    @Override
    default  void checkRelation(Document association) {
        // not need
    }

    @Override
    default Document checkState() {
        checkArgument(Strings.isNotBlank(getName()));
        checkNotNull(getCommit());
        checkNotNull(getEventTime());
        return this;
    }
}
