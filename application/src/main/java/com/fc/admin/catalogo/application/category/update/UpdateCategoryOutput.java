package com.fc.admin.catalogo.application.category.update;

import com.fc.admin.catalogo.domain.category.Category;
import com.fc.admin.catalogo.domain.category.CategoryID;

public record UpdateCategoryOutput(CategoryID id) {

    public static UpdateCategoryOutput from(final Category aCategory) {
        return new UpdateCategoryOutput(aCategory.getId());
    }

}
