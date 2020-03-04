package org.echowiki.core.entity;

import org.echowiki.core.configuration.CoreConfiguration;
import org.echowiki.core.domain.Category;
import org.echowiki.core.domain.NameSpace;
import org.echowiki.core.manage.SimpleCategoryManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CoreConfiguration.class)
public class UnitTestSimpleCategoryManager {

    @Inject
    private SimpleCategoryManager categoryManager;

    @Transactional
    @Test
    public void unitTestSave() {
        Category category = new EntityCategory("에코위키의 규정", NameSpace.에코위키);
        Category parent = categoryManager.getCategory("에코위키의 도움말", NameSpace.에코위키);
        category.addParent(parent);
        category = categoryManager.save(category);

        assertNotNull(category);
        assertEquals(category.getName(), "에코위키의 규정");
        assertTrue(category.getParents().contains(parent));
    }
}
