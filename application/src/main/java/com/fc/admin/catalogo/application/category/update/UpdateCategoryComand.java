package com.fc.admin.catalogo.application.category.update;

public record UpdateCategoryComand(
    String id,
    String name,
    String description,
    boolean isActive
) {

    public static UpdateCategoryComand with(
            final String anId,
            final String aName,
            final String aDescription,
            final boolean isActive
    ){
        return new UpdateCategoryComand(anId, aName, aDescription, isActive);
    }

}
