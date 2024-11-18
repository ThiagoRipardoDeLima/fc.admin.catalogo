package com.fc.admin.catalogo.application.category.create;

import com.fc.admin.catalogo.domain.category.Category;
import com.fc.admin.catalogo.domain.category.CategoryGateway;
import com.fc.admin.catalogo.domain.validation.handler.Notification;
import com.fc.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase{

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CreateCategoryOutput execute(final CreateCategoryComand aComand) {
        final var aName = aComand.name();
        final var aDescription = aComand.description();
        final var aActive = aComand.isActive();

        final var notication = Notification.create();

        final var aCategory = Category.newCategory(aName, aDescription, aActive);
        aCategory.validate(notication);

        if(notication.hasError()){
            // return errors
        }

        return CreateCategoryOutput.from(this.categoryGateway.create(aCategory));
    }
}
