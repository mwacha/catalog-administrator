package tk.mwacha.application.castmember.update;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import tk.mwacha.Fixture;
import tk.mwacha.IntegrationTest;
import tk.mwacha.domain.castmember.CastMember;
import tk.mwacha.domain.castmember.CastMemberGateway;
import tk.mwacha.domain.castmember.CastMemberID;
import tk.mwacha.domain.castmember.CastMemberType;
import tk.mwacha.domain.exceptions.NotFoundException;
import tk.mwacha.domain.exceptions.NotificationException;
import tk.mwacha.infrastructure.castmember.persistence.CastMemberJpaEntity;
import tk.mwacha.infrastructure.castmember.persistence.CastMemberRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@IntegrationTest
public class UpdateCastMemberUseCaseIT {

    @Autowired
    private UpdateCastMemberUseCase useCase;

    @Autowired
    private CastMemberRepository castMemberRepository;

    @SpyBean
    private CastMemberGateway castMemberGateway;

    @Test
    public void givenAValidCommand_whenCallsUpdateCastMember_shouldReturnItsIdentifier() {
        // given
        final var aMember = CastMember.newMember("vin diesel", CastMemberType.DIRECTOR);

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        final var expectedId = aMember.getId();
        final var expectedName = Fixture.name();
        final var expectedType = CastMemberType.ACTOR;

        final var aCommand = UpdateCastMemberCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedType
        );

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        final var actualPersistedMember =
                this.castMemberRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedName, actualPersistedMember.getName());
        Assertions.assertEquals(expectedType, actualPersistedMember.getType());
        Assertions.assertEquals(aMember.getCreatedAt(), actualPersistedMember.getCreatedAt());
        Assertions.assertTrue(aMember.getUpdatedAt().isBefore(actualPersistedMember.getUpdatedAt()));

        verify(castMemberGateway).findById(any());
        verify(castMemberGateway).update(any());
    }

    @Test
    public void givenAnInvalidName_whenCallsUpdateCastMember_shouldThrowsNotificationException() {
        // given
        final var aMember = CastMember.newMember("vin diesel", CastMemberType.DIRECTOR);

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        final var expectedId = aMember.getId();
        final String expectedName = null;
        final var expectedType = CastMemberType.ACTOR;

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var aCommand = UpdateCastMemberCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedType
        );

        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        // then
        Assertions.assertNotNull(actualException);

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(castMemberGateway).findById(any());
        verify(castMemberGateway, times(0)).update(any());
    }

    @Test
    public void givenAnInvalidType_whenCallsUpdateCastMember_shouldThrowsNotificationException() {
        // given
        final var aMember = CastMember.newMember("vin diesel", CastMemberType.DIRECTOR);

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        final var expectedId = aMember.getId();
        final var expectedName = Fixture.name();
        final CastMemberType expectedType = null;

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'type' should not be null";

        final var aCommand = UpdateCastMemberCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedType
        );

        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        // then
        Assertions.assertNotNull(actualException);

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(castMemberGateway).findById(any());
        verify(castMemberGateway, times(0)).update(any());
    }

    @Test
    public void givenAnInvalidId_whenCallsUpdateCastMember_shouldThrowsNotFoundException() {
        // given
        final var expectedId = CastMemberID.from("123");
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMember.type();

        final var expectedErrorMessage = "CastMember with ID 123 was not found";

        final var aCommand = UpdateCastMemberCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedType
        );

        // when
        final var actualException = Assertions.assertThrows(NotFoundException.class, () -> {
            useCase.execute(aCommand);
        });

        // then
        Assertions.assertNotNull(actualException);

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(castMemberGateway).findById(any());
        verify(castMemberGateway, times(0)).update(any());
    }
}