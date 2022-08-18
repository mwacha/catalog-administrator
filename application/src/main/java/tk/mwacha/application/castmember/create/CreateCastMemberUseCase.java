package tk.mwacha.application.castmember.create;

import tk.mwacha.application.UseCase;

public sealed abstract class CreateCastMemberUseCase
        extends UseCase<CreateCastMemberCommand, CreateCastMemberOutput>  permits DefaultCreateCastMemberUseCase {
}
