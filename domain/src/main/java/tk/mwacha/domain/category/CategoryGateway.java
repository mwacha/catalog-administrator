package tk.mwacha.domain.category;

import tk.mwacha.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {

    Category create(Category category);

    void deleteById(CategoryID id);

    Optional<Category> findById(Category id);

    Category update(Category category);

    Pagination<Category> findAll(CategorySearchQuery query);

}
