package tk.mwacha.infrastructure.genre.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tk.mwacha.domain.category.CategoryID;
import tk.mwacha.domain.genre.Genre;
import tk.mwacha.domain.genre.GenreID;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "genres")
@NoArgsConstructor
@Getter
@Setter
public class GenreJpaEntity {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GenreCategoryJpaEntity> categories;

    @Column(nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    @Column(columnDefinition = "DATETIME(6)")
    private Instant deletedAt;

    private GenreJpaEntity(
            final String anId,
            final String aName,
            final boolean isActive,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        this.id = anId;
        this.name = aName;
        this.active = isActive;
        this.categories = new HashSet<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static GenreJpaEntity from(final Genre aGenre) {
        final var anEntity = new GenreJpaEntity(
                aGenre.getId().getValue(),
                aGenre.getName(),
                aGenre.isActive(),
                aGenre.getCreatedAt(),
                aGenre.getUpdatedAt(),
                aGenre.getDeletedAt()
        );

        aGenre.getCategories()
                .forEach(anEntity::addCategory);

        return anEntity;
    }

    public Genre toAggregate() {
        return Genre.with(
                GenreID.from(getId()),
                getName(),
                isActive(),
                getCategoryIDs(),
                getCreatedAt(),
                getUpdatedAt(),
                getDeletedAt()
        );
    }

    private void addCategory(final CategoryID anId) {
        this.categories.add(GenreCategoryJpaEntity.from(this, anId));
    }

    private void removeCategory(final CategoryID anId) {
        this.categories.remove(GenreCategoryJpaEntity.from(this, anId));
    }

    public List<CategoryID> getCategoryIDs() {
        return getCategories().stream()
                .map(it -> CategoryID.from(it.getId().getCategoryId()))
                .toList();
    }
}
