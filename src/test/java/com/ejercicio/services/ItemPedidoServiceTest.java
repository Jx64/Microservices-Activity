package com.ejercicio.services;

import com.ejercicio.dto.ClienteDto;
import com.ejercicio.dto.ItemPedidoDto;
import com.ejercicio.dto.mapper.ItemPedidoMapper;
import com.ejercicio.entities.Cliente;
import com.ejercicio.entities.ItemPedido;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.repositories.ItemPedidoRepository;
import com.ejercicio.services.impl.ItemPedidoServiceImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ItemPedidoServiceTest {
    @Mock
    private ItemPedidoMapper itemPedidoMapper;

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @InjectMocks
    private ItemPedidoServiceImpl itemPedidoService;

    ItemPedido itemPedido;

    @BeforeEach
    void setUp() {
        itemPedido = ItemPedido.builder()
                .cantidad(2)
                .precioUnitario(10000D)
                .build();
    }

    @Test
    void ItemPedidoService_FindAll_ShouldGetAllItemPedidos() {
        List<ItemPedido> itemsPedidos = new ArrayList<>();
        itemsPedidos.add(itemPedido);

        given(itemPedidoRepository.findAll())
                .willReturn(itemsPedidos);

        List<ItemPedidoDto> itemPedidoDtos = itemPedidoService.findAll();

        assertNotNull(itemPedidoDtos);
    }

    @Test
    void givenItemPedido_whenFindItemPedidoById_thenReturnItemPedido(){
        Long test1 = 1L;

        given(itemPedidoRepository.findById(test1))
                .willReturn(Optional.of(itemPedido));

        ItemPedidoDto itemPedidoDto = new ItemPedidoDto();

        given(itemPedidoMapper.itemPedidoToItemPedidoDto(any(ItemPedido.class)))
                .willReturn(itemPedidoDto);

        ItemPedidoDto result = itemPedidoService.findById(test1);

        assertNotNull(result);
    }

    @Test
    void givenNullOrEmptyItemPedidoId_whenIdNotFound_thenReturnException() {
        given(itemPedidoRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () ->{
            itemPedidoService.findById(any());
        }, "Data wasn't found");
    }

    @Test
    void givenItemPedido_whenUpdateItemPedido_thenUpdateItemPedido() {
        ItemPedido itemPedidoUpdate = ItemPedido.builder()
                .cantidad(4)
                .precioUnitario(21100D)
                .build();

        Long test1 = 1L;

        given(itemPedidoRepository.findById(test1))
                .willReturn(Optional.of(itemPedido));

        assertDoesNotThrow(() -> {
            itemPedidoService.update(test1, itemPedidoUpdate);
        });
    }

    @Test
    void givenAnId_whenCItemPedidoExist_thenDeleteItemPedido() {
        Long clienteId = 1L;

        given(itemPedidoRepo_  sitory.findById(clienteId))
                .willReturn(Optional.of(itemPedido));

        willDoNothing().given(itemPedidoRepository).delete(any());

        itemPedidoService.deleteById(clienteId);

        verify(itemPedidoRepository, times(1)).delete(any());
    }
}