package tk.mwacha.application;

import com.github.javafaker.Faker;
import tk.mwacha.domain.castmember.CastMemberType;

public final class Fixture {
    private static Faker FAKER = new Faker();

    public static String name() {
        return FAKER.name().fullName();
    }

    public static final class CastMember {
        public static CastMemberType type() {
            return FAKER.options().option(CastMemberType.ACTOR, CastMemberType.DIRECTOR);
        }
    }
}
