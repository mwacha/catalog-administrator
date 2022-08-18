package tk.mwacha.infrastructure.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tk.mwacha.ControllerTest;
import tk.mwacha.Fixture;
import tk.mwacha.application.castmember.create.CreateCastMemberOutput;
import tk.mwacha.application.castmember.create.CreateCastMemberUseCase;
import tk.mwacha.application.castmember.create.DefaultCreateCastMemberUseCase;
import tk.mwacha.application.castmember.delete.DefaultDeleteCastMemberUseCase;
import tk.mwacha.application.castmember.delete.DeleteCastMemberUseCase;
import tk.mwacha.application.castmember.retrieve.get.DefaultGetCastMemberByIdUseCase;
import tk.mwacha.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import tk.mwacha.application.castmember.retrieve.list.DefaultListCastMembersUseCase;
import tk.mwacha.application.castmember.retrieve.list.ListCastMembersUseCase;
import tk.mwacha.application.castmember.update.DefaultUpdateCastMemberUseCase;
import tk.mwacha.application.castmember.update.UpdateCastMemberUseCase;
import tk.mwacha.domain.castmember.CastMemberID;
import tk.mwacha.domain.exceptions.NotificationException;
import tk.mwacha.domain.validation.Error;
import tk.mwacha.infrastructure.castmember.models.CreateCastMemberRequest;

import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = CastMemberAPI.class)
public class CastMemberAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private DefaultCreateCastMemberUseCase createCastMemberUseCase;

    @MockBean
    private DefaultGetCastMemberByIdUseCase getCastMemberByIdUseCase;

    @MockBean
    private DefaultUpdateCastMemberUseCase updateCastMemberUseCase;

    @MockBean
    private DefaultDeleteCastMemberUseCase deleteCastMemberUseCase;

    @MockBean
    private DefaultListCastMembersUseCase listCastMembersUseCase;

    @Test
    void givenAValidCommand_whenCallCreateCastMember_shouldReturnCastMemberId() throws Exception {
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMember.type();
        final var expectedId = CastMemberID.from("1234556");

        final var command =
                new CreateCastMemberRequest(expectedName, expectedType);

        when(createCastMemberUseCase.execute(any())).thenReturn(CreateCastMemberOutput.from(expectedId));

        final var request = post("/cast_members")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(command));

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/cast_members/" + expectedId.getValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(expectedId.getValue())));

        verify(createCastMemberUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedType, cmd.type())
        ));
    }


    @Test
    void givenInValidName_whenCallCreateCastMember_thenShouldReturnNotification() throws Exception {
        final String expectedName = null;
        final var expectedType = Fixture.CastMember.type();
        final var expectedMessage = "'name' should not be null";

        final var command =
                new CreateCastMemberRequest(expectedName, expectedType);

        when(createCastMemberUseCase.execute(any())).thenThrow(NotificationException.with(new Error(expectedMessage)));

        final var request = post("/cast_members")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(command));

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedMessage)));

        verify(createCastMemberUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedType, cmd.type())
        ));
    }

