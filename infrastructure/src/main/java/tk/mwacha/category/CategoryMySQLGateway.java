package tk.mwacha.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tk.mwacha.category.persistence.CategoryJpaEntity;
import tk.mwacha.category.persistence.CategoryRepository;
import tk.mwacha.domain.category.Category;
import tk.mwacha.domain.category.CategoryGateway;
import tk.mwacha.domain.category.CategoryID;
import tk.mwacha.domain.category.CategorySearchQuery;
import tk.mwacha.domain.pagination.Pagination;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryMySQLGateway implements CategoryGateway {

    private final CategoryRepository repository;

    @Override
    public Category create(Category aCategory) {
        return this.repository.save(CategoryJpaEntity.from(aCategory)).toAggregate();
    }

    @Override
    public void deleteById(CategoryID anId) {

    }

    @Override
    public Optional<Category> findById(CategoryID anId) {
        return Optional.empty();
    }

    @Override
    public Category update(Category aCategory) {
        return null;
    }

    @Override
    public Pagination<Category> findAll(CategorySearchQuery aQuery) {
        return null;
    }
}
