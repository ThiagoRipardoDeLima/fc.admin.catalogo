package com.fc.admin.catalogo.application.category.create;

import com.fc.admin.catalogo.domain.category.CategoryGateway;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;

import java.util.Objects;

public class CreateCategoryUseCaseTest {

    @Test
    public void givenAnValidComand_whenCallsCreateCategory_shouldReturnCategoryId(){

        final var expectedName = "Filme";
        final var expectedDescription = "Categoria muito top";
        final var expectedIsActive = true;

        final var aComand = CreateCategoryComand.with(expectedName, expectedDescription, expectedIsActive);

        final CategoryGateway categoryGateway = Mockito.mock(CategoryGateway.class);
        Mockito.when(categoryGateway.create(Mockito.any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var useCase = new DefaultCreateCategoryUseCase(categoryGateway);

        final var actualOutput = useCase.execute(aComand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .create(Mockito.argThat(aCategory -> {
                            return Objects.equals(expectedName, aCategory.getName())
                                    && Objects.equals(expectedDescription, aCategory.getDescription())
                                    && Objects.equals(expectedIsActive, aCategory.isActive())
                                    && Objects.nonNull(aCategory.getId())
                                    && Objects.nonNull(aCategory.getCreateAt())
                                    && Objects.nonNull(aCategory.getUpdateAt())
                                    && Objects.isNull(aCategory.getDeleteAt());
                        }
                ));
    }

    @Test
    public void givenAnIvalidName_whenCallsCreateCategory_thenShouldReturnDomainException(){
        final var expectedName = "Filme";
        final var expectedDescription = "Categoria muito top";
        final var expectedIsActive = true;

        final var aComand = new CreateCategoryComand(expectedName, expectedDescription, expectedIsActive);

        final CategoryGateway categoryGateway = Mockito.mock(CategoryGateway.class);
        Mockito.when(categoryGateway.create(Mockito.any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var usecase = new DefaultCreateCategoryUseCase(categoryGateway);

        final var actualOutput = usecase.execute(aComand);

    }

}
