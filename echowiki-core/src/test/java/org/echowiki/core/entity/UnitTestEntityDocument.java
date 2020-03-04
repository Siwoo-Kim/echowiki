package org.echowiki.core.entity;

import org.echowiki.core.configuration.CoreConfiguration;
import org.echowiki.core.configuration.DatabaseConfiguration;
import org.echowiki.core.domain.Category;
import org.echowiki.core.domain.Document;
import org.echowiki.core.domain.Documents;
import org.echowiki.core.domain.NameSpace;
import org.echowiki.core.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, CoreConfiguration.class})
public class UnitTestEntityDocument {

    @Inject
    private Documents documents;

    @Inject
    private CategoryRepository categoryRepository;

    @Test
    public void unitTestNewDocument() {
        String name = "doc1";
        String content = "content";
        Category category = createFixtureCategory();
        Document document = documents.newDocument(name, content, category);
        assertEquals(document.getName(), name);
        assertEquals(document.getContent(), content);
        assertEquals(document.getCategory().getName(), category.getName());
        assertNotNull(document.getCommit());
        assertTrue(document.getCommit().isRoot());
        assertTrue(document.getCommit().isTrunk());

        document = documents.newDocument(document);
        assertEquals(document.getName(), name);
        assertEquals(document.getContent(), content);
        assertEquals(document.getCategory(), category);
        assertNotNull(document.getCommit());
        assertTrue(document.getCommit().isRoot());
        assertTrue(document.getCommit().isTrunk());
    }

    private Category createFixtureCategory() {
        return categoryRepository.byNameAndNameSpace("도움말", NameSpace.에코위키);
    }

    @Test
    public void unitTestNewLonerDocument() {
        String name = "doc1";
        String content = "content";
        Document document = documents.newLonerDocument(name, content);
        assertEquals(document.getName(), name);
        assertEquals(document.getContent(), content);
        assertNull(document.getCategory());
        assertTrue(document.isLoner());
    }

    @Test
    public void unitTestCommitDocument() {
        String name = "doc1";
        String content = "content";
        Document document = documents.newLonerDocument(name, content);
        String newName = "doc2";
        String newContent = "content2";
        Document committedDocument = documents.commitDocument(newName, newContent, document);
        assertEquals(committedDocument.getName(), newName);
        assertEquals(committedDocument.getContent(), newContent);
        assertEquals(committedDocument.getCommit().getIndex(), "r2");
        assertEquals(committedDocument.getCommit().previous().getDocument(), document);
    }
}
