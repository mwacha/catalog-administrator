package tk.mwacha.domain.genre;

import lombok.Getter;
import lombok.Singular;
import tk.mwacha.domain.AggregateRoot;
import tk.mwacha.domain.category.CategoryID;
import tk.mwacha.domain.exceptions.NotificationException;
import tk.mwacha.domain.utils.InstantUtils;
import tk.mwacha.domain.validation.ValidationHandler;
import tk.mwacha.domain.validation.handler.Notification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Genre extends AggregateRoot<GenreID> {

    private String name;
    private boolean active;
    @Singular private List<CategoryID> categories;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    protected Genre(final GenreID id,
                    final String name,
                    final boolean isActive,
                    final List<CategoryID> categories,
                    final Instant createdAt,
                    final Instant updatedAt,
                    final Instant deletedAt) {
        super(id);

        this.name = name;
        this.active = isActive;
        this.categories = categories;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;

        selfValidate();

    }

    public static Genre newGenre(final String name, final boolean isActive) {
        final var id = GenreID.unique();
        final var now = InstantUtils.now();
        final var deletedAt = isActive ? null : now;

        return new Genre(id, name, isActive, new ArrayList<>(), now, now, deletedAt);
    }

    public Genre with(final GenreID id,
                    final String name,
                    final boolean isActive,
                    final List<CategoryID> categories,
                    final Instant createdAt,
                    final Instant updatedAt,
                    final Instant deletedAt) {
        return new Genre(id, name, isActive, categories, createdAt, updatedAt, deletedAt);
    }

    public Genre with(final Genre genre) {
        return new Genre(genre.id, genre.name, genre.active, new ArrayList<>(genre.categories),
                genre.createdAt, genre.updatedAt, genre.deletedAt);
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new GenreValidator(this, handler).validate();
    }


    public Genre deactivate() {
        if (getDeletedAt() == null) {
            this.deletedAt = InstantUtils.now();
        }

        this.active = false;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Genre activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Genre update(final String name, final boolean isActive, final List<CategoryID> categories) {
        if (isActive) {
            activate();
        } else {
            deactivate();
        }

        this.name = name;
        this.categories = new ArrayList<>(categories != null ? categories : Collections.emptyList());
        this.updatedAt = InstantUtils.now();

        selfValidate();

        return this;
    }

    public Genre removeCategory(final CategoryID id) {
        if (id == null) {
            return this;
        }

        this.categories.remove(id);
        this.updatedAt = InstantUtils.now();

        return this;
    }

    public Genre addCategory(final CategoryID id) {

        if (id == null) {
            return this;
        }

        this.categories.add(id);
        this.updatedAt = InstantUtils.now();

        return this;
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Failed to create a Aggregate Genre", notification);
        }
    }
}
