package tk.mwacha.application.category.update;

import tk.mwacha.domain.category.Category;
import tk.mwacha.domain.category.CategoryID;

public record UpdateCategoryOutput(CategoryID id) {

    public static UpdateCategoryOutput from(final Category category) {
        return new UpdateCategoryOutput(category.getId());
    }
}
