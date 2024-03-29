package tk.mwacha.application.castmember.create;

import tk.mwacha.domain.castmember.CastMember;
import tk.mwacha.domain.castmember.CastMemberGateway;
import tk.mwacha.domain.exceptions.NotificationException;
import tk.mwacha.domain.validation.handler.Notification;

import java.util.Objects;


public non-sealed class DefaultCreateCastMemberUseCase extends CreateCastMemberUseCase {

    private final CastMemberGateway castMemberGateway;

    public DefaultCreateCastMemberUseCase(final CastMemberGateway gateway) {
        this.castMemberGateway = Objects.requireNonNull(gateway);
    }

    @Override
    public CreateCastMemberOutput execute(final CreateCastMemberCommand command) {
        final var name = command.name();
        final var type = command.type();


        final var notification = Notification.create();
        var castMember = notification.validate(() -> CastMember.newMember(name, type));

        if(notification.hasError())
            notify(notification);

        return CreateCastMemberOutput.from(this.castMemberGateway.create(castMember));
    }

    private void notify(Notification notification) {
        throw new NotificationException("Could not create Aggergate CastMember", notification);
    }

}
