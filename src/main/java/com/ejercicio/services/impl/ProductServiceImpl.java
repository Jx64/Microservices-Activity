package com.ejercicio.services.impl;

import com.ejercicio.dto.ProductDto;
import com.ejercicio.entities.Product;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.mapper.ProductMapper;
import com.ejercicio.repositories.ProductRepository;
import com.ejercicio.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDto> findAll() {
        List<Product> dataList = productRepository.findAll();
        return dataList.stream().map(productMapper::productToProductDto).toList();
    }

    @Override
    public ProductDto findById(Long id) {
        Product data = productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data wasn't found"));

        return productMapper.productToProductDto(data);
    }

    @Override
    public ProductDto save(ProductDto entityDto) {
        Product entity = productMapper.productDtoToProduct(entityDto);
        return productMapper.productToProductDto(productRepository.save(entity));
    }

    @Override
    public ProductDto update(Long id, ProductDto entityDto) {
        Product entity = productMapper.productDtoToProduct(entityDto);
        Product data = productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data wasn't found"));
        Product entityUpdate = data;
        data = productRepository.save(entity);
        return productMapper.productToProductDto(data);
    }

    @Override
    public void deleteById(Long id) {
        Product entity = productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data wasn't found"));

        productRepository.delete(entity);
    }

    @Override
    public List<ProductDto> search(String searchTerm) {
        List<Product> dataList = productRepository.findBySearchTerm(searchTerm);

        return dataList.stream().map(productMapper::productToProductDto).toList();
    }

    @Override
    public List<ProductDto> haveStock() {
        List<Product> dataList = productRepository.findByHaveStock();

        return dataList.stream().map(productMapper::productToProductDto).toList();
    }

    @Override
    public List<ProductDto> searchForMaxPrice(Double price) {
        List<Product> dataList = productRepository.findByPriceLessThanAndHaveStock(price);

        return dataList.stream().map(productMapper::productToProductDto).toList();
    }
}
