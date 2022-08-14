package tk.mwacha.domain.castmember;

import tk.mwacha.domain.category.Category;
import tk.mwacha.domain.validation.Error;
import tk.mwacha.domain.validation.ValidationHandler;
import tk.mwacha.domain.validation.Validator;

public class CastMemberValidator extends Validator {

    private final CastMember castMember;
    private static final int NAME_MAX_LENGTH = 255;
    private static final int NAME_MIN_LENGTH = 3;

    public CastMemberValidator(final CastMember category, final ValidationHandler handler) {
        super(handler);
        this.castMember = category;
    }

    @Override
    public void validate() {
        checkNameConstraints();
        checkTypeConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.castMember.getName();
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
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
            return;
        }
    }

    private void checkTypeConstraints() {
        final var type = this.castMember.getType();

        if(type == null) {
            this.validationHandler().append(new Error("'type' should not be null"));
            return;
        }
    }
}
