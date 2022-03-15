package com.alkomis.shop.repository;

import com.alkomis.shop.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category testCategory = new Category(1, "sneakers", new ArrayList<>());

    @BeforeEach
    public void setUp() {
        categoryRepository.save(testCategory);
    }

    @Test
    public void findTopCategoryByTitle_categoryWithMatchingTitleFound_optionalWithCategory() {
        Optional<Category> result = categoryRepository.findTopCategoryByTitle("sneakers");

        assertEquals(testCategory, result.get());
    }

    @Test
    public void findTopCategoryByTitle_categoryNotFound_emptyOptional() {
        Optional<Category> result = categoryRepository.findTopCategoryByTitle("boots");

        assertTrue(result.isEmpty());
    }
}