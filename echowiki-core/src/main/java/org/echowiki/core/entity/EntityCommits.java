package org.echowiki.core.entity;

import org.echowiki.core.domain.Commit;
import org.echowiki.core.domain.Commits;
import org.springframework.stereotype.Service;

@Service
public class EntityCommits implements Commits {

    public Commit newRoot() {
        return new EntityCommit();
    }

    @Override
    public Commit newCommit(Commit oldCommit) {
        EntityCommit old = (EntityCommit) oldCommit;
        return new EntityCommit(old);
    }

}
