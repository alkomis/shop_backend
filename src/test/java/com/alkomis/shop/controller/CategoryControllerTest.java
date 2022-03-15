package com.alkomis.shop.controller;


import com.alkomis.shop.dto.*;
import com.alkomis.shop.exceptions.ElementNotFoundException;
import com.alkomis.shop.model.enums.TargetAudience;
import com.alkomis.shop.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;
    private CategoryDTO testCategory = new CategoryDTO();

    @BeforeEach
    public void setUp() {
        testCategory.setId(1);
        testCategory.setTitle("sneakers");
    }

    @Test
    public void getAllCategories_getListOfAllCategories_listOfCategories() throws Exception {
        List<CategoryDTO> testListOfCategories = new ArrayList<>();
        testListOfCategories.add(testCategory);

        when(categoryService.getAllCategories()).thenReturn(testListOfCategories);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(testListOfCategories)));
    }

    @Test
    public void getAllCategories_listOfCategoriesIsEmpty_throwsExceptionAnd404() throws Exception {
        when(categoryService.getAllCategories()).thenThrow(new ElementNotFoundException("Can't find any categories."));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ElementNotFoundException))
                .andExpect(result -> assertEquals("Can't find any categories.", result.getResolvedException().getMessage()));
    }

    @Test
    public void getAllProductsOfCategory_categoryAndListOfProductsInAMap_hashMapOfCategoryAndProducts() throws Exception {
        ProductDTO testDTO = new ProductDTO();
        testDTO.setId(1);
        testDTO.setTitle("sneakers");
        testDTO.setTargetAudience(TargetAudience.UNISEX);
        testDTO.setDescription("some description");
        testDTO.setPrice(new BigDecimal(300));
        testDTO.setBrand("home");
        ProductPropertiesDTO testProperties = new ProductPropertiesDTO();
        testProperties.setStock(1);
        testProperties.setColor("red");
        testProperties.setSize(40);
        testProperties.setImageLink("/");
        testDTO.setCategory(testCategory);

        CategoryWithProductsDTO result = new CategoryWithProductsDTO();
        result.setId(testCategory.getId());
        result.setTitle(testCategory.getTitle());
        result.getProductList().add(testDTO);

        when((categoryService.getAllProductsByCategoryId(anyLong()))).thenReturn(result);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/{id}", testCategory.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    public void getAllProductsOfCategory_categoryWasNotFound_throwsExceptionAnd404() throws Exception {
        when((categoryService.getAllProductsByCategoryId(anyLong()))).thenThrow(new ElementNotFoundException("Can't find category with id: " + testCategory.getId()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/{id}", testCategory.getId()))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ElementNotFoundException))
                .andExpect(result -> assertEquals("Can't find category with id: " + testCategory.getId(), result.getResolvedException().getMessage()));
    }
}