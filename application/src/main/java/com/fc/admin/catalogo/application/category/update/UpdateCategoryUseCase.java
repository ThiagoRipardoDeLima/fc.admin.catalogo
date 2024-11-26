package com.fc.admin.catalogo.application.category.update;

import com.fc.admin.catalogo.application.UseCase;
import com.fc.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase
        extends UseCase<UpdateCategoryComand, Either<Notification, UpdateCategoryOutput>> {
}
