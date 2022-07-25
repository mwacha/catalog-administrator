package tk.mwacha.infrastructure.genre.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tk.mwacha.domain.category.CategoryID;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "genres_categories")
@Getter
@Setter
public class GenreCategoryJpaEntity {

    @EmbeddedId
    private GenreCategoryID id;

    @ManyToOne
    @MapsId("genreId")
    private GenreJpaEntity genre;

    private GenreCategoryJpaEntity(final GenreJpaEntity genre, final CategoryID categoryID) {
        this.id = GenreCategoryID.from(genre.getId(), categoryID.getValue());
        this.genre = genre;
    }

    public static GenreCategoryJpaEntity from(final GenreJpaEntity genre, final CategoryID categoryID) {
        return new GenreCategoryJpaEntity(genre, categoryID);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GenreCategoryJpaEntity that = (GenreCategoryJpaEntity) o;
        return id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
