package tk.mwacha.application.category.retrieve.list;

import tk.mwacha.application.UseCase;
import tk.mwacha.domain.pagination.SearchQuery;
import tk.mwacha.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<SearchQuery, Pagination<CategoryListOutput>> {
}
