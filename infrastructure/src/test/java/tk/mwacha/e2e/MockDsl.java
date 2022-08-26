package tk.mwacha.e2e;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tk.mwacha.domain.Identifier;
import tk.mwacha.domain.castmember.CastMemberID;
import tk.mwacha.domain.castmember.CastMemberType;
import tk.mwacha.domain.category.CategoryID;
import tk.mwacha.domain.genre.GenreID;
import tk.mwacha.infrastructure.castmember.models.CastMemberResponse;
import tk.mwacha.infrastructure.castmember.models.CreateCastMemberRequest;
import tk.mwacha.infrastructure.castmember.models.UpdateCastMemberRequest;
import tk.mwacha.infrastructure.category.models.CategoryResponse;
import tk.mwacha.infrastructure.category.models.CreateCategoryRequest;
import tk.mwacha.infrastructure.category.models.UpdateCategoryRequest;
import tk.mwacha.infrastructure.configuration.json.Json;
import tk.mwacha.infrastructure.genre.models.CreateGenreRequest;
import tk.mwacha.infrastructure.genre.models.GenreResponse;
import tk.mwacha.infrastructure.genre.models.UpdateGenreRequest;

import java.util.List;
import java.util.function.Function;

public interface MockDsl {

    MockMvc mvc();

    default CategoryID givenACategory(final String expectedName,
                                      final String expectedDescription,
                                      final boolean expectedIsActive) throws Exception {
        final var requestBody = new CreateCategoryRequest(expectedName, expectedDescription, expectedIsActive);

        final var actualId = this.given("/categories", requestBody);

        return CategoryID.from(actualId);
    }

    default ResultActions deleteACategory(final Identifier id) throws Exception {
        return this.delete("/categories", id);
    }


    default ResultActions listCategories(final int page, final int perPage) throws Exception {
        return listCategories(page, perPage, "", "", "");
    }


    default ResultActions listCategories(final int page, final int perPage, final String search) throws Exception {
        return listCategories(page, perPage, search, "", "");
    }

    default ResultActions listCategories(
            final int page,
            final int perPage,
            final String search,
            final String sort,
            final String direction
    ) throws Exception {
        return this.list("/categories", page, perPage, search, sort, direction);
    }

    default CategoryResponse retrieveACategory(final Identifier id) throws Exception {
        return this.retrieve("/categories/", id, CategoryResponse.class);
    }

    default ResultActions updateACategory(final Identifier id, final UpdateCategoryRequest request) throws Exception {
        return this.update("/categories/", id,request);
    }

    default ResultActions deleteAGenre(final Identifier id) throws Exception {
        return this.delete("/genres/", id);
    }

    default GenreID givenAGenre(final String expectedName,
                                final boolean expectedIsActive, final List<CategoryID> categories) throws Exception {

        final var requestBody = new CreateGenreRequest(expectedName, mapTo(categories, CategoryID::getValue), expectedIsActive);

        final var actualId = this.given("/genres", requestBody);

        return GenreID.from(actualId);
    }
    default ResultActions listGenres(final int page, final int perPage) throws Exception {
        return listGenres(page, perPage, "", "", "");
    }


    default ResultActions listGenres(final int page, final int perPage, final String search) throws Exception {
        return listGenres(page, perPage, search, "", "");
    }

    default ResultActions listGenres(
            final int page,
            final int perPage,
            final String search,
            final String sort,
            final String direction
    ) throws Exception {
        return this.list("/genres", page, perPage, search, sort, direction);
    }

    default GenreResponse retrieveAGenre(final Identifier id) throws Exception {
        return this.retrieve("/genres/", id, GenreResponse.class);
    }

    default ResultActions updateAGenre(final Identifier id, final UpdateGenreRequest request) throws Exception {
        return this.update("/genres/", id,request);
    }

    default <A, D> List<D> mapTo(final List<A> actual, final Function<A, D> mapper) {
        return actual.stream().map(mapper).toList();
    }

    private String given(final String url, final Object body) throws Exception {

        final var request = MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(Json.writeValueAsString(body));

        final var actualId = this.mvc().perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse().getHeader("Location")
                .replace("%s/".formatted(url), "");

        return actualId;
    }



    private ResultActions list(
            final String url,
            final int page,
            final int perPage,
            final String search,
            final String sort,
            final String direction
    ) throws Exception {
        final var aRequest = MockMvcRequestBuilders.get(url)
                .queryParam("page", String.valueOf(page))
                .queryParam("perPage", String.valueOf(perPage))
                .queryParam("search", search)
                .queryParam("sort", sort)
                .queryParam("dir", direction)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        return this.mvc().perform(aRequest);
    }

    private <T> T retrieve(final String url, final Identifier id, final Class<T> clazz) throws Exception {
        final var request =
                MockMvcRequestBuilders.get(url + id.getValue())
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_UTF8);

        final var json = this.mvc().perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        return Json.readValue(json, clazz);
    }

    private ResultActions delete(final String url, final Identifier id) throws Exception {

        final var request = MockMvcRequestBuilders.delete(url + id.getValue())
                .contentType(MediaType.APPLICATION_JSON);

        return this.mvc().perform(request);
    }

    private ResultActions update(final String url, final Identifier id, final Object body) throws Exception {

        final var request = MockMvcRequestBuilders.delete(url + id.getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(body));

        return this.mvc().perform(request);
    }

    default CastMemberID givenACastMember(final String aName, final CastMemberType aType) throws Exception {
        final var aRequestBody = new CreateCastMemberRequest(aName, aType);
        final var actualId = this.given("/cast_members", aRequestBody);
        return CastMemberID.from(actualId);
    }

    default ResultActions givenACastMemberResult(final String aName, final CastMemberType aType) throws Exception {
        final var aRequestBody = new CreateCastMemberRequest(aName, aType);
        return this.givenResult("/cast_members", aRequestBody);
    }

    private ResultActions givenResult(final String url, final Object body) throws Exception {
        final var aRequest = MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(body));

        return this.mvc().perform(aRequest);
    }

    default ResultActions listCastMembers(final int page, final int perPage) throws Exception {
        return listCastMembers(page, perPage, "", "", "");
    }

    default ResultActions listCastMembers(final int page, final int perPage, final String search) throws Exception {
        return listCastMembers(page, perPage, search, "", "");
    }

    default ResultActions listCastMembers(final int page, final int perPage, final String search, final String sort, final String direction) throws Exception {
        return this.list("/cast_members", page, perPage, search, sort, direction);
    }

    default CastMemberResponse retrieveACastMember(final CastMemberID anId) throws Exception {
        return this.retrieve("/cast_members/", anId, CastMemberResponse.class);
    }

    default ResultActions retrieveACastMemberResult(final CastMemberID anId) throws Exception {
        return this.retrieveResult("/cast_members/", anId);
    }

    private ResultActions retrieveResult(final String url, final Identifier anId) throws Exception {
        final var aRequest = MockMvcRequestBuilders.get(url + anId.getValue())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        return this.mvc().perform(aRequest);
    }

    default ResultActions updateACastMember(final CastMemberID anId, final String aName, final CastMemberType aType) throws Exception {
        return this.update("/cast_members/", anId, new UpdateCastMemberRequest(aName, aType));
    }

    default ResultActions deleteACastMember(final CastMemberID anId) throws Exception {
        return this.delete("/cast_members/", anId);
    }
}
