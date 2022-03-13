package com.alkomis.shop.repository;

import com.alkomis.shop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interface of repository, containing CRUD logic for {@link Category} entity.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Fetches first {@link Category} entity with matching title.
     *
     * @param title value of a {@link Category} object.
     * @return Optional of {@link Category}.
     */
    Optional<Category> findTopCategoryByTitle(String title);
}
