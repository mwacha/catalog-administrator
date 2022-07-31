package tk.mwacha.infrastructure.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tk.mwacha.application.category.retrieve.list.ListCategoriesUseCase;
import tk.mwacha.application.genre.create.CreateGenreCommand;
import tk.mwacha.application.genre.create.CreateGenreUseCase;
import tk.mwacha.application.genre.delete.DeleteGenreUseCase;
import tk.mwacha.application.genre.retrieve.get.GetGenreByIdUseCase;
import tk.mwacha.application.genre.retrieve.list.ListGenreUseCase;
import tk.mwacha.application.genre.update.UpdateGenreCommand;
import tk.mwacha.application.genre.update.UpdateGenreUseCase;
import tk.mwacha.domain.pagination.Pagination;
import tk.mwacha.domain.pagination.SearchQuery;
import tk.mwacha.infrastructure.api.GenreAPI;
import tk.mwacha.infrastructure.genre.models.CreateGenreRequest;
import tk.mwacha.infrastructure.genre.models.GenreListResponse;
import tk.mwacha.infrastructure.genre.models.GenreResponse;
import tk.mwacha.infrastructure.genre.models.UpdateGenreRequest;
import tk.mwacha.infrastructure.genre.presenters.GenreApiPresenter;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class GenreController implements GenreAPI {

    private final CreateGenreUseCase createGenreUseCase;
    private final GetGenreByIdUseCase getGenreByIdUseCase;
    private final UpdateGenreUseCase updateGenreUseCase;
    private final DeleteGenreUseCase deleteGenreUseCase;
    private final ListGenreUseCase listGenreUseCase;

    @Override
    public ResponseEntity<?> create(final CreateGenreRequest input) {
       final var command =  CreateGenreCommand.with(
                input.name(),
                input.isActive(),
                input.categories()
        );

        final var output = this.createGenreUseCase.execute(command);

        return ResponseEntity.created(URI.create("/genres/" + output.id())).body(output);
    }

    @Override
    public Pagination<GenreListResponse> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return listGenreUseCase.execute(new SearchQuery(page, perPage, search, sort, direction))
               .map(GenreApiPresenter::present);
    }

    @Override
    public GenreResponse getById(final String id) {
        return GenreApiPresenter.present(this.getGenreByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateGenreRequest input) {
        final var command = UpdateGenreCommand.with(
                id,
                input.name(),
                input.active(),
                input.categories()
        );

        final var output = this.updateGenreUseCase.execute(command);

        return ResponseEntity.ok(output);

    }

    @Override
    public void deleteById(final String id) {
        this.deleteGenreUseCase.execute(id);
    }
}
