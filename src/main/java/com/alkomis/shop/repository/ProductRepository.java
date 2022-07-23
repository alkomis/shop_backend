package com.alkomis.shop.repository;

import com.alkomis.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Interface of repository, containing CRUD logic for {@link Product} entity.

 */
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    /**
     * Fetches all {@link Product} from database by it's {@link com.alkomis.shop.model.Category} id.
     *
     * @param categoryId id of {@link com.alkomis.shop.model.Category} entity.
     * @return list of found products.
     */
    List<Product> findAllByCategoryId(long categoryId);

}