package tk.mwacha.application.category.update;

import io.vavr.control.Either;
import tk.mwacha.application.UseCase;
import tk.mwacha.domain.validation.handler.Notification;

public abstract class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {
}
