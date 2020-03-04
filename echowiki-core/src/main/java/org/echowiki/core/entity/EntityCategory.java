package org.echowiki.core.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.echowiki.core.check.Check;
import org.echowiki.core.domain.Category;
import org.echowiki.core.domain.Document;
import org.echowiki.core.domain.EventTime;
import org.echowiki.core.domain.NameSpace;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity(name = "Category")
@Table(name = "category")
public class EntityCategory implements Category, Check<Category> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private EntityEventTime eventTime;

    @Column(unique = true)
    String name;

    @ManyToMany(targetEntity = EntityCategory.class, fetch = FetchType.EAGER)
    @JoinTable(name = "category_join",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id"))
    List<Category> parents = new ArrayList<>();

    @ManyToMany(targetEntity = EntityCategory.class, fetch = FetchType.EAGER)
    @JoinTable(name = "category_join",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "child_id"))
    List<Category> children = new ArrayList<>();

    @OneToMany(mappedBy = "category",
            targetEntity = EntityDocument.class,
            fetch = FetchType.EAGER)
    List<Document> documents = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @JoinTable(name = "namespace",
            joinColumns = @JoinColumn(name = "namespace"))
    NameSpace nameSpace;

    public EntityCategory(String name, NameSpace nameSpace) {
        this.name = name;
        this.nameSpace = nameSpace;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public NameSpace getNameSpace() {
        return nameSpace;
    }

    @Override
    public List<? extends Category> getParents() {
        return new ArrayList<>(parents);
    }

    @Override
    public List<? extends Category> getChildren() {
        return new ArrayList<>(children);
    }

    @Override
    public void addChild(Category child) {
        checkRelation(child);
        if (!hasChild(child))
            children.add(child);
        if (!child.hasParent(this))
            child.addParent(this);
    }

    @Override
    public void removeChild(Category child) {
        checkRelation(child);
        if (hasChild(child))
            children.remove(child);
        if (child.hasParent(this))
            child.removeParent(this);
    }

    @Override
    public void addParent(Category parent) {
        checkRelation(parent);
        if (!hasParent(parent))
            parents.add(parent);
        if (!parent.hasChild(this))
            parent.addChild(this);
    }

    @Override
    public void removeParent(Category parent) {
        checkRelation(parent);
        if (hasParent(parent))
            parents.remove(parent);
        if (parent.hasChild(this))
            parent.removeChild(this);
    }

    @Override
    public boolean hasParent(Category parent) {
        return parents.contains(parent);
    }

    @Override
    public boolean hasChild(Category child) {
        return children.contains(child);
    }

    @Override
    public List<Document> getDocuments() {
        return new ArrayList<>(documents);
    }

    @Override
    public EventTime getEventTime() {
        return eventTime;
    }

    @Override
    public void addDocument(Document document) {
        checkNotNull(document);
        checkArgument(document instanceof EntityDocument);
        EntityDocument doc = (EntityDocument) document;
        if (!documents.contains(doc))
            documents.add(doc);
        if (!document.getCategory().equals(this))
            doc.category = this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityCategory that = (EntityCategory) o;
        return Objects.equals(name, that.name) &&
                nameSpace == that.nameSpace;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nameSpace);
    }

    @Override
    public void checkRelation(Category category) {
        checkNotNull(category, "When making an association, Category cannot be null.");
        checkArgument(nameSpace == category.getNameSpace());
        checkArgument(Strings.isNotBlank(category.getName()),
                "When making an association, Category Name shouldn't blank", category.getName());
        checkArgument(!this.equals(category),
                "When making an association, Category cannot have association with itself.", category.getName());
    }

    @Override
    public Category checkState() {
        checkArgument(Strings.isNotBlank(name));
        checkNotNull(nameSpace);
        return this;
    }
}
