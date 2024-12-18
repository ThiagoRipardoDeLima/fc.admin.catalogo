package com.fc.admin.catalogo.application.category.create;

import com.fc.admin.catalogo.domain.category.CategoryGateway;

import com.fc.admin.catalogo.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidComand_whenCallsCreateCategory_shouldReturnCategoryId(){
        final var expectedName = "Filme";
        final var expectedDescription = "Categoria muito top";
        final var expectedIsActive = true;

        final var aComand = CreateCategoryComand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aComand).get();

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
        final String expectedName = null;
        final var expectedDescription = "Categoria muito top";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aComand =
                new CreateCategoryComand(expectedName, expectedDescription, expectedIsActive);

        final var notification = useCase.execute(aComand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, Mockito.times(0)).create(any());
    }

    @Test
    public void givenAValidComandWithInactiveCategory_whenCallsCreateCategory_shouldReturnInactiveCategoryId(){
        final var expectedName = "Filme";
        final var expectedDescription = "Categoria muito top";
        final var expectedIsActive = false;

        final var aComand = CreateCategoryComand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aComand).get();

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
                                    && Objects.nonNull(aCategory.getDeleteAt());
                        }
                ));
    }

    @Test
    public void givenAValidComand_whenGatewayThrowsRandomException_shouldReturnAException(){
        final var expectedName = "Filme";
        final var expectedDescription = "Categoria muito top";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Gateway error";
        final var expectedErrorCount = 1;

        final var aComand = CreateCategoryComand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any()))
                .thenThrow(new IllegalStateException("Gateway error"));

        final var notification = useCase.execute(aComand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

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
}
