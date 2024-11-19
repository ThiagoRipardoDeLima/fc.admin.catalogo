package com.fc.admin.catalogo.application.category.create;

import com.fc.admin.catalogo.domain.category.Category;
import com.fc.admin.catalogo.domain.category.CategoryGateway;
import com.fc.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.control.Either.left;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase{

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, CreateCategoryOutput> execute(final CreateCategoryComand aComand) {
        final var aName = aComand.name();
        final var aDescription = aComand.description();
        final var aActive = aComand.isActive();

        final var notication = Notification.create();

        final var aCategory = Category.newCategory(aName, aDescription, aActive);
        aCategory.validate(notication);

        return notication.hasError() ? left(notication) : create(aCategory);
    }

    private Either<Notification, CreateCategoryOutput> create(final Category aCategory) {
        return API.Try(() -> this.categoryGateway.create(aCategory))
                .toEither()
                .bimap(Notification::create, CreateCategoryOutput::from);
    }
}
