package com.fc.admin.catalogo.application.category.update;

import com.fc.admin.catalogo.application.category.create.CreateCategoryComand;
import com.fc.admin.catalogo.domain.category.Category;
import com.fc.admin.catalogo.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    //ok teste do caminho feliz
    //ok teste passando uma propriedade invalida
    // teste atualizando uma categoria para inativa
    // teste simulando um erro generico vindo do gateway
    // teste atualizando uma categoria passando ID invalido

    @Test
    public void givenAValidComand_whenCallsUpdateCategory_shouldReturnCategoryId(){
        final var aCategory =
                Category.newCategory("film", null, true);

        final var expectedName = "filmes";
        final var expectedDescription = "A categoria mais top";
        final var expectedIsActive = true;

        final var expectedId = aCategory.getId();

        final var aComand = UpdateCategoryComand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aComand).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1)).findById(eq(expectedId));
        verify(categoryGateway, times(1)).update(argThat(
                aUpdateCategory ->
                        Objects.equals(expectedName, aUpdateCategory.getName())
                            && Objects.equals(expectedDescription, aUpdateCategory.getDescription())
                            && Objects.equals(expectedIsActive, aUpdateCategory.isActive())
                            && Objects.equals(expectedId, aUpdateCategory.getId())
                            && Objects.equals(aCategory.getCreateAt(), aUpdateCategory.getCreateAt())
                            && aCategory.getUpdateAt().isBefore(aUpdateCategory.getUpdateAt())
                            && Objects.isNull(aUpdateCategory.getDeleteAt())
        ));
    }

    @Test
    public void givenAnIvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException(){
        final var aCategory =
                Category.newCategory("film", null, true);

        final String expectedName = null;
        final var expectedDescription = "Categoria muito top";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();

        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aComand = UpdateCategoryComand
                .with(aCategory.getId().getValue(), expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Category.with(aCategory)));

        final var notification = useCase.execute(aComand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, Mockito.times(0)).update(any());
    }

    @Test
    public void givenAValidInactiveComand_whenCallsUpdateCategory_shouldReturnCategoryId(){
        final var aCategory =
                Category.newCategory("film", null, true);

        final var expectedName = "filmes";
        final var expectedDescription = "A categoria mais top";
        final var expectedIsActive = false;

        final var expectedId = aCategory.getId();

        final var aComand = UpdateCategoryComand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        assertTrue(aCategory.isActive());
        assertNull(aCategory.getDeleteAt());

        final var actualOutput = useCase.execute(aComand).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1)).findById(eq(expectedId));
        verify(categoryGateway, times(1)).update(argThat(
                aUpdateCategory ->
                        Objects.equals(expectedName, aUpdateCategory.getName())
                                && Objects.equals(expectedDescription, aUpdateCategory.getDescription())
                                && Objects.equals(expectedIsActive, aUpdateCategory.isActive())
                                && Objects.equals(expectedId, aUpdateCategory.getId())
                                && Objects.equals(aCategory.getCreateAt(), aUpdateCategory.getCreateAt())
                                && aCategory.getUpdateAt().isBefore(aUpdateCategory.getUpdateAt())
                                && Objects.isNull(aUpdateCategory.getDeleteAt())
        ));
    }

}
