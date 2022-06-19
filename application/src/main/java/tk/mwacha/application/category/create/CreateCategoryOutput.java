package tk.mwacha.application.category.create;

import tk.mwacha.domain.category.Category;

import java.util.UUID;

public record CreateCategoryOutput(UUID id) {

    public static CreateCategoryOutput from(final Category category) {
        return new CreateCategoryOutput(category.getId().getValue());
    }
}
