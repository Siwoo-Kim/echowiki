package org.echowiki.core.manage;

import org.echowiki.core.domain.Category;
import org.echowiki.core.domain.NameSpace;

public interface CategoryManager {

    Category getCategory(String name, NameSpace nameSpace);

    Category save(Category category);

}
