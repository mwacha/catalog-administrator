package tk.mwacha.domain.castmember;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tk.mwacha.domain.exceptions.NotificationException;

class CastMemberTest {

    @Test
    void givenAValidParams_whenCallsNewMember_thenInstantiateACastMember() {
        final var expectedName = "Vin Diesel";
        final var expectedType = CastMemberType.ACTOR;

        final var actualMember =  CastMember.newMember(expectedName, expectedType);

        Assertions.assertNotNull(actualMember);
        Assertions.assertNotNull(actualMember.getId());
        Assertions.assertEquals(expectedName, actualMember.getName());
        Assertions.assertEquals(expectedType, actualMember.getType());
        Assertions.assertNotNull(actualMember.getCreatedAt());
        Assertions.assertNotNull(actualMember.getUpdatedAt());
        Assertions.assertEquals(actualMember.getUpdatedAt(), actualMember.getUpdatedAt());
    }

    @Test
    void givenInvalidNullName_whenCallNewMemberAndValidate_shouldReceiveAError() {
        final String expectedName = null;
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            CastMember.newMember(expectedName, expectedType);
        });

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    void givenInvalidEmptyName_whenCallNewMemberAndValidate_shouldReceiveAError() {
        final String expectedName = "";
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            CastMember.newMember(expectedName, expectedType);
        });

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    void givenInvalidNameWithLengthGreaterThan255_whenCallNewMemberAndValidate_shouldReceiveAError() {
        final var expectedName = """
                Gostaria de enfatizar que o consenso sobre a necessidade de qualificação auxilia a preparação e a
                composição das posturas dos órgãos dirigentes com relação às suas atribuições.
                Do mesmo modo, a estrutura atual da organização apresenta tendências no sentido de aprovar a
                manutenção das novas proposições.
                """;
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            CastMember.newMember(expectedName, expectedType);
        });

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    void givenInvalidNullType_whenCallNewMemberAndValidate_shouldReceiveAError() {
        final var expectedName = "Vin Diesel";
        final CastMemberType expectedType = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'type' should not be null";

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            CastMember.newMember(expectedName, expectedType);
        });

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    void givenValidCastMember_whenCallUpdate_shouldReceivedUpdated() {
        final var expectedName = "Vin Diesel";
        final var expectedType = CastMemberType.ACTOR;

        final var actualMember =  CastMember.newMember("vin", CastMemberType.DIRECTOR);


        Assertions.assertNotNull(actualMember);
        Assertions.assertNotNull(actualMember.getId());

        final var createdAt = actualMember.getCreatedAt();
        final var updatedAt = actualMember.getUpdatedAt();

        actualMember.update(expectedName, expectedType);

        Assertions.assertEquals(expectedName, actualMember.getName());
        Assertions.assertEquals(expectedType, actualMember.getType());
        Assertions.assertEquals(createdAt, actualMember.getCreatedAt());
        Assertions.assertTrue(updatedAt.isBefore(actualMember.getUpdatedAt()));
    }

    @Test
    void givenValidCastMember_whenCallUpdateWithInvalidNullName_shouldReceivedNotification() {
        final String expectedName = null;
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var actualMember =  CastMember.newMember("vin", CastMemberType.DIRECTOR);


        Assertions.assertNotNull(actualMember);
        Assertions.assertNotNull(actualMember.getId());


        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            actualMember.update(expectedName, expectedType);
        });

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    void givenValidCastMember_whenCallUpdateWithInvalidEmptyName_shouldReceivedNotification() {
        final String expectedName = "";
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var actualMember =  CastMember.newMember("vin", CastMemberType.DIRECTOR);


        Assertions.assertNotNull(actualMember);
        Assertions.assertNotNull(actualMember.getId());


        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            actualMember.update(expectedName, expectedType);
        });

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    void givenValidCastMember_whenCallUpdateWithInvalidNullType_shouldReceivedNotification() {
        final var expectedName = "Vin Diesel";
        final CastMemberType expectedType = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'type' should not be null";

        final var actualMember =  CastMember.newMember("vin", CastMemberType.DIRECTOR);


        Assertions.assertNotNull(actualMember);
        Assertions.assertNotNull(actualMember.getId());


        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            actualMember.update(expectedName, expectedType);
        });

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    void givenInvalidNameWithLengthGreaterThan255_whenCallUpdateMemberAndValidate_shouldReceiveAError() {
        final var expectedName = """
                Gostaria de enfatizar que o consenso sobre a necessidade de qualificação auxilia a preparação e a
                composição das posturas dos órgãos dirigentes com relação às suas atribuições.
                Do mesmo modo, a estrutura atual da organização apresenta tendências no sentido de aprovar a
                manutenção das novas proposições.
                """;
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";

        final var actualMember =  CastMember.newMember("vin", CastMemberType.DIRECTOR);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            actualMember.update(expectedName, expectedType);
        });

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }
}
