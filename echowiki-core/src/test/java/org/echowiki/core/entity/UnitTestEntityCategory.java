package org.echowiki.core.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

public class UnitTestEntityCategory {

    private static final String MOCK_JSON = ClassLoader.getSystemClassLoader().getResource("./mock/entity_category_mock.json").getFile();
    private List<EntityCategory> MOCK;

    @Before
    public void setup() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream in = new BufferedInputStream(new FileInputStream(MOCK_JSON))) {
            this.MOCK = mapper.readValue(in, new TypeReference<List<EntityCategory>>() {
            });
        }
    }

    @Test
    public void unitTestAddChild() {
        EntityCategory parent = MOCK.get(0);
        EntityCategory child = MOCK.get(1);
        EntityCategory grandChild = MOCK.get(2);

        //when
        parent.addChild(child);

        assertTrue(parent.hasChild(child));
        assertTrue(child.hasParent(parent));
        assertEquals(parent.getChildren().size(), 1);
        assertEquals(child.getParents().size(), 1);

        //when
        child.addChild(grandChild);

        assertTrue(child.hasChild(grandChild));
        assertTrue(grandChild.hasParent(child));
        assertEquals(child.getChildren().size(), 1);
        assertEquals(grandChild.getParents().size(), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unitTestAddChildWhenRefItself() {
        MOCK.get(0).addChild(MOCK.get(0));
    }

    @Test
    public void unitTestRemoveChild() {
        EntityCategory parent = MOCK.get(0);
        EntityCategory child1 = MOCK.get(1);
        EntityCategory child2 = MOCK.get(2);
        EntityCategory grandChild1 = MOCK.get(3);
        EntityCategory grandChild2 = MOCK.get(4);

        parent.addChild(child1);
        parent.addChild(child2);
        child1.addChild(grandChild1);
        child1.addChild(grandChild2);

        //when
        parent.removeChild(child1);

        assertFalse(parent.hasChild(child1));
        assertTrue(parent.hasChild(child2));
        assertFalse(child1.hasParent(parent));
        assertTrue(child2.hasParent(parent));

        //when
        child1.removeChild(grandChild1);

        assertFalse(child1.hasChild(grandChild1));
        assertTrue(child1.hasChild(grandChild2));
        assertFalse(grandChild1.hasParent(child1));
        assertTrue(grandChild2.hasParent(child1));
    }
}

