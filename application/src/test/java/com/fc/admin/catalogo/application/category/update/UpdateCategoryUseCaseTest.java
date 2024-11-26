package com.fc.admin.catalogo.application.category.update;

import com.fc.admin.catalogo.domain.category.Category;
import com.fc.admin.catalogo.domain.category.CategoryGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    // teste do caminho feliz
    // teste passando uma propriedade invalida
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
                .thenReturn(Optional.of(aCategory));

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

}
