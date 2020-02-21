package org.echowiki.core.repository;

import org.echowiki.core.entity.EntityDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<EntityDocument, Long> {

    EntityDocument getDocumentById(Long id);

}
