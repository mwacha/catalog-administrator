package tk.mwacha.domain.castmember;

import lombok.Getter;
import tk.mwacha.domain.AggregateRoot;
import tk.mwacha.domain.exceptions.NotificationException;
import tk.mwacha.domain.utils.InstantUtils;
import tk.mwacha.domain.validation.ValidationHandler;
import tk.mwacha.domain.validation.handler.Notification;

import java.time.Instant;

@Getter
public class CastMember extends AggregateRoot<CastMemberID>  {
    private String name;
    private CastMemberType type;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    protected CastMember(final CastMemberID castMemberID,
                         final String name,
                         final CastMemberType type,
                         final Instant createdAt,
                         final Instant updatedA) {
        super(castMemberID);
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        selfValidate();

    }

    public static CastMember with(
            final CastMemberID anId,
            final String aName,
            final CastMemberType type,
            final Instant aCreatedAt,
            final Instant aUpdatedAt
    ) {
        return new CastMember(anId, aName, type, aCreatedAt, aUpdatedAt);
    }

    public static CastMember with(final CastMember castMember) {
        return new CastMember(
                castMember.id,
                castMember.name,
                castMember.type,
                castMember.createdAt,
                castMember.updatedAt
        );
    }
    public static CastMember newMember(final String aName, final CastMemberType type) {
        return new CastMember(CastMemberID.unique(), aName, type, InstantUtils.now(),  InstantUtils.now());
    }

    public CastMember update(final String name, final CastMemberType type) {
        this.name = name;
        this.type = type;
        this.updatedAt = InstantUtils.now();

        selfValidate();

        return this;
    }

    @Override
    public void validate(ValidationHandler handler) {

            new CastMemberValidator(this, handler).validate();
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Failed to create a Aggregate CastMember", notification);
        }
    }
}
