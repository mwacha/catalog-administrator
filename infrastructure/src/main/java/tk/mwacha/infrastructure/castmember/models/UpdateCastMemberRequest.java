package tk.mwacha.infrastructure.castmember.models;


import tk.mwacha.domain.castmember.CastMemberType;

public record UpdateCastMemberRequest(String name, CastMemberType type) {
}