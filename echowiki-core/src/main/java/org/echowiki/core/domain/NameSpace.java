package org.echowiki.core.domain;

/**
 * The enum represents a type of {@link Category}
 */
public enum NameSpace {
    /**
     * Type for {@link Category} used normally.
     * 일반 문서를 를 위한 분류 (Category).
     */
    일반,
    사용자,
    /**
     * Type for {@link Category} used in the app.
     * 앱에 관련된 문서를 위한 분류 (Category)
     */
    에코위키,
    템플릿;
}
