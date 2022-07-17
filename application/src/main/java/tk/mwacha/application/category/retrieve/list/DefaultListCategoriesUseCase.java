package tk.mwacha.application.category.retrieve.list;

import tk.mwacha.domain.category.CategoryGateway;
import tk.mwacha.domain.pagination.SearchQuery;
import tk.mwacha.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultListCategoriesUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }


    @Override
    public Pagination<CategoryListOutput> execute(final SearchQuery categorySearchQuery) {
        return this.categoryGateway.findAll(categorySearchQuery).map(CategoryListOutput::from);
    }
}
