package tk.mwacha.category.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryJpaEntity, UUID> {
}
