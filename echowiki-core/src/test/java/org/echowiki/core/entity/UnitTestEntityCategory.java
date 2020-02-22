package org.echowiki.core.entity;

import org.echowiki.core.configuration.CoreConfiguration;
import org.echowiki.core.configuration.DatabaseConfiguration;
import org.echowiki.core.domain.Category;
import org.echowiki.core.domain.Tree;
import org.echowiki.core.entity.EntityCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CoreConfiguration.class, DatabaseConfiguration.class})
public class UnitTestEntityCategory {

    @Test
    public void unitTestAddChildAndSetParent() {
        Category parent = EntityCategory.builder()
                .id(1L)
                .title("TEST1")
                .build();
        Category child_level2_1 = EntityCategory.builder()
                .id(2L)
                .title("TEST2-1")
                .build();

        parent.addChild(child_level2_1);
        assertThat(parent.getChildren(), hasItems(child_level2_1));
        assertEquals(child_level2_1.getParent(), parent);

        Category child_level2_2 = EntityCategory.builder()
                .id(3L)
                .title("TEST2-2")
                .build();
        child_level2_2.setParent(parent);

        assertThat(parent.getChildren(), hasItems(child_level2_1));
        assertThat(parent.getChildren(), hasItems(child_level2_2));
        assertEquals(child_level2_1.getParent(), parent);
        assertEquals(child_level2_2.getParent(), parent);

        Category child_level3_1 = EntityCategory.builder()
                .id(4L)
                .title("TEST3-1")
                .build();
        child_level2_1.addChild(child_level3_1);

        assertThat(parent.getChildren(), hasItems(child_level2_1));
        assertThat(parent.getChildren(), hasItems(child_level2_2));
        assertThat(child_level2_1.getChildren(), hasItems(child_level3_1));
        assertEquals(child_level2_1.getParent(), parent);
        assertEquals(child_level2_2.getParent(), parent);
        assertEquals(child_level3_1.getParent(), child_level2_1);

        Category child_level3_2 = EntityCategory.builder()
                .id(5L)
                .title("TEST3-2")
                .build();
        child_level2_2.addChild(child_level3_2);

        assertThat(parent.getChildren(), hasItems(child_level2_1));
        assertThat(parent.getChildren(), hasItems(child_level2_2));
        assertThat(child_level2_1.getChildren(), hasItems(child_level3_1));
        assertThat(child_level2_2.getChildren(), hasItems(child_level3_2));
        assertEquals(child_level2_1.getParent(), parent);
        assertEquals(child_level2_2.getParent(), parent);
        assertEquals(child_level3_1.getParent(), child_level2_1);
        assertEquals(child_level3_2.getParent(), child_level2_2);
        assertTrue(child_level3_1.isLeaf());
        assertTrue(child_level3_2.isLeaf());

        assertEquals(child_level3_1.getRoot(), parent);
    }

    @Test
    public void unitTestIsRootAndGetRoot() {
        Category parent = EntityCategory.builder()
                .id(1L)
                .title("TEST1")
                .build();
        Category child_level2_1 = EntityCategory.builder()
                .id(2L)
                .title("TEST2-1")
                .build();
        Category child_level2_2 = EntityCategory.builder()
                .id(3L)
                .title("TEST2-2")
                .build();

        parent.addChild(child_level2_1);
        parent.addChild(child_level2_2);

        assertTrue(parent.isRoot());
        assertFalse(child_level2_1.isRoot());
        assertFalse(child_level2_2.isRoot());
        assertEquals(child_level2_1.getRoot(), parent);
        assertEquals(child_level2_2.getRoot(), parent);

        Category child_level3_1 = EntityCategory.builder()
                .id(4L)
                .title("TEST3-1")
                .build();
        child_level2_1.addChild(child_level3_1);
        Category child_level3_2 = EntityCategory.builder()
                .id(5L)
                .title("TEST3-2")
                .build();
        child_level2_2.addChild(child_level3_2);

        assertTrue(parent.isRoot());
        assertFalse(child_level2_1.isRoot());
        assertFalse(child_level2_2.isRoot());
        assertFalse(child_level3_1.isRoot());
        assertFalse(child_level3_2.isRoot());
        assertEquals(child_level2_1.getRoot(), parent);
        assertEquals(child_level2_2.getRoot(), parent);
        assertEquals(child_level3_1.getRoot(), parent);
        assertEquals(child_level3_2.getRoot(), parent);
    }


    @Test
    public void unitTestRemoveChild() {
        Category parent = EntityCategory.builder()
                .id(1L)
                .title("TEST1")
                .build();
        Category child_level2_1 = EntityCategory.builder()
                .id(2L)
                .title("TEST2-1")
                .build();
        Category child_level2_2 = EntityCategory.builder()
                .id(3L)
                .title("TEST2-2")
                .build();

        parent.removeChild(child_level2_1);
        assertThat(parent.getChildren(), not(hasItems(child_level2_1)));
        assertNull(child_level2_1.getParent());

        parent.addChild(child_level2_1);
        parent.addChild(child_level2_2);

        parent.removeChild(child_level2_1);
        assertThat(parent.getChildren(), not(hasItems(child_level2_1)));
        assertThat(parent.getChildren(), hasItems(child_level2_2));
        assertNull(child_level2_1.getParent());
        assertEquals(child_level2_2.getParent(), parent);
    }

    @Test
    public void unitTestGetDescendants() {
        Category parent = EntityCategory.builder()
                .id(1L)
                .title("TEST1")
                .build();
        Category child_level2_1 = EntityCategory.builder()
                .id(2L)
                .title("TEST2-1")
                .build();
        Category child_level2_2 = EntityCategory.builder()
                .id(3L)
                .title("TEST2-2")
                .build();
        Category child_level2_3 = EntityCategory.builder()
                .id(3L)
                .title("TEST2-3")
                .build();
        Category child_level3_1 = EntityCategory.builder()
                .id(4L)
                .title("TEST3-1")
                .build();
        Category child_level3_2 = EntityCategory.builder()
                .id(5L)
                .title("TEST3-2")
                .build();
        Category child_level3_3 = EntityCategory.builder()
                .id(6L)
                .title("TEST3-3")
                .build();
        Category child_level3_4 = EntityCategory.builder()
                .id(7L)
                .title("TEST3-4")
                .build();

        parent.addChild(child_level2_1);
        parent.addChild(child_level2_2);
        parent.addChild(child_level2_3);
        child_level2_1.addChild(child_level3_1);
        child_level2_2.addChild(child_level3_2);
        child_level2_3.addChild(child_level3_3);
        child_level2_3.addChild(child_level3_4);

        List<Category> results = parent.getDescendants(Tree.Traversal.LEVEL);
        assertArrayEquals(results.toArray(new Category[0]),
                new Category[]{parent, child_level2_1, child_level2_2, child_level2_3,
                        child_level3_1, child_level3_2, child_level3_3, child_level3_4});
    }
}
