package tk.mwacha.application.category.retrieve.list;

import tk.mwacha.application.UseCase;
import tk.mwacha.domain.category.CategorySearchQuery;
import tk.mwacha.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}
