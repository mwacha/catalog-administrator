package tk.mwacha.application.castmember.create;

import tk.mwacha.domain.castmember.CastMember;
import tk.mwacha.domain.castmember.CastMemberID;

public record CreateCastMemberOutput(String id) {

    public static CreateCastMemberOutput from(final String anId) {
        return from(anId);
    }

    public static CreateCastMemberOutput from(final CastMember castMember) {
        return new CreateCastMemberOutput(castMember.getId().getValue());
    }

    public static CreateCastMemberOutput from(final CastMemberID id) {
        return new CreateCastMemberOutput(id.getValue());
    }
}
