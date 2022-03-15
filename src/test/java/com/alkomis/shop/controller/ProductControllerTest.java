package com.alkomis.shop.controller;

import com.alkomis.shop.dto.*;
import com.alkomis.shop.exceptions.ElementNotFoundException;
import com.alkomis.shop.model.enums.TargetAudience;
import com.alkomis.shop.service.ProductService;
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
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    private static final String PATH = "/api/products";
    private static final String PATH_ID = "/api/products/{id}";
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    private ProductDTO testDTO = new ProductDTO();

    @BeforeEach
    public void setUp() {
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

        testDTO.setProductProperties(List.of(testProperties));

        CategoryDTO testCategory = new CategoryDTO();
        testCategory.setId(1);
        testCategory.setTitle("sneakers");

        testDTO.setCategory(testCategory);
    }

    @Test
    public void getAllProducts_getListOfAllProducts_returnsListOfAllProductsAndStatusOk() throws Exception {
        List<ProductDTO> testList = new ArrayList<>();
        testList.add(testDTO);

        when(productService.getAllProducts()).thenReturn(testList);

        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(testList)));
    }

    @Test
    public void getAllProducts_listOfProductsIsEmpty_throwsExceptionAndReturns404() throws Exception {
        when(productService.getAllProducts()).thenThrow(new ElementNotFoundException("Can't find any products."));

        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ElementNotFoundException))
                .andExpect(result -> assertEquals("Can't find any products.", result.getResolvedException().getMessage()));
    }

    @Test
    public void getProductById_getSpecificProductById_productAndStatusOk() throws Exception {
        when(productService.getProductById(testDTO.getId())).thenReturn(testDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(PATH_ID, testDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(testDTO)));
    }

    @Test
    public void getProductById_cantFindSpecificProductById_throwsExceptionAndReturn404() throws Exception {
        when(productService.getProductById(testDTO.getId())).thenThrow(new ElementNotFoundException("Product with id: " + testDTO.getId() + " does not exist."));

        mockMvc.perform(MockMvcRequestBuilders.get(PATH_ID, testDTO.getId()))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ElementNotFoundException))
                .andExpect(result -> assertEquals("Product with id: " + testDTO.getId() + " does not exist.", result.getResolvedException().getMessage()));
    }

    @Test
    public void addNewProduct_saveAndReturnNewProduct_savedProductAndStatusOk() throws Exception {
        when(productService.addNewProduct(testDTO)).thenReturn(testDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void addNewProduct_validationFailed_throwsExceptionAndStatus400() throws Exception {
        testDTO.setTitle(" ");

        when(productService.addNewProduct(testDTO)).thenReturn(testDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Validation failed")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateProductById_updateProduct_statusOk() throws Exception {
        doNothing().when(productService).updateProductById(testDTO.getId(), testDTO);

        mockMvc.perform(MockMvcRequestBuilders.put(PATH_ID, testDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateProductById_cantFindProductForUpdate_throwsExceptionAndReturn404() throws Exception {
        doThrow(new ElementNotFoundException("Product with id: " + testDTO.getId() + " does not exist."))
                .when(productService).updateProductById(testDTO.getId(), testDTO);


        mockMvc.perform(MockMvcRequestBuilders.put(PATH_ID, testDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDTO)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ElementNotFoundException))
                .andExpect(result -> assertEquals("Product with id: " + testDTO.getId() + " does not exist.", result.getResolvedException().getMessage()));
    }

    @Test
    public void updateProductById_validationFailed_throwsExceptionAndReturn400() throws Exception {
        testDTO.setTitle(" ");

        doNothing().when(productService).updateProductById(testDTO.getId(), testDTO);

        mockMvc.perform(MockMvcRequestBuilders.put(PATH_ID, testDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Validation failed")));
    }

    @Test
    public void deleteProductById_deleteProduct_statusOk() throws Exception {
        doNothing().when(productService).updateProductById(testDTO.getId(), testDTO);

        mockMvc.perform(MockMvcRequestBuilders.delete(PATH_ID, testDTO.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProductById_cantFindProductForDeleting_throwsExceptionAndReturn404() throws Exception {
        doThrow(new ElementNotFoundException("Product with id: " + testDTO.getId() + " does not exist."))
                .when(productService).deleteProductById(testDTO.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete(PATH_ID, testDTO.getId()))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ElementNotFoundException))
                .andExpect(result -> assertEquals("Product with id: " + testDTO.getId() + " does not exist.", result.getResolvedException().getMessage()));
    }
}