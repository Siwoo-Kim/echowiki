package org.echowiki.core.domain;

public interface Commit {

    String ROOT_INDEX = "r1";

    String getIndex();

    Document getDocument();

    boolean isTrunk();

    boolean isRoot();

    Commit previous();

    Commit root();

}
