package tk.mwacha.application.castmember.create;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import tk.mwacha.application.Fixture;
import tk.mwacha.application.UseCaseTest;
import tk.mwacha.domain.castmember.CastMemberGateway;
import tk.mwacha.domain.castmember.CastMemberType;
import tk.mwacha.domain.exceptions.NotificationException;

import java.util.List;
import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class CreateCastMemberUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateCastMemberUseCase useCase;

    @Mock
    private CastMemberGateway CastMemberGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(CastMemberGateway);
    }

    @Test
    void givenAValidCommand_whenCallCreateCastMember_shouldReturnCastMemberIt() {
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMembers.type();


        final var aCommand = CreateCastMemberCommand.with(expectedName, expectedType);

        when(CastMemberGateway.create(Mockito.any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(CastMemberGateway, times(1))
                .create(argThat(aCastMember -> Objects.equals(expectedName, aCastMember.getName()) &&
                        Objects.equals(expectedType, aCastMember.getType()) &&
                        Objects.nonNull(aCastMember.getId()) &&
                        Objects.nonNull(aCastMember.getCreatedAt()) &&
                        Objects.nonNull(aCastMember.getUpdatedAt())
                ));

    }

    @Test
    void givenInValidName_whenCallCreateCastMember_thenShouldThrowsNotificationException() {
        final String expectedName = null;
        final var expectedType = Fixture.CastMembers.type();
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCastMemberCommand.with(expectedName, expectedType);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(CastMemberGateway, times(0)).create(any());
    }

    @Test
    void givenInValidType_whenCallCreateCastMember_thenShouldThrowsNotificationException() {
        final var expectedName = Fixture.name();
        final CastMemberType expectedType = null;
        final var expectedErrorMessage = "'type' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCastMemberCommand.with(expectedName, expectedType);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(CastMemberGateway, times(0)).create(any());
    }

}
