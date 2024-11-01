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

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenAnValidComand_whenCallsCreateCategory_shouldReturnCategoryId(){
        final var expectedName = "Filme";
        final var expectedDescription = "Categoria muito top";
        final var expectedIsActive = true;

        final var aComand = CreateCategoryComand.with(expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.create(Mockito.any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

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
        final var expectedErrorMessage = "";
        final var expectedErrorCount = 1;

        final var aComand = new CreateCategoryComand(expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.create(Mockito.any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var actualOutput = useCase.execute(aComand);

        Assertions.assertThrows(DomainException.class, ()-> useCase.execute(aComand));

    }

}