//    @Test
//    void givenInValidCommand_whenCallCreateCastMember_thenShouldReturnDomainException() throws Exception {
//        final String expectedName = null;
//        final var expectedDescription = "A categoria mais assistida";
//        final var expectedIsActive = true;
//        final var expectedMessage = "'name' should not be null";
//
//        final var input =
//                new CreateCastMemberRequest(expectedName, expectedDescription, expectedIsActive);
//
//        when(createCastMemberUseCase.execute(any())).thenThrow(DomainException.with(new Error(expectedMessage)));
//
//        final var request = post("/CastMembers")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(this.mapper.writeValueAsString(input));
//
//        this.mvc.perform(request)
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity())
//                .andExpect(header().string("Location", nullValue()))
//                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.message", equalTo(expectedMessage)))
//                .andExpect(jsonPath("$.errors", hasSize(1)))
//                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedMessage)));
//
//        verify(createCastMemberUseCase, times(1)).execute(argThat(cmd ->
//                Objects.equals(expectedName, cmd.name())
//                        && Objects.equals(expectedDescription, cmd.description())
//                        && Objects.equals(expectedIsActive, cmd.isActive())
//        ));
//    }
//
//
//    @Test
//    void givenAValidId_whenCallsGetCastMember_shouldReturnCastMember() throws Exception {
//        final var expectedName = "Filmes";
//        final var expectedDescription = "A categoria mais assistida";
//        final var expectedIsActive = true;
//
//        final var aCastMember =
//                CastMember.newCastMember(expectedName, expectedDescription, expectedIsActive);
//
//        final var expectedId = aCastMember.getId();
//
//        when(getCastMemberByIdUseCase.execute(any())).thenReturn(CastMemberOutput.from(aCastMember));
//
//        final var request = get("/CastMembers/{id}", expectedId.getValue())
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON);
//
//
//        this.mvc.perform(request)
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", equalTo(expectedId.getValue())))
//                .andExpect(jsonPath("$.name", equalTo(expectedName)))
//                .andExpect(jsonPath("$.description", equalTo(expectedDescription)))
//                .andExpect(jsonPath("$.is_active", equalTo(expectedIsActive)))
//                .andExpect(jsonPath("$.created_at", equalTo(aCastMember.getCreatedAt().toString())))
//                .andExpect(jsonPath("$.updated_at", equalTo(aCastMember.getUpdatedAt().toString())))
//                .andExpect(jsonPath("$.deleted_at", equalTo(aCastMember.getDeletedAt())));
//
//
//        verify(getCastMemberByIdUseCase, times(1)).execute(eq(expectedId.getValue()));
//
//    }
//
//    @Test
//    public void givenAInvalidId_whenCallsGetCastMember_shouldReturnNotFound() throws Exception {
//        final var expectedErrorMessage = "CastMember with ID 123 was not found";
//        final var expectedId = CastMemberID.from("123");
//
//        when(getCastMemberByIdUseCase.execute(any())).thenThrow(NotFoundException.with(
//                CastMember.class, expectedId));
//
//
//        final var request = get("/CastMembers/{id}", expectedId.getValue())
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON);
//
//        final var response = this.mvc.perform(request)
//                .andDo(print());
//
//
//        response.andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));
//    }
//
//    @Test
//    public void givenAValidCommand_whenCallsUpdateCastMember_shouldReturnCastMemberId() throws Exception {
//        // given
//        final var expectedId = "123";
//        final var expectedName = "Filmes";
//        final var expectedDescription = "A categoria mais assistida";
//        final var expectedIsActive = true;
//
//        when(updateCastMemberUseCase.execute(any()))
//                .thenReturn(Right(UpdateCastMemberOutput.from(expectedId)));
//
//        final var aCommand =
//                new UpdateCastMemberRequest(expectedName, expectedDescription, expectedIsActive);
//
//        // when
//        final var request = put("/CastMembers/{id}", expectedId)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(aCommand));
//
//        final var response = this.mvc.perform(request)
//                .andDo(print());
//
//        // then
//        response.andExpect(status().isOk())
//                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.id", equalTo(expectedId)));
//
//        verify(updateCastMemberUseCase, times(1)).execute(argThat(cmd ->
//                Objects.equals(expectedName, cmd.name())
//                        && Objects.equals(expectedDescription, cmd.description())
//                        && Objects.equals(expectedIsActive, cmd.isActive())
//        ));
//    }
//
//
//    @Test
//    public void givenAInvalidName_whenCallsUpdateCastMember_thenShouldReturnDomainException() throws Exception {
//        // given
//        final var expectedId = "123";
//        final var expectedName = "Filmes";
//        final var expectedDescription = "A categoria mais assistida";
//        final var expectedIsActive = true;
//
//        final var expectedErrorCount = 1;
//        final var expectedMessage = "'name' should not be null";
//
//        when(updateCastMemberUseCase.execute(any()))
//                .thenReturn(Left(Notification.create(new Error(expectedMessage))));
//
//        final var aCommand =
//                new UpdateCastMemberRequest(expectedName, expectedDescription, expectedIsActive);
//
//        // when
//        final var request = put("/CastMembers/{id}", expectedId)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(aCommand));
//
//        final var response = this.mvc.perform(request)
//                .andDo(print());
//
//        // then
//        response.andExpect(status().isUnprocessableEntity())
//                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.errors", hasSize(expectedErrorCount)))
//                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedMessage)));
//
//        verify(updateCastMemberUseCase, times(1)).execute(argThat(cmd ->
//                Objects.equals(expectedName, cmd.name())
//                        && Objects.equals(expectedDescription, cmd.description())
//                        && Objects.equals(expectedIsActive, cmd.isActive())
//        ));
//    }
//
//    @Test
//    public void givenACommandWithInvalidID_whenCallsUpdateCastMember_shouldReturnNotFoundException() throws Exception {
//        // given
//        final var expectedId = "not-found";
//        final var expectedName = "Filmes";
//        final var expectedDescription = "A categoria mais assistida";
//        final var expectedIsActive = true;
//
//        final var expectedErrorMessage = "CastMember with ID not-found was not found";
//
//        when(updateCastMemberUseCase.execute(any()))
//                .thenThrow(NotFoundException.with(CastMember.class, CastMemberID.from(expectedId)));
//
//        final var aCommand =
//                new UpdateCastMemberRequest(expectedName, expectedDescription, expectedIsActive);
//
//        // when
//        final var request = put("/CastMembers/{id}", expectedId)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(aCommand));
//
//        final var response = this.mvc.perform(request)
//                .andDo(print());
//
//        // then
//        response.andExpect(status().isNotFound())
//                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));
//
//        verify(updateCastMemberUseCase, times(1)).execute(argThat(cmd ->
//                Objects.equals(expectedName, cmd.name())
//                        && Objects.equals(expectedDescription, cmd.description())
//                        && Objects.equals(expectedIsActive, cmd.isActive())
//        ));
//    }
//
//    @Test
//    public void givenAValidId_whenCallsDeleteCastMember_shouldReturnNoContent() throws Exception {
//        // given
//        final var expectedId = "123";
//
//        doNothing()
//                .when(deleteCastMemberUseCase).execute(any());
//
//        // when
//        final var request = delete("/CastMembers/{id}", expectedId)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON);
//
//        final var response = this.mvc.perform(request)
//                .andDo(print());
//
//        // then
//        response.andExpect(status().isNoContent());
//
//        verify(deleteCastMemberUseCase, times(1)).execute(eq(expectedId));
//    }
//
//    @Test
//    public void givenValidParams_whenCallsListCastMembers_shouldReturnCastMembers() throws Exception {
//        // given
//        final var aCastMember = CastMember.newCastMember("Movies", null, true);
//
//        final var expectedPage = 0;
//        final var expectedPerPage = 10;
//        final var expectedTerms = "movies";
//        final var expectedSort = "description";
//        final var expectedDirection = "asc";
//        final var expectedItemsCount = 1;
//        final var expectedTotal = 1;
//
//        final var expectedItems = List.of(CastMemberListOutput.from(aCastMember));
//
//        when(listCastMembersUseCase.execute(any()))
//                .thenReturn(new Pagination<>(expectedPage, expectedPerPage, expectedTotal, expectedItems));
//
//        // when
//        final var request = get("/CastMembers")
//                .queryParam("page", String.valueOf(expectedPage))
//                .queryParam("perPage", String.valueOf(expectedPerPage))
//                .queryParam("sort", expectedSort)
//                .queryParam("dir", expectedDirection)
//                .queryParam("search", expectedTerms)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON);
//
//        final var response = this.mvc.perform(request)
//                .andDo(print());
//
//        // then
//        response.andExpect(status().isOk())
//                .andExpect(jsonPath("$.current_page", equalTo(expectedPage)))
//                .andExpect(jsonPath("$.per_page", equalTo(expectedPerPage)))
//                .andExpect(jsonPath("$.total", equalTo(expectedTotal)))
//                .andExpect(jsonPath("$.items", hasSize(expectedItemsCount)))
//                .andExpect(jsonPath("$.items[0].id", equalTo(aCastMember.getId().getValue())))
//                .andExpect(jsonPath("$.items[0].name", equalTo(aCastMember.getName())))
//                .andExpect(jsonPath("$.items[0].description", equalTo(aCastMember.getDescription())))
//                .andExpect(jsonPath("$.items[0].is_active", equalTo(aCastMember.isActive())))
//                .andExpect(jsonPath("$.items[0].created_at", equalTo(aCastMember.getCreatedAt().toString())))
//                .andExpect(jsonPath("$.items[0].deleted_at", equalTo(aCastMember.getDeletedAt())));
//
//        verify(listCastMembersUseCase, times(1)).execute(argThat(query ->
//                Objects.equals(expectedPage, query.page())
//                        && Objects.equals(expectedPerPage, query.perPage())
//                        && Objects.equals(expectedDirection, query.direction())
//                        && Objects.equals(expectedSort, query.sort())
//                        && Objects.equals(expectedTerms, query.terms())
//        ));
//    }
}

