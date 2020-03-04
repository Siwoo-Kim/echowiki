package org.echowiki.core.entity;

import org.echowiki.core.configuration.CoreConfiguration;
import org.echowiki.core.configuration.DatabaseConfiguration;
import org.echowiki.core.domain.Commit;
import org.echowiki.core.domain.Commits;
import org.echowiki.core.domain.Document;
import org.echowiki.core.domain.Documents;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, CoreConfiguration.class})
public class UnitTestEntityCommit {

    @Inject
    private Commits commits;

    @Inject
    private Documents documents;

    @Test
    public void unitTestNewRoot() {
        Commit commit = commits.newRoot();
        assertTrue(commit.isRoot());
        assertTrue(commit.isTrunk());
        assertEquals(commit.root(), commit);
        assertNull(commit.previous());
    }

    @Test
    public void unitTestNewCommit() {
        EntityCommit commit = (EntityCommit) commits.newRoot();
        commit.document = createFixtureDocument();
        Commit newCommit = commits.newCommit(commit);

        assertFalse(commit.isTrunk());
        assertTrue(commit.isRoot());
        assertTrue(newCommit.isTrunk());
        assertFalse(newCommit.isRoot());
        assertEquals(newCommit.previous(), commit);
    }

    @Test
    public void unitTestConstructorWithOldCommit() {
        EntityCommit oldCommit = new EntityCommit();
        oldCommit.document = createFixtureDocument();

        assertTrue(oldCommit.isTrunk());
        assertEquals(oldCommit.getIndex(), "r1");

        EntityCommit newCommit = new EntityCommit(oldCommit);
        newCommit.document = createFixtureDocument();

        assertFalse(oldCommit.isTrunk());
        assertEquals(oldCommit.getIndex(), "r1");
        assertTrue(newCommit.isTrunk());
        assertEquals(newCommit.getIndex(), "r2");

        EntityCommit newNewCommit = new EntityCommit(newCommit);

        assertFalse(oldCommit.isTrunk());
        assertEquals(oldCommit.getIndex(), "r1");
        assertFalse(newCommit.isTrunk());
        assertEquals(newCommit.getIndex(), "r2");
        assertTrue(newNewCommit.isTrunk());
        assertEquals(newNewCommit.getIndex(), "r3");

        assertEquals(newNewCommit.previous(), newCommit);
        assertEquals(newCommit.previous(), oldCommit);
        assertNull(oldCommit.previous());

        assertEquals(newNewCommit.root(), oldCommit);
        assertEquals(newCommit.root(), oldCommit);
    }

    private Document createFixtureDocument() {
        return documents.newLonerDocument("test", "test");
    }

}
