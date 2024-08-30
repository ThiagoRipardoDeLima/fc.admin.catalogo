package com.fc.admin.catalogo.domain.category;

import java.time.Instant;
import java.util.UUID;

public class Category {
    private String id;
    private String name;
    private String description;
    private boolean active;
    private Instant createAt;
    private Instant updateAt;
    private Instant deleteAt;

    private Category(
            final String id,
            final String name,
            final String description,
            final boolean active,
            final Instant createAt,
            final Instant updateAt,
            final Instant deleteAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.deleteAt = deleteAt;
    }

    public static Category newCategory(final String aName, final String aDescription, final boolean isActive){
        final String id = UUID.randomUUID().toString();
        final Instant createAt = Instant.now();
        final Instant updateAt = Instant.now();
        final Instant deleteAt = null;

        return new Category(id, aName, aDescription, isActive, createAt, updateAt, deleteAt);
    }

    public String getId() {
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
