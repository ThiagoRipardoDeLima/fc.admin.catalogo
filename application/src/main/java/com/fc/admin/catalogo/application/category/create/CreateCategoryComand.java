package com.fc.admin.catalogo.application.category.create;

public record CreateCategoryComand(
        String name,
        String description,
        boolean isActive
) {

    public static CreateCategoryComand with(
            final String aName,
            final String aDescription,
            final boolean isActive
    ){
        return new CreateCategoryComand(aName, aDescription, isActive);
    }
}
