package com.alkomis.shop.mappers;

import com.alkomis.shop.dto.CategoryDTO;
import com.alkomis.shop.dto.ProductDTO;
import com.alkomis.shop.dto.ProductPropertiesDTO;
import com.alkomis.shop.model.Category;
import com.alkomis.shop.model.Product;
import com.alkomis.shop.model.ProductProperties;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-13T10:52:15+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.1.jar, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO productToProductDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId( product.getId() );
        productDTO.setTitle( product.getTitle() );
        productDTO.setPrice( product.getPrice() );
        productDTO.setDescription( product.getDescription() );
        productDTO.setBrand( product.getBrand() );
        productDTO.setTargetAudience( product.getTargetAudience() );
        productDTO.setProductProperties( propertiesToPropertiesDto( product.getProductProperties() ) );
        productDTO.setCategory( categoryToCategoryDto( product.getCategory() ) );

        return productDTO;
    }

    @Override
    public List<ProductPropertiesDTO> propertiesToPropertiesDto(List<ProductProperties> productProperties) {
        if ( productProperties == null ) {
            return null;
        }

        List<ProductPropertiesDTO> list = new ArrayList<ProductPropertiesDTO>( productProperties.size() );
        for ( ProductProperties productProperties1 : productProperties ) {
            list.add( productPropertiesToProductPropertiesDTO( productProperties1 ) );
        }

        return list;
    }

    @Override
    public Product productDTOToProduct(ProductDTO productDto) {
        if ( productDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setTitle( productDto.getTitle() );
        product.setPrice( productDto.getPrice() );
        product.setDescription( productDto.getDescription() );
        product.setBrand( productDto.getBrand() );
        product.setTargetAudience( productDto.getTargetAudience() );
        product.setProductProperties( propertiesDtoToProperties( productDto.getProductProperties() ) );
        product.setCategory( categoryDtoToCategory( productDto.getCategory() ) );

        return product;
    }

    @Override
    public List<ProductProperties> propertiesDtoToProperties(List<ProductPropertiesDTO> productPropertiesDto) {
        if ( productPropertiesDto == null ) {
            return null;
        }

        List<ProductProperties> list = new ArrayList<ProductProperties>( productPropertiesDto.size() );
        for ( ProductPropertiesDTO productPropertiesDTO : productPropertiesDto ) {
            list.add( productPropertiesDTOToProductProperties( productPropertiesDTO ) );
        }

        return list;
    }

    @Override
    public Category categoryDtoToCategory(CategoryDTO categoryDTO) {
        if ( categoryDTO == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( categoryDTO.getId() );
        category.setTitle( categoryDTO.getTitle() );

        return category;
    }

    @Override
    public CategoryDTO categoryToCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setTitle( category.getTitle() );
        categoryDTO.setId( category.getId() );

        return categoryDTO;
    }

    protected ProductPropertiesDTO productPropertiesToProductPropertiesDTO(ProductProperties productProperties) {
        if ( productProperties == null ) {
            return null;
        }

        ProductPropertiesDTO productPropertiesDTO = new ProductPropertiesDTO();

        productPropertiesDTO.setSize( productProperties.getSize() );
        productPropertiesDTO.setColor( productProperties.getColor() );
        productPropertiesDTO.setImageLink( productProperties.getImageLink() );
        productPropertiesDTO.setStock( productProperties.getStock() );

        return productPropertiesDTO;
    }

    protected ProductProperties productPropertiesDTOToProductProperties(ProductPropertiesDTO productPropertiesDTO) {
        if ( productPropertiesDTO == null ) {
            return null;
        }

        ProductProperties productProperties = new ProductProperties();

        productProperties.setSize( productPropertiesDTO.getSize() );
        productProperties.setColor( productPropertiesDTO.getColor() );
        productProperties.setImageLink( productPropertiesDTO.getImageLink() );
        productProperties.setStock( productPropertiesDTO.getStock() );

        return productProperties;
    }
}
