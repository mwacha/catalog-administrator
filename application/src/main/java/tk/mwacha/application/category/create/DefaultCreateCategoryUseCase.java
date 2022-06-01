package tk.mwacha.application.category.create;

import tk.mwacha.domain.category.Category;
import tk.mwacha.domain.category.CategoryGateway;
import tk.mwacha.domain.validation.handler.Notification;
import tk.mwacha.domain.validation.handler.ThrowsValidationHandler;

import java.util.Objects;


public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway gateway) {
        this.categoryGateway = Objects.requireNonNull(gateway);
    }

    @Override
    public CreateCategoryOutput execute(final CreateCategoryCommand command) {
        final var name = command.name();
        final var description = command.description();
        final var isActive = command.isActive();

        final var notification = Notification.create();
        final var category = Category.newCategory(name, description, isActive);
        category.validate(notification);

        if(notification.hasError()) {

        }
        return CreateCategoryOutput.from(this.categoryGateway.create(category));
    }
}
