package com.alkomis.shop.service;

import com.alkomis.shop.dto.*;
import com.alkomis.shop.exceptions.ElementNotFoundException;
import com.alkomis.shop.mappers.ProductMapper;
import com.alkomis.shop.model.*;
import com.alkomis.shop.model.enums.TargetAudience;
import com.alkomis.shop.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CategoryServiceTest {

    Category category = new Category(1, "sneakers", new ArrayList<>());
    CategoryDTO categoryDTO = new CategoryDTO();
    ProductDTO productDTO = new ProductDTO();
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ProductService productService;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private CategoryServiceImp categoryService;

    @BeforeEach
    public void setUp() {
        categoryDTO.setId(1);
        categoryDTO.setTitle("sneakers");

        productDTO.setId(1);
        productDTO.setTitle("sneakers");
        productDTO.setPrice(new BigDecimal(300));
        productDTO.setDescription("sturdy sneakers");
        productDTO.setBrand("Nike");
        productDTO.setTargetAudience(TargetAudience.MEN);
        productDTO.setProductProperties(new ArrayList<>());
        productDTO.setCategory(categoryDTO);
    }

    @Test
    public void getAllCategories_getListOfAllCategories_listOfCategoryDto() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1, "sneakers", new ArrayList<>()));
        categoryList.add(new Category(2, "slippers", new ArrayList<>()));
        categoryList.add(new Category(1, "boots", new ArrayList<>()));

        when(categoryRepository.findAll()).thenReturn(categoryList);
        when(productMapper.categoryToCategoryDto(any(Category.class))).thenReturn(categoryDTO);

        List<CategoryDTO> expected = new ArrayList<>();
        expected.add(categoryDTO);
        expected.add(categoryDTO);
        expected.add(categoryDTO);

        assertEquals(expected, categoryService.getAllCategories());
    }

    @Test
    public void getAllCategories_listOfCategoriesIsEmpty_throwsException() {
        List<Category> emptyList = new ArrayList<>();

        when(categoryRepository.findAll()).thenReturn(emptyList);
        when(productMapper.categoryToCategoryDto(any(Category.class))).thenReturn(categoryDTO);

        assertThrows(ElementNotFoundException.class, () -> categoryService.getAllCategories());
    }

    @Test
    public void getAllProductsByCategoryId_getAllProductsOfCategory_mapWithCategoryAsKeyAndListOfProductsAsValue() {
        List<ProductDTO> listOfTestProducts = new ArrayList<>();
        listOfTestProducts.add(productDTO);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(productMapper.categoryToCategoryDto(any(Category.class))).thenReturn(categoryDTO);
        when(productService.findProductsByCategoryId(anyLong())).thenReturn(listOfTestProducts);

        CategoryWithProductsDTO expected = new CategoryWithProductsDTO();
        expected.setId(categoryDTO.getId());
        expected.setTitle(categoryDTO.getTitle());
        expected.getProductList().addAll(listOfTestProducts);

        assertEquals(expected, categoryService.getAllProductsByCategoryId(anyLong()));
    }

    @Test
    public void getAllProductsByCategoryId_categoryNotFound_throwsException() {
        List<ProductDTO> listOfTestProducts = new ArrayList<>();
        listOfTestProducts.add(productDTO);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(productMapper.categoryToCategoryDto(any(Category.class))).thenReturn(categoryDTO);
        when(productService.findProductsByCategoryId(anyLong())).thenReturn(listOfTestProducts);

        assertThrows(ElementNotFoundException.class, () -> categoryService.getAllProductsByCategoryId(anyLong()));
    }

    @Test
    public void findTopCategoryByTitle_categoryWasFound_categoryEntity() {
        when(categoryRepository.findTopCategoryByTitle("sneakers")).thenReturn(Optional.of(category));

        assertEquals(category, categoryService.findTopCategoryByTitle("sneakers"));
    }

    @Test
    public void findTopCategoryByTitle_categoryNotFound_null() {
        when(categoryRepository.findTopCategoryByTitle("sneakers")).thenReturn(Optional.empty());

        assertNull(categoryService.findTopCategoryByTitle("sneakers"));
    }
}