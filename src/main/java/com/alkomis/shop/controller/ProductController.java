package com.alkomis.shop.controller;


import com.alkomis.shop.dto.ProductDTO;
import com.alkomis.shop.exceptions.ElementNotFoundException;
import com.alkomis.shop.model.Product;
import com.alkomis.shop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST controller, for all endpoints to interact with {@link Product} entity.
 */
@Tag(name = "Product", description = "Product API")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Provides all products from database.
     *
     * @return list of all {@link ProductDTO} from database.
     * @throws ElementNotFoundException when products are not found.
     */
    @Operation(summary = "Get list of all products from database")
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * Gets product with specific id from database.
     *
     * @param productId id of requested product.
     * @return {@link ProductDTO} of requested product and 200 status code.
     * @throws ElementNotFoundException when product isn't found and returns 404 response code.
     */
    @Operation(summary = "Get product by id from database")
    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") long productId) {
        ProductDTO neededProduct = productService.getProductById(productId);
        return new ResponseEntity<>(neededProduct, HttpStatus.OK);
    }

    /**
     * Saves new {@link Product} into database.
     *
     * @param productDTO of new product.
     * @return {@link ProductDTO} of saved product and response code 201.
     */
    @Operation(summary = "Add new product in database")
    @PostMapping
    public ResponseEntity<ProductDTO> addNewProduct(@Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.addNewProduct(productDTO), HttpStatus.CREATED);
    }

    /**
     * Update properties of a {@link Product} with specific id.
     *
     * @param productId  id of updated product.
     * @param productDTO {@link ProductDTO} with edited parameters
     * @return status code 200 in case of successful update.
     * @throws ElementNotFoundException in case when provided id was not found in database and returns 404 status code.
     */
    @Operation(summary = "Update product with provided id in database")
    @PutMapping("{id}")
    public ResponseEntity<ProductDTO> updateProductById(@PathVariable("id") long productId, @Valid @RequestBody ProductDTO productDTO) {
        productService.updateProductById(productId, productDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Marks {@link Product} as deleted in database.
     *
     * @param productId id of product to delete.
     * @return status code 200 in case of successful operation.
     * @throws ElementNotFoundException in case when provided id was not found in database and returns 404 status code.
     */
    @Operation(summary = "Mark product with provided id as deleted")
    @DeleteMapping("{id}")
    public ResponseEntity<ProductDTO> deleteProductById(@PathVariable("id") long productId) {
        productService.deleteProductById(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}