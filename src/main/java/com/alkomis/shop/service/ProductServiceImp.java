package com.alkomis.shop.service;

import com.alkomis.shop.dto.CategoryDTO;
import com.alkomis.shop.dto.ProductDTO;
import com.alkomis.shop.exceptions.ElementNotFoundException;
import com.alkomis.shop.mappers.ProductMapper;
import com.alkomis.shop.model.Category;
import com.alkomis.shop.model.Product;
import com.alkomis.shop.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.alkomis.shop.specifications.ProductSpecifications.*;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Class contains implementation of {@link ProductService} interface.
 */
@Slf4j
@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    @Lazy
    private CategoryService categoryService;

    @Override
    @Transactional
    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> allProducts = productRepository.findAll(activeProducts())
                .stream()
                .map(product -> productMapper.productToProductDto(product)).toList();

        if (allProducts.isEmpty()) {
            log.error("There is no available non-archive products in database.");
            throw new ElementNotFoundException("Can't find any products.");
        }

        log.info("Found products in the database: {}.", allProducts);
        return allProducts;
    }

    @Override
    @Transactional
    public ProductDTO getProductById(long id) {
        Optional<ProductDTO> existingProduct = productRepository.findOne(where(byId(id).and(activeProducts())))
                .stream()
                .map(product -> productMapper.productToProductDto(product)).findFirst();

        if (existingProduct.isEmpty()) {
            log.error("Failed to find non-archive product with id: {}.", id);
            throw new ElementNotFoundException("Product with id: " + id + "  does not exist.");
        }

        ProductDTO result = existingProduct.get();
        log.info("Found product by id: {} in database. {}", id, result);
        return result;
    }

    @Override
    @Transactional
    public ProductDTO addNewProduct(ProductDTO productDTO) {
        Product productToAdd = productMapper.productDTOToProduct(productDTO);
        ProductDTO addedProductDto = productMapper.productToProductDto(productToAdd);
        productToAdd.setCategory(categoryValidation(productDTO.getCategory()));
        productRepository.save(productToAdd);
        log.info("Product: {} added to database.", productToAdd);
        return addedProductDto;
    }

    @Override
    @Transactional
    public void updateProductById(long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findOne(byId(id))
                .orElseThrow(() -> {
                    log.error("Failed to find non-archive product with id: {} for update in database.", id);
                    return new ElementNotFoundException("Product with id: " + id + " does not exist.");
                });
        Product updatedProduct = productMapper.productDTOToProduct(productDTO);

        existingProduct.setTitle(updatedProduct.getTitle());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setBrand(updatedProduct.getBrand());
        existingProduct.setTargetAudience(updatedProduct.getTargetAudience());
        existingProduct.getProductProperties().clear();
        existingProduct.getProductProperties().addAll(updatedProduct.getProductProperties());
        existingProduct.setCategory(categoryValidation(productDTO.getCategory()));

        productRepository.save(existingProduct);

        log.info("Product with id: {} was updated to following parameters: {}.", id, updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProductById(long id) {
        Product existingProduct = productRepository.findOne(where(byId(id).and(activeProducts())))
                .orElseThrow(() -> {
                    log.error("Failed to find non-archive product with id: {} for deleting in database.", id);
                    return new ElementNotFoundException("Product with id: " + id + " does not exist.");
                });
        existingProduct.setArchive(true);
        productRepository.save(existingProduct);
        log.info("Product with id: {} was marked as deleted.", id);
    }

    @Override
    @Transactional
    public List<ProductDTO> findProductsByCategoryId(long id) {
        List<Product> allProducts = productRepository.findAll(where(byCategoryId(id).and(activeProducts())));

        if (allProducts.isEmpty()) {
            log.error("There is no available non-archive products in database with category id {}. ", id);
            throw new ElementNotFoundException("Can't find any products in category with id " + id + ".");
        }

        log.info("Found products in the database: {}.", allProducts);
        return allProducts.stream().map(product -> productMapper.productToProductDto(product)).toList();
    }

    /**
     * Method checks database for provided {@link Category}.
     *
     * @param categoryDTO DTO of category from request body.
     * @return if {@link Category} pulls it from database, else maps provided DTO into new {@link Category} entity.
     */
    private Category categoryValidation(CategoryDTO categoryDTO) {
        String categoryTitle = categoryDTO.getTitle();
        Category validCategory;

        if (categoryService.findTopCategoryByTitle(categoryTitle) != null) {
            validCategory = categoryService.findTopCategoryByTitle(categoryTitle);
        } else {
            validCategory = productMapper.categoryDtoToCategory(categoryDTO);
        }

        return validCategory;
    }
}

