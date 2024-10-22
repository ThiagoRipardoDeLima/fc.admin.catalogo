package com.fc.admin.catalogo.domain;

import com.fc.admin.catalogo.domain.category.Category;
import com.fc.admin.catalogo.domain.exceptions.DomainException;
import com.fc.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void givenValidParams_whenCallNewCategory_thenInstantiateACategory(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais top";
        final var expectedIsActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreateAt());
        Assertions.assertNotNull(actualCategory.getUpdateAt());
        Assertions.assertNull(actualCategory.getDeleteAt());

    }

    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError(){
        final String expectedName = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedDescription = "A categoria mais top";
        final var expectedIsActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError(){
        final String expectedName = "  ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedDescription = "A categoria mais top";
        final var expectedIsActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameLengthLess3_whenCallNewCategoryAndValidate_thenShouldReceiveError(){
        final var expectedName = "Fi ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characteres";
        final var expectedDescription = "A categoria mais top";
        final var expectedIsActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameLengthMoreThan255_whenCallNewCategoryAndValidate_thenShouldReceiveError(){
        final String expectedName = """
                Caros amigos, a execução dos pontos do programa nos obriga à análise dos níveis de motivação 
                departamental. É claro que o consenso sobre a necessidade de qualificação estende o alcance e a 
                importância do investimento em reciclagem técnica. Assim mesmo, a valorização de fatores subjetivos
                """;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characteres";
        final var expectedDescription = "A categoria mais top";
        final var expectedIsActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAValidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldReceiveOk(){
        final var expectedName = "Filmes";
        final var expectedDescription = " ";
        final var expectedIsActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(()-> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreateAt());
        Assertions.assertNotNull(actualCategory.getUpdateAt());
        Assertions.assertNull(actualCategory.getDeleteAt());

    }

    @Test
    public void givenAValidFalseIsActive_whenCallNewCategoryAndValidate_thenShouldReceiveOk(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais top";
        final var expectedIsActive = false;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(()-> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreateAt());
        Assertions.assertNotNull(actualCategory.getUpdateAt());
        Assertions.assertNotNull(actualCategory.getDeleteAt());

    }

    @Test
    public void givenAValidActiveCategory_whenCallDeactivate_thenReturnCategoryInactivated(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais top";
        final var expectedIsActive = false;

        final var aCategory =
                Category.newCategory(expectedName, expectedDescription, true);

        Assertions.assertDoesNotThrow(()-> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getCreateAt();
        final var updatedAt = aCategory.getUpdateAt();

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeleteAt());

        final var actualCategory = aCategory.deactivate();

        Assertions.assertDoesNotThrow(()-> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreateAt());
        Assertions.assertTrue(actualCategory.getUpdateAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualCategory.getDeleteAt());
    }

    @Test
    public void givenAValidInactiveCategory_whenCallActivate_thenReturnCategoryActivated(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais top";
        final var expectedIsActive = true;

        final var aCategory =
                Category.newCategory(expectedName, expectedDescription, false);

        Assertions.assertDoesNotThrow(()-> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getCreateAt();
        final var updatedAt = aCategory.getUpdateAt();

        Assertions.assertFalse(aCategory.isActive());
        Assertions.assertNotNull(aCategory.getDeleteAt());

        final var actualCategory = aCategory.activate();

        Assertions.assertDoesNotThrow(()-> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreateAt());
        Assertions.assertTrue(actualCategory.getUpdateAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeleteAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdated(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais top";
        final var expectedIsActive = true;

        final var aCategory =
                Category.newCategory("Film", "A categoria", expectedIsActive);

        Assertions.assertDoesNotThrow(()-> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getCreateAt();
        final var updatedAt = aCategory.getUpdateAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(()-> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreateAt());
        Assertions.assertTrue(actualCategory.getUpdateAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeleteAt());    }

    @Test
    public void givenAValidCategory_whenCallUpdateToInactive_thenReturnCategoryUpdated(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais top";
        final var expectedIsActive = false;

        final var aCategory =
                Category.newCategory("Film", "A categoria", true);

        Assertions.assertDoesNotThrow(()-> aCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeleteAt());

        final var createdAt = aCategory.getCreateAt();
        final var updatedAt = aCategory.getUpdateAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(()-> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertFalse(aCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreateAt());
        Assertions.assertTrue(actualCategory.getUpdateAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualCategory.getDeleteAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdateWithInvalidParams_thenReturnCategoryUpdated(){
        final String expectedName = null;
        final var expectedDescription = "A categoria mais top";
        final var expectedIsActive = true;

        final var aCategory =
                Category.newCategory("Film", "A categoria", expectedIsActive);

        Assertions.assertDoesNotThrow(()-> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getCreateAt();
        final var updatedAt = aCategory.getUpdateAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreateAt());
        Assertions.assertTrue(actualCategory.getUpdateAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeleteAt());
    }
}
