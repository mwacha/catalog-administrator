package tk.mwacha.infrastructure.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tk.mwacha.infrastructure.category.persistence.CategoryJpaEntity;
import tk.mwacha.infrastructure.category.persistence.CategoryRepository;
import tk.mwacha.domain.category.Category;
import tk.mwacha.domain.category.CategoryGateway;
import tk.mwacha.domain.category.CategoryID;
import tk.mwacha.domain.pagination.SearchQuery;
import tk.mwacha.domain.pagination.Pagination;
import tk.mwacha.infrastructure.utils.SpecificationsUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryMySQLGateway implements CategoryGateway {

    private final CategoryRepository repository;

    @Override
    public Category create(Category aCategory) {
        return this.save(aCategory);
    }

    @Override
    public void deleteById(CategoryID anId) {

        if(this.repository.existsById(anId.getValue())) {
            this.repository.deleteById(anId.getValue());
        }
    }

    @Override
    public Optional<Category> findById(final CategoryID anId) {
        return this.repository.findById(anId.getValue()).map(CategoryJpaEntity::toAggregate);
    }

    @Override
    public Category update(final Category aCategory) {
        return this.save(aCategory);
    }

    @Override
    public Pagination<Category> findAll(final SearchQuery aQuery) {

        final var page = PageRequest.of(aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort()));

        final var specifications = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(str -> {
                    final Specification<CategoryJpaEntity> nameLike =  SpecificationsUtils.<CategoryJpaEntity>like("name", str);
                    final Specification<CategoryJpaEntity> descriptionLike =  SpecificationsUtils.<CategoryJpaEntity>like("description", str);
                    return nameLike.or(descriptionLike);
                        }
                ).orElse(null);

        final var pageResult =  this.repository.findAll(Specification.where(specifications),page);

        return new Pagination<>(pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CategoryJpaEntity::toAggregate).toList());
    }

    @Override
    public List<CategoryID> existsByIds(final Iterable<CategoryID> ids) {
        return Collections.emptyList();
    }

    private Category save(final Category category) {
        return this.repository.save(CategoryJpaEntity.from(category)).toAggregate();
    }
}
