package org.echowiki.core.repository;

import org.echowiki.core.domain.Document;
import org.echowiki.core.entity.EntityDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<EntityDocument, Long> {

    @Query("select d from Document d where d.id = ?1")
    Document getById(Long id);

    void save(Document document);

    @Query("select d from Document d where upper(title) = upper(?1)")
    List<Document> getByTitle(String title);

}
