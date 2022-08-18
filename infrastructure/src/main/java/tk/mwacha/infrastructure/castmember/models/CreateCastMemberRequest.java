package tk.mwacha.infrastructure.castmember.models;

import tk.mwacha.domain.castmember.CastMemberType;

public record CreateCastMemberRequest(String name, CastMemberType castMemberType) {

}
