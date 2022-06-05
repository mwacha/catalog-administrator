package tk.mwacha.application.category.retrieve.get;

import tk.mwacha.domain.category.CategoryGateway;
import tk.mwacha.domain.category.CategoryID;
import tk.mwacha.domain.exceptions.DomainException;
import tk.mwacha.domain.validation.Error;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

    private final CategoryGateway categoryGateway;


    public DefaultGetCategoryByIdUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CategoryOutput execute(final UUID id) {
        final var categoryId = CategoryID.from(id);
        return this.categoryGateway.findById(categoryId)
                .map(CategoryOutput::from)
                .orElseThrow(notFound(categoryId));
    }

    private Supplier<DomainException> notFound(final CategoryID id) {
        return () -> DomainException.with(new Error("Category with ID $s was not found".formatted(id.getValue())));
    }
}
