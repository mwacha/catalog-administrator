package tk.mwacha.application.category.update;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tk.mwacha.domain.category.Category;
import tk.mwacha.domain.category.CategoryGateway;
import tk.mwacha.domain.category.CategoryID;
import tk.mwacha.domain.exceptions.DomainException;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    void givenAValidCommand_whenCallUpdateCategory_shouldReturnCategoryId() {
        final var aCategory = Category.newCategory("Film", null, true);
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();

        final var command = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive);

        when(categoryGateway.findById(expectedId))
                .thenReturn(Optional.of(Category.with(aCategory)));

        when(categoryGateway.update(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(command).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));

        Mockito.verify(categoryGateway, times(1)).update(
                argThat(updatedCategory ->
                        Objects.equals(expectedName, updatedCategory.getName()) &&
                                Objects.equals(expectedDescription, updatedCategory.getDescription()) &&
                                Objects.equals(expectedIsActive, updatedCategory.isActive()) &&
                                Objects.equals(expectedId, updatedCategory.getId()) &&
                                Objects.equals(aCategory.getCreatedAt(), updatedCategory.getCreatedAt()) &&
                                aCategory.getUpdatedAt().isBefore(updatedCategory.getUpdatedAt()) &&
                                Objects.nonNull(updatedCategory.getUpdatedAt()) &&
                                Objects.isNull(updatedCategory.getDeletedAt())
                ));
    }

    @Test
    void givenInValidName_whenCallUpdateCategory_thenShouldReturnDomainException() {
        final var aCategory = Category.newCategory("Film", null, true);

        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();

        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;


        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(expectedId))
                .thenReturn(Optional.of(Category.with(aCategory)));


        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        verify(categoryGateway, times(0)).update(any());
    }

    @Test
    void givenAValidCInactivateCommand_whenCallUpdateCategory_shouldReturnInactivateCategoryId() {
        final var aCategory = Category.newCategory("Film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedId = aCategory.getId();

        final var command = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive);

        when(categoryGateway.findById(expectedId))
                .thenReturn(Optional.of(Category.with(aCategory)));

        when(categoryGateway.update(any())).thenAnswer(returnsFirstArg());

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var actualOutput = useCase.execute(command).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));

        Mockito.verify(categoryGateway, times(1)).update(
                argThat(updatedCategory ->
                        Objects.equals(expectedName, updatedCategory.getName()) &&
                                Objects.equals(expectedDescription, updatedCategory.getDescription()) &&
                                Objects.equals(expectedIsActive, updatedCategory.isActive()) &&
                                Objects.equals(expectedId, updatedCategory.getId()) &&
                                Objects.equals(aCategory.getCreatedAt(), updatedCategory.getCreatedAt()) &&
                                aCategory.getUpdatedAt().isBefore(updatedCategory.getUpdatedAt()) &&
                                Objects.nonNull(updatedCategory.getUpdatedAt()) &&
                                Objects.nonNull(updatedCategory.getDeletedAt())
                ));
    }

    @Test
    void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
        final var aCategory = Category.newCategory("Film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();

        final var expectedErrorMessage = "Gateway Error";
        final var expectedErrorCount = 1;

        final var command = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive);

        when(categoryGateway.findById(expectedId))
                .thenReturn(Optional.of(Category.with(aCategory)));

        when(categoryGateway.update(Mockito.any())).thenThrow(new IllegalStateException("Gateway Error"));

        final var notification = useCase.execute(command).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, times(1)).update(
                argThat(updatedCategory ->
                        Objects.equals(expectedName, updatedCategory.getName()) &&
                                Objects.equals(expectedDescription, updatedCategory.getDescription()) &&
                                Objects.equals(expectedIsActive, updatedCategory.isActive()) &&
                                Objects.equals(expectedId, updatedCategory.getId()) &&
                                Objects.equals(aCategory.getCreatedAt(), updatedCategory.getCreatedAt()) &&
                                aCategory.getUpdatedAt().isBefore(updatedCategory.getUpdatedAt()) &&
                                Objects.nonNull(updatedCategory.getUpdatedAt()) &&
                                Objects.isNull(updatedCategory.getDeletedAt())
                ));
    }

    @Test
    void givenACommandWithInvalidID_whenCallUpdateCategory_shouldReturnNotFoundException() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedId = UUID.randomUUID();
        final var expectedErrorMessage = "Category with ID $s was not found".formatted(expectedId);
        final var expectedErrorCount = 1;

        final var command = UpdateCategoryCommand.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsActive);

        when(categoryGateway.findById(eq(CategoryID.from(expectedId))))
                .thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(command));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(categoryGateway, times(1)).findById(eq(CategoryID.from(expectedId)));

        Mockito.verify(categoryGateway, times(0)).update(any());
    }
}
