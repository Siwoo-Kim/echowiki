package org.echowiki.core.entity;

import org.echowiki.core.configuration.CoreConfiguration;
import org.echowiki.core.configuration.DatabaseConfiguration;
import org.echowiki.core.entity.EntityDocument;
import org.echowiki.core.repository.DocumentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CoreConfiguration.class, DatabaseConfiguration.class})
public class UnitTestEntityDocument {

    @Inject
    private DocumentRepository documentRepository;

    @Test
    public void test() {
        EntityDocument document = documentRepository.getDocumentById(1L);
        System.out.println(document);
    }
}