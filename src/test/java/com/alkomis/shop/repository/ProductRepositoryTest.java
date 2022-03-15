package com.alkomis.shop.repository;


import com.alkomis.shop.model.*;
import com.alkomis.shop.model.enums.TargetAudience;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findAllByCategoryId_getListOfProductsByIdOfCategory_listOfProduct() {
        Product testProduct1 = new Product(1, "sneakers", new BigDecimal(300), "sturdy sneakers", "Nike", false, TargetAudience.MEN, new ArrayList<>(), null);
        Product testProduct2 = new Product(2, "New Balance", new BigDecimal(300), "sturdy sneakers", "NB", false, TargetAudience.MEN, new ArrayList<>(), null);

        Category category = new Category(1, "sneakers", new ArrayList<>());
        testProduct1.setCategory(category);
        testProduct2.setCategory(category);
        productRepository.saveAndFlush(testProduct1);
        productRepository.saveAndFlush(testProduct2);

        List<Product> result = productRepository.findAllByCategoryId(1);
        List<Product> expected = new ArrayList<>();

        expected.add(testProduct1);
        expected.add(testProduct2);

        assertEquals(expected, result);
    }

    @Test
    public void findAllByCategoryId_getListOfProductsByIdOfCategory_emptyList() {
        List<Product> result = productRepository.findAllByCategoryId(1);

        assertTrue(result.isEmpty());
    }
}