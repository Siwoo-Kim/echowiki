package org.echowiki.core.repository;

import org.echowiki.core.domain.Category;
import org.echowiki.core.domain.NameSpace;
import org.echowiki.core.entity.EntityCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<EntityCategory, Long> {

    @Query("select c from Category c where upper(c.name) = upper(?1) and c.nameSpace = ?2")
    Category byNameAndNameSpace(String name, NameSpace nameSpace);

}
