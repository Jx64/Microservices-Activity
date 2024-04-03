package com.ejercicio.services.impl;

import com.ejercicio.dto.ProductoDto;
import com.ejercicio.dto.mapper.ProductoMapper;
import com.ejercicio.exceptions.CannotSaveException;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.services.ProductoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    public ProductoServiceImpl(ProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }


    @Override
    public List<ProductoDto> findAll() throws DataNotFoundException {
        List<Producto> dataList = productoRepository.findAll();

        if (dataList.isEmpty()) {
            throw new DataNotFoundException("There's not products");
        }

        return dataList.stream()
                .map(productoMapper::productoToProductoDto)
                .toList();
    }

    @Override
    public ProductoDto findById(Long id) throws DataNotFoundException {
        Producto entityData = productoRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product wasn't found"));

        return productoMapper.productoToProductoDto(entityData);
    }

    @Override
    public ProductoDto save(Producto entity) throws CannotSaveException {
        if(entity == null){
            throw new CannotSaveException("Invalid product");
        }
        return productoMapper.productoToProductoDto(productoRepository.save(entity));
    }

    @Override
    public ProductoDto update(Long id, Producto e) throws DataNotFoundException {
        Producto entityData = productoRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product wasn't found"));

        BeanUtils.copyProperties(e, entityData);
        productoRepository.save(entityData);

        return productoMapper.productoToProductoDto(entityData);
    }

    @Override
    public void deleteById(Long id) throws DataNotFoundException {
        Producto e = productoRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product wasn't found"));

        productoRepository.delete(e);
    }

    @Override
    public List<ProductoDto> findBySearchTerm(String searchTerm) throws DataNotFoundException {
        List<Producto> dataList = productoRepository.findBySearchTerm(searchTerm);

        if (dataList.isEmpty()) {
            throw new DataNotFoundException("There's not products");
        }

        return dataList.stream()
                .map(productoMapper::productoToProductoDto)
                .toList();
    }

    @Override
    public List<ProductoDto> findByHaveStock() throws DataNotFoundException {
        List<Producto> dataList = productoRepository.findByHaveStock();

        if (dataList.isEmpty()) {
            throw new DataNotFoundException("There's not products");
        }

        return dataList.stream()
                .map(productoMapper::productoToProductoDto)
                .toList();
    }

    @Override
    public List<ProductoDto> findByPriceLessThanAndHaveStock(Double price) throws DataNotFoundException {
        List<Producto> dataList = productoRepository.findByPriceLessThanAndHaveStock(price);

        if (dataList.isEmpty()) {
            throw new DataNotFoundException("There's not products");
        }

        return dataList.stream()
                .map(productoMapper::productoToProductoDto)
                .toList();
    }
}