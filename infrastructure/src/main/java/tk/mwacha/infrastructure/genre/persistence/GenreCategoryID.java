package tk.mwacha.infrastructure.genre.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GenreCategoryID implements Serializable {

    @Column(nullable = false)
    private String genreId;

    @Column(nullable = false)
    private String categoryId;

    public static GenreCategoryID from(final String genreId, final String categoryId) {
        return new GenreCategoryID(genreId, categoryId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreCategoryID that = (GenreCategoryID) o;
        return genreId.equals(that.genreId) && categoryId.equals(that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreId, categoryId);
    }
}
