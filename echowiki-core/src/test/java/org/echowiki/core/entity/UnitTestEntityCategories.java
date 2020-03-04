package org.echowiki.core.entity;

import org.echowiki.core.configuration.CoreConfiguration;
import org.echowiki.core.domain.Categories;
import org.echowiki.core.domain.Category;
import org.echowiki.core.domain.NameSpace;
import org.echowiki.core.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CoreConfiguration.class})
public class UnitTestEntityCategories {

    @Inject
    private Categories categories;

    @Inject
    private CategoryRepository repository;

    @Test
    public void unitTestNewCategory() {
        String name = "편집지침/일반 문서";
        NameSpace nameSpace = NameSpace.에코위키;
        Category category = categories.newCategory(name, nameSpace);
        assertNotNull(category);
        assertEquals(category.getName(), name);
        assertEquals(category.getNameSpace(), NameSpace.에코위키);

        Category parent = repository.byNameAndNameSpace("도움말", NameSpace.에코위키);
        category.addParent(parent);
        category = categories.newCategory(name, nameSpace, Arrays.asList(parent));
        assertNotNull(category);
        assertEquals(category.getName(), name);
        assertEquals(category.getNameSpace(), NameSpace.에코위키);
        assertTrue(category.getParents().contains(parent));
    }
}
