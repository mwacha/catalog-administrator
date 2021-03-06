package tk.mwacha.domain.category;

import lombok.Getter;
import tk.mwacha.domain.AggregateRoot;
import tk.mwacha.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Objects;

@Getter
public class Category extends AggregateRoot<CategoryID> implements Cloneable {

    private String name;
    private String description;
    private boolean active;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
    private Instant deletedAt;

    private Category(final CategoryID categoryID,
                     final String name,
                     final String description,
                     final boolean active,
                     final Instant createdAt,
                     final Instant updatedAt,
                     final Instant deletedAt) {
        super(categoryID);
        this.name = name;
        this.description = description;
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt should not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt should not be null");
        this.deletedAt = deletedAt;
    }

    public static Category newCategory(final String name,
                                       final String description,
                                       final boolean active) {

        var deletedAt = active ? null : Instant.now();
        return new Category(CategoryID.unique(), name, description, active, Instant.now(), Instant.now(), deletedAt);
    }

    public static Category with(final Category aCategory) {
        return with(
                aCategory.getId(),
                aCategory.name,
                aCategory.description,
                aCategory.isActive(),
                aCategory.createdAt,
                aCategory.updatedAt,
                aCategory.deletedAt
        );
    }

    public static Category with(final CategoryID id,
                                final String name,
                                final String description,
                                final boolean active,
                                final Instant createdAt,
                                final Instant updatedAt,
                                final Instant deletedAt) {
        return new Category(id, name, description, active, createdAt, updatedAt, deletedAt);
    }


    @Override
    public void validate(ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public Category activate() {
        this.deletedAt = null;

        this.active = true;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category deactivate() {
        if(getDeletedAt() == null) {
            this.deletedAt = Instant.now();
        }

        this.active = false;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category update(final String name, final String description, final boolean isActive) {

        if(isActive) {
            activate();
        } else {
            deactivate();
        }

        this.name = name;
        this.description = description;
        this.updatedAt = Instant.now();

        return this;
    }

    public Category clone() {
        try {
           return (Category) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
