package tk.mwacha.domain.genre;

import tk.mwacha.domain.pagination.Pagination;
import tk.mwacha.domain.pagination.SearchQuery;

import java.util.Optional;

public interface GenreGateway {

    Genre create(Genre genre);

    void deleteById(GenreID id);

    Optional<Genre> findById(GenreID id);

    Genre update(Genre genre);

    Pagination<Genre> findAll(SearchQuery query);
}
