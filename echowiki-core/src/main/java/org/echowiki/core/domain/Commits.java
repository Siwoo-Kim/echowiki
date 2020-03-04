package org.echowiki.core.domain;

public interface Commits {

    Commit newRoot();

    Commit newCommit(Commit oldCommit);

}
