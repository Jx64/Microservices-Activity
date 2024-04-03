package com.ejercicio.services;

import com.ejercicio.dto.PedidoDto;
import com.ejercicio.dto.mapper.PedidoMapper;
import com.ejercicio.entities.Pedido;
import com.ejercicio.entities.enums.PedidoStatus;
import com.ejercicio.exceptions.DataNotFoundException;
import com.ejercicio.repositories.PedidoRepository;
import com.ejercicio.services.impl.PedidoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
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
public class PedidoServiceTest {
    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoMapper pedidoMapper;

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    Pedido pedido;

    @BeforeEach
    void setUp(){
        LocalDateTime date = LocalDateTime.of(2024,1,23, 0, 0);

        pedido = Pedido.builder()
                .id(1L)
                .fechaPedido(date)
                .status(PedidoStatus.ENTREGADO)
                .build();
    }

    @Test
    void PedidoService_FindAll_ShouldGetAllPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(pedido);

        given(pedidoRepository.findAll())
                .willReturn(pedidos);

        List<PedidoDto> pedidoDtos = pedidoService.findAll();

        assertNotNull(pedidoDtos);
    }

    @Test
    void givenPedido_whenFindById_thenReturnPedido(){
        Long test1 = 1L;

        given(pedidoRepository.findById(test1))
                .willReturn(Optional.of(pedido));

        PedidoDto pedidoDto = new PedidoDto();

        given(pedidoMapper.pedidoToPedidoDto(any(Pedido.class)))
                .willReturn(pedidoDto);

        PedidoDto result = pedidoService.findById(test1);

        assertNotNull(result);
    }

    @Test
    void givenPedido_whenIdNotFound_thenReturnException() {
        given(pedidoRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () ->{
            pedidoService.findById(any());
        }, "Data wasn't found");
    }

    @Test
    void givenPedido_whenUpdate_thenReturnPedidoUpdated() {
        LocalDateTime new_date = LocalDateTime.of(2024,2,23, 0, 0);
        Pedido pedidoUpdate = Pedido.builder()
                .fechaPedido(new_date)
                .status(PedidoStatus.ENTREGADO)
                .build();

        Long test1 = 1L;

        given(pedidoRepository.findById(test1))
                .willReturn(Optional.of(pedido));

        assertDoesNotThrow(() -> {
            pedidoService.update(test1, pedidoUpdate);
        });
    }

    @Test
    void givenAnId_whenPedidoExist_thenDeletePedido() {
        Long pedidoId = 1L;

        given(pedidoRepository.findById(pedidoId))
                .willReturn(Optional.of(pedido));

        willDoNothing().given(pedidoRepository).delete(any());

        pedidoService.deleteById(pedidoId);

        verify(pedidoRepository, times(1)).delete(any());
    }
}
