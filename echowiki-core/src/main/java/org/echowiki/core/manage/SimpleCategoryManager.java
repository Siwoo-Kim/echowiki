package org.echowiki.core.manage;

import org.echowiki.core.domain.Category;
import org.echowiki.core.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class SimpleCategoryManager implements CategoryManager {

    @Inject
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getRoots() {
        return categoryRepository.getRoots();
    }

}
