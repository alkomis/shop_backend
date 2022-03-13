package com.alkomis.shop.service;

import com.alkomis.shop.dto.ProductDTO;
import com.alkomis.shop.exceptions.ElementNotFoundException;
import com.alkomis.shop.model.Category;
import com.alkomis.shop.model.Product;

import java.util.List;

/**
 * Interface of a {@link Product} class service.
 */
public interface ProductService {

    /**
     * Returns all non-archived products from database.
     *
     * @return List of a {@link ProductDTO} objects.
     * @throws ElementNotFoundException if List is empty.
     */
    List<ProductDTO> getAllProducts();

    /**
     * Returns product with specific id.
     *
     * @param id of the {@link Product} entity.
     * @return {@link ProductDTO} of product from database.
     * @throws ElementNotFoundException if there is no product with provided id.
     */
    ProductDTO getProductById(long id);

    /**
     * Saves new {@link Product} to database.
     *
     * @param productDTO {@link ProductDTO} of a product to save.
     * @return {@link ProductDTO} of saved object.
     */
    ProductDTO addNewProduct(ProductDTO productDTO);

    /**
     * Updates {@link Product} with specific id in database.
     *
     * @param id         of product for update.
     * @param productDTO with new properties for update.
     * @throws ElementNotFoundException if product does not exist in database or marked as deleted.
     */
    void updateProductById(long id, ProductDTO productDTO);

    /**
     * Marks {@link Product} as deleted in database.
     *
     * @param id of product for deleting.
     * @throws ElementNotFoundException if product does not exist or already deleted.
     */
    void deleteProductById(long id);

    /**
     * Returns list of {@link ProductDTO} with specific {@link Category} id from database.
     *
     * @param id {@link Category} id.
     * @return list of found {@link ProductDTO}.
     */
    List<ProductDTO> findProductsByCategoryId(long id);
}
