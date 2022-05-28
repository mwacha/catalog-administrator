package tk.mwacha.domain.validation;

public interface ValidationHandler {
    ValidationHandler append(Error error);
}
