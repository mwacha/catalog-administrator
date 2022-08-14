package tk.mwacha.application.castmember.create;

import tk.mwacha.domain.castmember.CastMember;

public record CreateCastMemberOutput(String id) {

    public static CreateCastMemberOutput from(final String anId) {
        return new CreateCastMemberOutput(anId);
    }

    public static CreateCastMemberOutput from(final CastMember castMember) {
        return new CreateCastMemberOutput(castMember.getId().getValue());
    }
}
