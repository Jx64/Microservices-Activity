    package com.ejercicio.services;
    
    import com.ejercicio.dto.ProductoDto;
    import com.ejercicio.dto.mapper.ProductoMapper;
    import com.ejercicio.entities.Producto;
    import com.ejercicio.exceptions.DataNotFoundException;
    import com.ejercicio.repositories.ProductoRepository;
    import com.ejercicio.services.impl.ProductoServiceImpl;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.mockito.junit.jupiter.MockitoExtension;
    
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;
    
    import static org.junit.jupiter.api.Assertions.*;
    import static org.mockito.BDDMockito.given;
    import static org.mockito.ArgumentMatchers.any;
    import static org.assertj.core.api.Assertions.assertThat;
    import static org.mockito.BDDMockito.willDoNothing;
    import static org.mockito.Mockito.*;

    @ExtendWith(MockitoExtension.class)
    public class ProductoServiceTest {
        @Mock
        private ProductoRepository productoRepository;
    
        @Mock
        private ProductoMapper productoMapper;
    
        @InjectMocks
        private ProductoServiceImpl productoService;
    
        Producto producto;
    
        @BeforeEach
        void setUp(){
            producto = Producto.builder()
                    .id(1L)
                    .nombre("Limon")
                    .price(23.0)
                    .stock(4)
                    .build();
        }
    
        @Test
        void ProductoService_FindAll_ShouldGetAllProductos() {
            List<Producto> productos = new ArrayList<>();
            productos.add(producto);
    
            given(productoRepository.findAll())
                    .willReturn(productos);
    
            List<ProductoDto> productoDtos = productoService.findAll();
    
            assertNotNull(productoDtos);
        }
    
        @Test
        void givenProductoId_whenFindProductoById_thenReturnProducto(){
            Long test1 = 1L;
    
            given(productoRepository.findById(test1))
                    .willReturn(Optional.of(producto));
    
            ProductoDto productoDto = new ProductoDto();
    
            given(productoMapper.productoToProductoDto(any(Producto.class)))
                    .willReturn(productoDto);
    
            ProductoDto result = productoService.findById(test1);
    
            assertNotNull(result);
        }
    
        @Test
        void givenProductoId_whenIdNotFound_thenReturnException() {
            given(productoRepository.findById(any()))
                    .willReturn(Optional.empty());
    
            assertThrows(DataNotFoundException.class, () ->{
                productoService.findById(any());
            }, "Data wasn't found");
        }

        @Test
        void givenProducto_whenUpdateProducto_thenUpdateProducto() {
            Producto productoUpdate = Producto.builder()
                    .nombre("Manzana")
                    .price(23.0)
                    .stock(2)
                    .build();

            Long test1 = 1L;

            given(productoRepository.findById(test1))
                    .willReturn(Optional.of(producto));

            assertDoesNotThrow(() -> {
                productoService.update(test1, productoUpdate);
            });
        }

        @Test
        void givenAnId_whenProductoExist_thenDeleteProducto() {
            Long productoId = 1l;

            given(productoRepository.findById(productoId))
                    .willReturn(Optional.of(producto));

            willDoNothing().given(productoRepository).delete(any());

            productoService.deleteById(productoId);

            verify(productoRepository, times(1)).delete(any());
        }
    }
