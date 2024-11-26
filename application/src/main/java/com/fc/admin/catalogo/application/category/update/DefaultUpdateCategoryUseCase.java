package com.fc.admin.catalogo.application.category.update;

import com.fc.admin.catalogo.domain.category.Category;
import com.fc.admin.catalogo.domain.category.CategoryGateway;
import com.fc.admin.catalogo.domain.category.CategoryID;
import com.fc.admin.catalogo.domain.exceptions.DomainException;
import com.fc.admin.catalogo.domain.validation.Error;
import com.fc.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

import static io.vavr.API.Try;
import static io.vavr.control.Either.left;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryComand aComand) {
        //destruction
        final var anId = CategoryID.from(aComand.id());
        final var aName = aComand.name();
        final var aDescription = aComand.description();
        final var aActive = aComand.isActive();

        final var aCategory = this.categoryGateway.findById(anId)
                .orElseThrow(notFound(anId));

        Notification notification = Notification.create();

        aCategory.update(aName, aDescription, aActive)
            .validate(notification);

        return notification.hasError() ? left(notification) : update(aCategory);

    }

    private Supplier<DomainException> notFound(final CategoryID anId) {
        return ()-> DomainException.with(
            new Error("Category not found with id %s".formatted(anId.getValue()))
        );
    }

    private Either<Notification, UpdateCategoryOutput> update(final Category aCategory) {
        return Try(() -> this.categoryGateway.update(aCategory))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);
    }
}
