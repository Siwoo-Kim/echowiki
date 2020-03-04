package org.echowiki.core.entity;

import org.echowiki.core.domain.Categories;
import org.echowiki.core.domain.Category;
import org.echowiki.core.domain.NameSpace;
import org.echowiki.core.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class EntityCategories implements Categories {

    @Inject
    private CategoryRepository repository;

    @Override
    public Category newCategory(String name, NameSpace nameSpace) {
        return newCategory(name, nameSpace, Collections.emptyList());
    }

    @Override
    public Category newCategory(Category category) {
        return newCategory(category.getName(), category.getNameSpace(), new ArrayList<>(category.getParents()));
    }

    @Override
    public Category newCategory(String name, NameSpace nameSpace, List<Category> parents) {
        checkExists(name, nameSpace);
        Category category = new EntityCategory(name, nameSpace).checkState();
        for (Category p : parents) {
            Category parent = repository.byNameAndNameSpace(p.getName(), p.getNameSpace());
            checkNotNull(parent,
                    String.format("When create new category, it cannot resolve the parent category [%s].", p.getName()));
            category.addParent(parent);
        }
        return category;
    }

    private void checkExists(String name, NameSpace nameSpace) {
        checkArgument(repository.byNameAndNameSpace(name, nameSpace) == null,
                String.format("When create new category, Category [%s] already exists.", name));
    }

}
