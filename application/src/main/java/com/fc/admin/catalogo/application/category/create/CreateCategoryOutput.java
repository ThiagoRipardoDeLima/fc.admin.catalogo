package com.fc.admin.catalogo.application.category.create;

import com.fc.admin.catalogo.domain.category.Category;
import com.fc.admin.catalogo.domain.category.CategoryID;

public record CreateCategoryOutput(CategoryID id) {

    public static CreateCategoryOutput from(Category aCategory){
        return new CreateCategoryOutput(aCategory.getId());
    }
}
