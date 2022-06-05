package tk.mwacha.application.category.create;

import io.vavr.control.Either;
import tk.mwacha.application.UseCase;
import tk.mwacha.domain.validation.handler.Notification;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {
}
