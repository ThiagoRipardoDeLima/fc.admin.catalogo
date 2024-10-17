package com.fc.admin.catalogo.domain.category;

import com.fc.admin.catalogo.domain.AggregateRoot;
import com.fc.admin.catalogo.domain.validation.ValidationHandler;

import java.time.Instant;

public class Category extends AggregateRoot<CategoryID> {

    private String name;
    private String description;
    private boolean active;
    private Instant createAt;
    private Instant updateAt;
    private Instant deleteAt;

    private Category(
            final CategoryID anId,
            final String aName,
            final String aDescription,
            final boolean isActive,
            final Instant aCreateAt,
            final Instant aUpdateAt,
            final Instant aDeleteAt
    ) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.active = isActive;
        this.createAt = aCreateAt;
        this.updateAt = aUpdateAt;
        this.deleteAt = aDeleteAt;
    }

    public static Category newCategory(final String aName, final String aDescription, final boolean isActive){
        final CategoryID id = CategoryID.unique();
        final Instant createAt = Instant.now();
        final Instant updateAt = Instant.now();
        final Instant deleteAt = isActive ? null : Instant.now();

        return new Category(id, aName, aDescription, isActive, createAt, updateAt, deleteAt);
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public CategoryID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public Instant getDeleteAt() {
        return deleteAt;
    }
}
