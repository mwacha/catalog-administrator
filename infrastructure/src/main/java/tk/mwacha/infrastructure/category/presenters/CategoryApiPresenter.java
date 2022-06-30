package tk.mwacha.infrastructure.category.presenters;

import tk.mwacha.application.category.retrieve.get.CategoryOutput;
import tk.mwacha.infrastructure.category.models.CategoryApiOutput;

public interface CategoryApiPresenter {

    static CategoryApiOutput present(final CategoryOutput output) {
        return new CategoryApiOutput(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
                );
    }
}
