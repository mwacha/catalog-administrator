package tk.mwacha.domain.validation;

import java.util.List;

public interface ValidationHandler {
    ValidationHandler append(Error error);

    ValidationHandler append(ValidationHandler handler);

    ValidationHandler append(Validation validation);

    List<Error> getErrors();

    default boolean hasError() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError() {

        if(getErrors() != null && !getErrors().isEmpty()){
            return getErrors().get(0);
        }
        return null;
    }


    interface Validation{
        void validate();
    }
}
