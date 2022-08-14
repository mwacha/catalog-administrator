package tk.mwacha.application.castmember.delete;


import tk.mwacha.application.UnitUseCase;

public sealed abstract class DeleteCastMemberUseCase
        extends UnitUseCase<String>
        permits DefaultDeleteCastMemberUseCase {
}