package tk.mwacha.application.castmember.create;

import io.vavr.control.Either;
import tk.mwacha.application.UseCase;
import tk.mwacha.domain.validation.handler.Notification;

public sealed abstract class CreateCastMemberUseCase
        extends UseCase<CreateCastMemberCommand, CreateCastMemberOutput>  permits DefaultCreateCastMemberUseCase {
}
