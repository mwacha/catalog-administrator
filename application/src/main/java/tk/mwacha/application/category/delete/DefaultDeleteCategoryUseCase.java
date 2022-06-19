package tk.mwacha.application.category.delete;

import tk.mwacha.domain.category.CategoryGateway;
import tk.mwacha.domain.category.CategoryID;

import java.util.Objects;
import java.util.UUID;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {

    private final CategoryGateway categoryGateway;


    public DefaultDeleteCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public void execute(final String id) {
        this.categoryGateway.deleteById(CategoryID.from(id));
    }
}
