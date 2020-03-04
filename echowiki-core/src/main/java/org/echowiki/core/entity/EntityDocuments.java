package org.echowiki.core.entity;

import org.echowiki.core.domain.*;
import org.echowiki.core.manage.CategoryManager;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class EntityDocuments implements Documents {

    @Inject
    private CategoryManager categoryManager;

    @Inject
    private Commits commits;

    @Override
    public Document newDocument(Document document) {
        return newDocument(document.getName(), document.getContent(), document.getCategory());
    }

    @Override
    public Document newDocument(String name, String content, Category category) {
        category = categoryManager.getCategory(category.getName(), category.getNameSpace());
        checkNotNull(category);
        return new EntityDocument(name, content, category, commits.newRoot()).checkState();
    }

    @Override
    public Document newLonerDocument(String name, String content) {
        return new EntityDocument(name, content, null, commits.newRoot()).checkState();
    }

    @Override
    public Document commitDocument(String name, String content, Document oldDocument) {
        oldDocument = oldDocument.checkState();
        Commit newCommit = commits.newCommit(oldDocument.getCommit());
        return new EntityDocument(name, content, oldDocument.getCategory(), newCommit).checkState();
    }

}
