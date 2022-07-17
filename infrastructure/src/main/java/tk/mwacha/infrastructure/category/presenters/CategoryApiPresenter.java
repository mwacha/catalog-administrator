package tk.mwacha.infrastructure.category.presenters;

import tk.mwacha.application.category.retrieve.get.CategoryOutput;
import tk.mwacha.application.category.retrieve.list.CategoryListOutput;
import tk.mwacha.infrastructure.category.models.CategoryResponse;
import tk.mwacha.infrastructure.category.models.CategoryListResponse;

public interface CategoryApiPresenter {

    static CategoryResponse present(final CategoryOutput output) {
        return new CategoryResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
                );
    }

    static CategoryListResponse present(final CategoryListOutput output) {
        return new CategoryListResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}
