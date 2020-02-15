package org.echowiki.core.repository;

import org.echowiki.core.domain.Category;
import org.echowiki.core.entity.EntityCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<EntityCategory, Long> {

    @Query("select c from Category c where c.parent is null")
    List<Category> getRoots();

}
