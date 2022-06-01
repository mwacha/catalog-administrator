package tk.mwacha.application.category.create;

import tk.mwacha.domain.category.Category;
import tk.mwacha.domain.category.CategoryID;

public record CreateCategoryOutput(CategoryID id) {

    public static CreateCategoryOutput from(final Category category) {
        return new CreateCategoryOutput(category.getId());
    }
}
