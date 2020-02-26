package org.echowiki.core.entity;

import org.echowiki.core.domain.Revision;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTestEntityRevision {

    @Test
    public void unitTestNewRoot() {
        EntityDocument mockDocument = EntityDocument.builder().build();
        String mockCommitBy = "mock";
        String mockMessage = "message";
        Revision revision = EntityRevision.newRoot(mockDocument, mockCommitBy, mockMessage);
        assertTrue(revision.isTrunk());
        assertEquals(revision.getRevision(), "r1");
        assertEquals(revision.commitBy(), "mock");
    }

    @Test
    public void unitTestFromTrunk() {
        EntityDocument mockDocument = EntityDocument.builder().build();
        String mockCommitBy = "mock";
        String mockMessage = "message";
        Revision revision = EntityRevision.newRoot(mockDocument, mockCommitBy, mockMessage);

        assertTrue(revision.isTrunk());
        assertTrue(revision.isRoot());
        assertEquals(revision.getRevision(), "r1");
        assertEquals(revision.commitBy(), "mock");

        Revision revision2 = EntityRevision.fromTrunk(revision, mockDocument, mockCommitBy, mockMessage);

        assertFalse(revision.isTrunk());
        assertTrue(revision.isRoot());
        assertEquals(revision.getRevision(), "r1");
        assertEquals(revision.commitBy(), "mock");
        assertEquals(revision.getNext(), revision2);
        assertEquals(revision2.getRevision(), "r2");

        Revision revision3 = EntityRevision.fromTrunk(revision2, mockDocument, mockCommitBy, mockMessage);

        assertFalse(revision.isTrunk());
        assertTrue(revision.isRoot());
        assertEquals(revision.getNext(), revision2);
        assertEquals(revision.getRevision(), "r1");;
        assertFalse(revision2.isTrunk());
        assertFalse(revision2.isRoot());
        assertEquals(revision2.getNext(), revision3);
        assertEquals(revision2.getRevision(), "r2");
        assertTrue(revision3.isTrunk());
        assertFalse(revision3.isRoot());
        assertEquals(revision3.getRevision(), "r3");
        assertNull(revision3.getNext());

        EntityRevision.fromTrunk(revision3, mockDocument, mockCommitBy, mockMessage);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unitTestFromTrunkWhenGivenRevisionIsNotTrunk() {
        EntityDocument mockDocument = EntityDocument.builder().build();
        String mockCommitBy = "mock";
        String mockMessage = "message";
        Revision revision = EntityRevision.newRoot(mockDocument, mockCommitBy, mockMessage);
        assertTrue(revision.isRoot());
        assertTrue(revision.isTrunk());

        Revision revision1 = EntityRevision.fromTrunk(revision, mockDocument, mockCommitBy, mockMessage);
        assertTrue(revision.isRoot());
        assertFalse(revision.isTrunk());
        assertTrue(revision1.isTrunk());

        Revision revision2 = EntityRevision.fromTrunk(revision, mockDocument, mockCommitBy, mockMessage);
    }
}
