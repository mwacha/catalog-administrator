package tk.mwacha.infrastructure.genre.presenters;


import tk.mwacha.application.genre.retrieve.get.GenreOutput;
import tk.mwacha.application.genre.retrieve.list.GenreListOutput;
import tk.mwacha.infrastructure.genre.models.GenreListResponse;
import tk.mwacha.infrastructure.genre.models.GenreResponse;

public interface GenreApiPresenter {

    static GenreResponse present(final GenreOutput output) {
        return new GenreResponse(
                output.id(),
                output.name(),
                output.categories(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
                );
    }

    static GenreListResponse present(final GenreListOutput output) {
        return new GenreListResponse(
                output.id(),
                output.name(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}
