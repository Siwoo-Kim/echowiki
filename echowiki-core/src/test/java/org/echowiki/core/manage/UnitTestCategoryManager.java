package org.echowiki.core.manage;

import org.echowiki.core.configuration.CoreConfiguration;
import org.echowiki.core.configuration.DatabaseConfiguration;
import org.echowiki.core.domain.Category;
import org.echowiki.core.domain.NameSpace;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, CoreConfiguration.class})
public class UnitTestCategoryManager {

    @Inject
    private CategoryManager categoryManager;

    @Test
    public void unitTestGetCategory() {
        Category category = categoryManager.getCategory("조선", NameSpace.일반);
        assertNotNull(category);
        assertEquals(category.getName(), "조선");
        assertEquals(category.getNameSpace(), NameSpace.일반);

        List<? extends Category> children = category.getChildren();
        assertFalse(children.isEmpty());
    }
}
