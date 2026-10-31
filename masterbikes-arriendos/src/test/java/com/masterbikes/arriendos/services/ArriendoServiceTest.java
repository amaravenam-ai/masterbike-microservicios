package com.masterbikes.arriendos.services;

import com.masterbikes.arriendos.models.ArriendoModel;
import com.masterbikes.arriendos.repositories.ArriendoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArriendoServiceTest {

    @Mock
    private ArriendoRepository arriendoRepository;

    @InjectMocks
    private ArriendoService arriendoService;

    private ArriendoModel crearArriendo(Long id, Long idCliente, String estado) {
        ArriendoModel arriendo = new ArriendoModel();
        arriendo.setId(id);
        arriendo.setIdCliente(idCliente);
        arriendo.setIdBicicleta(1L);
        arriendo.setFechaInicio(LocalDate.now());
        arriendo.setFechaFin(LocalDate.now().plusDays(3));
        arriendo.setCosto(new BigDecimal("7500.00"));
        arriendo.setEstadoArriendo(estado);
        return arriendo;
    }

    @Test
    void obtenerTodos_debeRetornarTodosLosArriendos() {
        // Given
        ArriendoModel a1 = crearArriendo(1L, 1L, "activo");
        ArriendoModel a2 = crearArriendo(2L, 2L, "finalizado");
        when(arriendoRepository.findAll()).thenReturn(List.of(a1, a2));

        // When
        List<ArriendoModel> resultado = arriendoService.obtenerTodos();

        // Then
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getEstadoArriendo()).isEqualTo("activo");
        verify(arriendoRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_cuandoExiste_debeRetornarArriendo() {
        // Given
        ArriendoModel arriendo = crearArriendo(1L, 1L, "activo");
        when(arriendoRepository.findById(1L)).thenReturn(Optional.of(arriendo));

        // When
        Optional<ArriendoModel> resultado = arriendoService.obtenerPorId(1L);

        // Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getIdCliente()).isEqualTo(1L);
    }

    @Test
    void obtenerPorId_cuandoNoExiste_debeRetornarVacio() {
        // Given
        when(arriendoRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        Optional<ArriendoModel> resultado = arriendoService.obtenerPorId(99L);

        // Then
        assertThat(resultado).isEmpty();
    }

    @Test
    void guardar_debeAlmacenarArriendo() {
        // Given
        ArriendoModel arriendo = crearArriendo(null, 1L, "activo");
        ArriendoModel guardado = crearArriendo(1L, 1L, "activo");
        when(arriendoRepository.save(any(ArriendoModel.class))).thenReturn(guardado);

        // When
        ArriendoModel resultado = arriendoService.guardar(arriendo);

        // Then
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getCosto()).isEqualByComparingTo(new BigDecimal("7500.00"));
        verify(arriendoRepository, times(1)).save(arriendo);
    }

    @Test
    void estadoArriendo_cuandoEstadoActivo_debeRetornarTrue() {
        // Given
        ArriendoModel arriendo = crearArriendo(1L, 1L, "activo");
        when(arriendoRepository.findById(1L)).thenReturn(Optional.of(arriendo));

        // When
        boolean resultado = arriendoService.estadoArriendo(1L);

        // Then
        assertThat(resultado).isTrue();
    }

    @Test
    void estadoArriendo_cuandoEstadoFinalizado_debeRetornarFalse() {
        // Given
        ArriendoModel arriendo = crearArriendo(1L, 1L, "finalizado");
        when(arriendoRepository.findById(1L)).thenReturn(Optional.of(arriendo));

        // When
        boolean resultado = arriendoService.estadoArriendo(1L);

        // Then
        assertThat(resultado).isFalse();
    }

    @Test
    void estadoArriendo_cuandoNoExiste_debeRetornarFalse() {
        // Given
        when(arriendoRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        boolean resultado = arriendoService.estadoArriendo(99L);

        // Then
        assertThat(resultado).isFalse();
    }
}
