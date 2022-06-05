package tk.mwacha.application.category.create;

import io.vavr.control.Either;
import tk.mwacha.domain.category.Category;
import tk.mwacha.domain.category.CategoryGateway;
import tk.mwacha.domain.validation.handler.Notification;

import java.util.Objects;

import static io.vavr.API.*;


public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway gateway) {
        this.categoryGateway = Objects.requireNonNull(gateway);
    }

    @Override
    public Either<Notification, CreateCategoryOutput> execute(final CreateCategoryCommand command) {
        final var name = command.name();
        final var description = command.description();
        final var isActive = command.isActive();

        final var notification = Notification.create();
        final var category = Category.newCategory(name, description, isActive);
        category.validate(notification);

        if (notification.hasError()) {

        }
        return notification.hasError() ? Left(notification) : create(category);
    }

    private Either<Notification, CreateCategoryOutput> create(final Category category) {
        return Try(() -> this.categoryGateway.create(category))
                .toEither()
                .bimap(Notification::create, CreateCategoryOutput::from);
    }
}
