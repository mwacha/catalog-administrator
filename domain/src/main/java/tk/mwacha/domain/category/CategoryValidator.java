package tk.mwacha.domain.category;

import tk.mwacha.domain.validation.Error;
import tk.mwacha.domain.validation.ValidationHandler;
import tk.mwacha.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;
    public static final int NAME_MAX_LENGTH = 255;
    public static final int NAME_MIN_LENGTH = 3;

    public CategoryValidator(final Category category, final ValidationHandler handler) {
        super(handler);
        this.category = category;
    }

    @Override
    public void validate() {
        checkNameConstrtaints();
    }

    private void checkNameConstrtaints() {
        final var name = this.category.getName();
        if(name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if(name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final var length = name.trim().length();

        if(length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characteres"));
            return;
        }
    }
}
