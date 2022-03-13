package com.alkomis.shop.mappers;

import com.alkomis.shop.dto.CategoryDTO;
import com.alkomis.shop.dto.ProductDTO;
import com.alkomis.shop.dto.ProductPropertiesDTO;
import com.alkomis.shop.model.Category;
import com.alkomis.shop.model.Product;
import com.alkomis.shop.model.ProductProperties;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Interface contains conversion configuration for mapping {@link Product} and {@link ProductDTO} classes.
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    /**
     * Converts object of {@link Product} class into {@link ProductDTO} object.
     *
     * @param product {@link Product} entity for conversion.
     * @return DTO of provided object.
     */
    ProductDTO productToProductDto(Product product);

    /**
     * Converts object of {@link ProductProperties} class into {@link ProductPropertiesDTO} object.
     *
     * @param productProperties {@link ProductProperties} entity for conversion.
     * @return DTO of provided properties.
     */
    List<ProductPropertiesDTO> propertiesToPropertiesDto(List<ProductProperties> productProperties);

    /**
     * Converts object of {@link ProductDTO} class into {@link Product} object.
     *
     * @param productDto DTO for conversion into entity.
     * @return {@link Product} entity.
     */
    @Mapping(target = "archive", ignore = true)
    @Mapping(target = "id", ignore = true)
    Product productDTOToProduct(ProductDTO productDto);

    /**
     * Converts object of {@link ProductPropertiesDTO} class into {@link ProductProperties} object.
     *
     * @param productPropertiesDto DTO for conversion into entity.
     * @return {@link ProductProperties} entity.
     */
    @Mapping(target = "id", ignore = true)
    List<ProductProperties> propertiesDtoToProperties(List<ProductPropertiesDTO> productPropertiesDto);

    /**
     * Converts object of {@link CategoryDTO} class into {@link Category} object.
     *
     * @param categoryDTO DTO for conversion into entity.
     * @return {@link Category} entity.
     */
    @Mapping(target = "product", ignore = true)
    Category categoryDtoToCategory(CategoryDTO categoryDTO);

    /**
     * Converts object of {@link Category} class into {@link CategoryDTO} object.
     *
     * @param category {@link Category} entity for conversion.
     * @return DTO of provided entity.
     */
    CategoryDTO categoryToCategoryDto(Category category);
}
