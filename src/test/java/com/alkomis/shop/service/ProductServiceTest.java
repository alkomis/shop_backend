package com.alkomis.shop.service;

import com.alkomis.shop.dto.*;
import com.alkomis.shop.exceptions.ElementNotFoundException;
import com.alkomis.shop.mappers.ProductMapper;
import com.alkomis.shop.model.*;
import com.alkomis.shop.model.enums.TargetAudience;
import com.alkomis.shop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductServiceImp productService;

    private Product product = new Product(1, "sneakers", new BigDecimal(300), "sturdy sneakers", "Nike", false, TargetAudience.MEN, new ArrayList<>(), new Category(1, "sneakers", new ArrayList<>()));
    private ProductDTO productDTO = new ProductDTO();

    @BeforeEach
    public void setUp() {
        productDTO.setId(1);
        productDTO.setTitle("sneakers");
        productDTO.setPrice(new BigDecimal(300));
        productDTO.setDescription("sturdy sneakers");
        productDTO.setBrand("Nike");
        productDTO.setTargetAudience(TargetAudience.MEN);
        productDTO.setProductProperties(new ArrayList<>());

        CategoryDTO testCategoryDto = new CategoryDTO();
        testCategoryDto.setId(1);
        testCategoryDto.setTitle("sneakers");

        productDTO.setCategory(testCategoryDto);
    }

    @Test
    public void getAllProducts_getListOfAllProducts_listOfDTO() {
        List<ProductDTO> testResult = new ArrayList<>();
        testResult.add(productDTO);

        when(productRepository.findAll(any(Specification.class))).thenReturn(List.of(product));
        when(productMapper.productToProductDto(any(Product.class))).thenReturn(productDTO);

        assertEquals(testResult, productService.getAllProducts());
    }

    @Test
    public void getAllProducts_listIsEmpty_throwsException() {

        // Changed archive to true, so method will not find available products and throw exception.
        product.setArchive(true);

        when(productRepository.findAll(any(Specification.class))).thenReturn(List.of());
        when(productMapper.productToProductDto(any(Product.class))).thenReturn(productDTO);

        assertThrows(ElementNotFoundException.class, () -> productService.getAllProducts());
    }

    @Test
    public void getProductById_getSpecificProduct_DTOOfProduct() {

        when(productRepository.findOne(any(Specification.class))).thenReturn(Optional.of(product));
        when(productMapper.productToProductDto(any(Product.class))).thenReturn(productDTO);

        assertEquals(productDTO, productService.getProductById(1));
    }

    @Test
    public void getProductById_cantFindSpecificProductById_throwsException() {

        // Changed archive to true, so method will not find product and throw exception.
        product.setArchive(true);

        when(productRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());
        when(productMapper.productToProductDto(any(Product.class))).thenReturn(productDTO);

        assertThrows(ElementNotFoundException.class, () -> productService.getProductById(any(Long.class)));
    }

    @Test
    public void addNewProduct_successfullyAddNewProduct_DTOOfAddedProduct() {
        when(productMapper.productDTOToProduct(any(ProductDTO.class))).thenReturn(product);
        when(productMapper.productToProductDto(any(Product.class))).thenReturn(productDTO);
        when(categoryService.findTopCategoryByTitle(any(String.class))).thenReturn(product.getCategory());
        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(product);

        assertEquals(productDTO, productService.addNewProduct(productDTO));
    }

    @Test
    public void updateProductById_updateProductSuccessfully_exitSuccessfully() {
        when(productMapper.productDTOToProduct(any(ProductDTO.class))).thenReturn(product);
        when(categoryService.findTopCategoryByTitle(any(String.class))).thenReturn(product.getCategory());
        when(productRepository.findOne(any(Specification.class))).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.updateProductById(anyLong(), productDTO);

        verify(productRepository, times(1)).findOne(any(Specification.class));
        verify(productMapper, times(1)).productDTOToProduct(any(ProductDTO.class));
        verify(productRepository, times(1)).save(any(Product.class));
        verify(categoryService, times(2)).findTopCategoryByTitle(any(String.class));
    }

    @Test
    public void updateProductById_cantFindProductForUpdate_throwsException() {

        // Changed archive to true, so method will not find product for update and throw exception.
        product.setArchive(true);

        when(productMapper.productDTOToProduct(any(ProductDTO.class))).thenReturn(product);
        when(productRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        assertThrows(ElementNotFoundException.class, () -> productService.updateProductById(anyLong(), productDTO));
    }

    @Test
    public void deleteProductById_markProductDeleted_exitSuccessfully() {
        when(productRepository.findOne(any(Specification.class))).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.deleteProductById(1);

        verify(productRepository, times(1)).findOne(any(Specification.class));
        verify(productRepository, times(1)).save(any(Product.class));
        assertTrue(product.isArchive());
    }

    @Test
    public void deleteProductById_cantFindProductForDeleteMarking_throwsException() {

        // Changed archive to true, so method will not find product for delete operation and throw exception.
        product.setArchive(true);

        when(productRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        assertThrows(ElementNotFoundException.class, () -> productService.deleteProductById(1));
    }
}