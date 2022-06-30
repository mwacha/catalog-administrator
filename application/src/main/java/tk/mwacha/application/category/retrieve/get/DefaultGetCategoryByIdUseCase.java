package tk.mwacha.application.category.retrieve.get;

import tk.mwacha.domain.category.Category;
import tk.mwacha.domain.category.CategoryGateway;
import tk.mwacha.domain.category.CategoryID;
import tk.mwacha.domain.exceptions.DomainException;
import tk.mwacha.domain.exceptions.NotFoundException;
import tk.mwacha.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

    private final CategoryGateway categoryGateway;


    public DefaultGetCategoryByIdUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CategoryOutput execute(final String id) {
        final var categoryId = CategoryID.from(id);
        return this.categoryGateway.findById(categoryId)
                .map(CategoryOutput::from)
                .orElseThrow(notFound(categoryId));
    }

    private Supplier<NotFoundException> notFound(final CategoryID id) {
        return () -> NotFoundException.with(Category.class, id);
    }
}
