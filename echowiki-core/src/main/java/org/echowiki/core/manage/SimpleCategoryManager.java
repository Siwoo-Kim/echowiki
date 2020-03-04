package org.echowiki.core.manage;

import org.apache.logging.log4j.util.Strings;
import org.echowiki.core.domain.Categories;
import org.echowiki.core.domain.Category;
import org.echowiki.core.domain.NameSpace;
import org.echowiki.core.entity.EntityCategory;
import org.echowiki.core.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class SimpleCategoryManager implements CategoryManager {

    @Inject
    private Categories categories;

    @Inject
    private CategoryRepository repository;

    @Override
    public Category getCategory(String name, NameSpace nameSpace) {
        checkArgument(Strings.isNotBlank(name));
        return repository.byNameAndNameSpace(name, nameSpace);
    }

    @Override
    public Category save(Category category) {
        return repository.save((EntityCategory) categories.newCategory(category));
    }
}
