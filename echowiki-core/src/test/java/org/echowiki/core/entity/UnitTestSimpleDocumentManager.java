package org.echowiki.core.entity;

import org.echowiki.core.configuration.CoreConfiguration;
import org.echowiki.core.configuration.DatabaseConfiguration;
import org.echowiki.core.manage.DocumentManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, CoreConfiguration.class})
public class UnitTestSimpleDocumentManager {

    @Inject
    private DocumentManager documentManager;

    @Test
    public void unitTestSave() {

    }
}
