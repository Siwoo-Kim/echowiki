package org.echowiki.core.entity;

import org.echowiki.core.domain.Revision;
import org.echowiki.core.entity.EntityDocument;
import org.echowiki.core.entity.EntityRevision;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTestEntityRevision {

    @Test
    public void unitTestNewMasterInstance() {
        EntityDocument mockDocument = EntityDocument.builder().build();
        String mockCommitBy = "mock";
        String mockMessage = "message";
        Revision revision = EntityRevision.newMasterInstance(mockDocument, mockCommitBy, mockMessage);
        assertTrue(revision.isHead());
        assertEquals(revision.getVersion(), "rev{1}");
        assertEquals(revision.commitBy(), "mock");
    }

    @Test
    public void unitTestFromTrunk() {
        EntityDocument mockDocument = EntityDocument.builder().build();
        String mockCommitBy = "mock";
        String mockMessage = "message";
        Revision revision = EntityRevision.newMasterInstance(mockDocument, mockCommitBy, mockMessage);

        assertTrue(revision.isHead());
        assertTrue(revision.isTrunk());
        assertEquals(revision.getVersion(), "rev{1}");
        assertEquals(revision.commitBy(), "mock");

        Revision revision1 = EntityRevision.fromTrunk(revision, mockDocument, mockCommitBy, mockMessage);

        assertFalse(revision.isHead());
        assertTrue(revision.isTrunk());
        assertEquals(revision.getVersion(), "rev{1}");
        assertEquals(revision.commitBy(), "mock");
        assertEquals(revision.getNext(), revision1);

        Revision revision2 = EntityRevision.fromTrunk(revision1, mockDocument, mockCommitBy, mockMessage);

        assertFalse(revision.isHead());
        assertTrue(revision.isTrunk());
        assertEquals(revision.getNext(), revision1);
        assertEquals(revision.getVersion(), "rev{1}");

        assertFalse(revision1.isHead());
        assertFalse(revision1.isTrunk());
        assertEquals(revision1.getNext(), revision2);
        assertEquals(revision1.getVersion(), "rev{2}");

        assertTrue(revision2.isHead());
        assertFalse(revision2.isTrunk());
        assertEquals(revision2.getVersion(), "rev{3}");
        assertNull(revision2.getNext());

        EntityRevision.fromTrunk(revision1, mockDocument, mockCommitBy, mockMessage);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unitTestFromTrunkWhenGivenRevisionIsNotTrunk() {
        EntityDocument mockDocument = EntityDocument.builder().build();
        String mockCommitBy = "mock";
        String mockMessage = "message";
        Revision revision = EntityRevision.newMasterInstance(mockDocument, mockCommitBy, mockMessage);
        Revision revision1 = EntityRevision.fromTrunk(revision, mockDocument, mockCommitBy, mockMessage);

        assertFalse(revision.isTrunk());
        assertTrue(revision1.isTrunk());
        EntityRevision.fromTrunk(revision, mockDocument, mockCommitBy, mockMessage);
    }
}
