package tk.mwacha.infrastructure.utils;

import org.springframework.data.jpa.domain.Specification;

public final class SpecificationsUtils {

    private SpecificationsUtils() {}

    public static <T> Specification<T> like(final String prop, final String term) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get(prop)), like(term).toUpperCase());
    }

    private static String like(final String term) {
        return "%" + term +"%";
    }
}
