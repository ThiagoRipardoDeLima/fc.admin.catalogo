package com.fc.admin.catalogo.application.category.create;

import com.fc.admin.catalogo.application.UseCase;
import com.fc.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase
    extends UseCase<CreateCategoryComand, Either<Notification, CreateCategoryOutput>> {
}
