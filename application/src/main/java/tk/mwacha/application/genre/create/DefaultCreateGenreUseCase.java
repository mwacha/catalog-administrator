package tk.mwacha.application.genre.create;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import tk.mwacha.domain.category.CategoryGateway;
import tk.mwacha.domain.category.CategoryID;
import tk.mwacha.domain.exceptions.NotificationException;
import tk.mwacha.domain.genre.Genre;
import tk.mwacha.domain.genre.GenreGateway;
import tk.mwacha.domain.validation.Error;
import tk.mwacha.domain.validation.ValidationHandler;
import tk.mwacha.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DefaultCreateGenreUseCase extends CreateGenreUseCase {

    @NonNull
    private final CategoryGateway categoryGateway;
    @NonNull
    private final GenreGateway genreGateway;

    @Override
    public CreateGenreOutput execute(CreateGenreCommand command) {
        final var name = command.name();
        final var isActive = command.isActive();
        final var categories = toCategoryID(command.categories());

        final var notification = Notification.create();
        notification.append(validateCategories(categories));
        final var genre = notification.validate(() -> Genre.newGenre(name, isActive));

        if (notification.hasError()) {
            throw new NotificationException("Could not create Aggregate Genre", notification);
        }

        genre.addCategories(categories);
        return CreateGenreOutput.from(this.genreGateway.create(genre));
    }


    private ValidationHandler validateCategories(List<CategoryID> ids) {
        final var notification = Notification.create();

        if (ids == null || ids.isEmpty()) {
            return notification;
        }

        final var retrievedIds = categoryGateway.existsByIds(ids);

        if (ids.size() != retrievedIds.size()) {
            final var missingIds = new ArrayList<>(ids);
            missingIds.removeAll(retrievedIds);

           final var missingIdsMessage =  missingIds.stream().map(CategoryID::getValue)
                    .collect(Collectors.joining(", "));

           notification.append(new Error("Some categories could not be found: %s".formatted(missingIdsMessage)));
        }

        return notification;
    }


    private List<CategoryID> toCategoryID(List<String> categories) {
        return categories.stream().map(CategoryID::from)
                .toList();
    }
}
