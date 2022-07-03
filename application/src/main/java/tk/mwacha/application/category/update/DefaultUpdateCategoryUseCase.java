package tk.mwacha.application.category.update;

import io.vavr.control.Either;
import tk.mwacha.application.category.create.CreateCategoryOutput;
import tk.mwacha.domain.category.Category;
import tk.mwacha.domain.category.CategoryGateway;
import tk.mwacha.domain.category.CategoryID;
import tk.mwacha.domain.exceptions.DomainException;
import tk.mwacha.domain.exceptions.NotFoundException;
import tk.mwacha.domain.validation.Error;
import tk.mwacha.domain.validation.handler.Notification;

import java.util.Objects;
import java.util.function.Supplier;

import static io.vavr.API.Left;
import static io.vavr.API.Try;


public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway gateway) {
        this.categoryGateway = Objects.requireNonNull(gateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryCommand command) {
        final var id = CategoryID.from(command.id());
        final var name = command.name();
        final var description = command.description();
        final var isActive = command.isActive();

       final var category = this.categoryGateway.findById(id).orElseThrow(
                notFound(id));

       final var notification = Notification.create();

       category.update(name, description, isActive).validate(notification);

       return notification.hasError() ? Left(notification) : update(category);

    }

    private Either<Notification, UpdateCategoryOutput> update(final Category category) {
        return Try(() -> this.categoryGateway.update(category))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);
    }

    private Supplier<DomainException> notFound(final CategoryID id) {
        return () -> NotFoundException.with(Category.class, id);
    }

    private Either<Notification, CreateCategoryOutput> create(final Category category) {
        return Try(() -> this.categoryGateway.create(category))
                .toEither()
                .bimap(Notification::create, CreateCategoryOutput::from);
    }
}
