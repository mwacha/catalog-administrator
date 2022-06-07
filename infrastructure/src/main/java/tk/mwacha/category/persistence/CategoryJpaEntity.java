package tk.mwacha.category.persistence;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tk.mwacha.domain.category.Category;
import tk.mwacha.domain.category.CategoryID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Table(name = "category")
@Entity
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class CategoryJpaEntity {

    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 4000)
    private String description;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    @Column(name = "deleted_at", columnDefinition = "DATETIME(6)")
    private Instant deletedAt;

    public static CategoryJpaEntity from(final Category category) {
        return CategoryJpaEntity.builder()
                .id(category.getId().getValue())
                .name(category.getName())
                .description(category.getDescription())
                .active(category.isActive())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .deletedAt(category.getDeletedAt()).build();
    }

    public Category toAggregate() {
        return Category.with(
                CategoryID.from(id),
                name,
                description,
                active,
                createdAt,
                updatedAt,
                deletedAt
        );
    }



}