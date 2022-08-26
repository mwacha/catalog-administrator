package tk.mwacha.infrastructure.castmember.presenter;

import tk.mwacha.application.castmember.retrieve.get.CastMemberOutput;
import tk.mwacha.application.castmember.retrieve.list.CastMemberListOutput;
import tk.mwacha.infrastructure.castmember.models.CastMemberListResponse;
import tk.mwacha.infrastructure.castmember.models.CastMemberResponse;

public interface CastMemberPresenter {

    static CastMemberResponse present(final CastMemberOutput aMember) {
        return new CastMemberResponse(
                aMember.id(),
                aMember.name(),
                aMember.type().name(),
                aMember.createdAt().toString(),
                aMember.updatedAt().toString()
        );
    }

    static CastMemberListResponse present(final CastMemberListOutput aMember) {
        return new CastMemberListResponse(
                aMember.id(),
                aMember.name(),
                aMember.type().name(),
                aMember.createdAt().toString()
        );
    }
}