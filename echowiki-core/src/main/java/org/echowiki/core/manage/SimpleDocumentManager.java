package org.echowiki.core.manage;

import org.apache.logging.log4j.util.Strings;
import org.echowiki.core.domain.Document;
import org.echowiki.core.meta.Persistable;
import org.echowiki.core.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleDocumentManager implements DocumentManager {

    @Inject
    private DocumentRepository repository;

    @Override
    public boolean exits(String title) {
        if (Strings.isBlank(title)) return false;
        return !repository.getByTitle(title).isEmpty();
    }

    /**
     * @param document
     * @return
     */
    @Override
    public Persistable save(Document document) {
        return null;
    }

    /**
     * @param name
     * @return
     */
    @Override
    public Optional<Document> getHeadOf(String name) {
        List<Document> documents = repository.getByTitle(name);
        Document trunk = null;
        for (Document d : documents)
            if (d.isTrunk()) {
                trunk = d;
                break;
            }
        //should happened
        assert !documents.isEmpty() && trunk != null;
        return Optional.ofNullable(trunk);
    }

}
