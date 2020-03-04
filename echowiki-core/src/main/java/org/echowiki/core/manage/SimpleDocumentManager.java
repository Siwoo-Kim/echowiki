package org.echowiki.core.manage;

import org.echowiki.core.domain.Document;
import org.echowiki.core.entity.EntityDocument;
import org.echowiki.core.entity.EntityDocuments;
import org.echowiki.core.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class SimpleDocumentManager implements DocumentManager {

    @Inject
    private EntityDocuments entityDocuments;

    @Inject
    private DocumentRepository repository;

    @Override
    public Document save(Document document) {
        checkNotNull(document);
        EntityDocument entity = (EntityDocument) entityDocuments.newDocument(document);
        return repository.save(entity);
    }
}
