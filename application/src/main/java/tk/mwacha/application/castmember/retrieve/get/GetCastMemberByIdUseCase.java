package tk.mwacha.application.castmember.retrieve.get;

import tk.mwacha.application.UseCase;

public sealed abstract class GetCastMemberByIdUseCase
        extends UseCase<String, CastMemberOutput>
        permits DefaultGetCastMemberByIdUseCase {
}