package org.echowiki.core.manage;

import org.echowiki.core.domain.Document;
import org.echowiki.core.meta.Persistable;

import java.util.Optional;

public interface DocumentManager {

    boolean exits(String document);

    Persistable save(Document document);

    Optional<Document> getHeadOf(String name);

}
