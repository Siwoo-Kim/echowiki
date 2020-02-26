package org.echowiki.core.manage;

import org.echowiki.core.configuration.CoreConfiguration;
import org.echowiki.core.configuration.DatabaseConfiguration;
import org.echowiki.core.domain.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, CoreConfiguration.class})
public class UnitTestDocumentManager {

    @Inject
    private DocumentManager documentManager;

    @Transactional
    @Test
    public void unitTestExists() {
        assertTrue(documentManager.exits("Java"));
        assertTrue(documentManager.exits("java"));
        assertTrue(documentManager.exits("조선"));
        assertFalse(documentManager.exits("Happy"));
    }

    @Test
    public void unitTestGetHeadOf() {
        Document document = documentManager.getHeadOf("java").orElse(null);
        assertNotNull(document);
        assertEquals(document.getTitle(), "Java");
        assertTrue(document.isTrunk());
    }

}
