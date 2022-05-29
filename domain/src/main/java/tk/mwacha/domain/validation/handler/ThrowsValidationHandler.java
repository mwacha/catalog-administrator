package tk.mwacha.domain.validation.handler;

import tk.mwacha.domain.exceptions.DomainException;
import tk.mwacha.domain.validation.Error;
import tk.mwacha.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(final Error error) {
        throw DomainException.with(error);
    }

    @Override
    public ValidationHandler append(final ValidationHandler handler) {
        throw DomainException.with(handler.getErrors());
    }

    @Override
    public ValidationHandler append(Validation validation) {
       try {
           validation.validate();
       } catch (final Exception ex) {
           throw DomainException.with(new Error(ex.getMessage()));
       }
       return this;
    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }
}
