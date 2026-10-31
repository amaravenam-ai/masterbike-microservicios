package com.masterbikes.ventas.services;

import com.masterbikes.ventas.models.VentaModel;
import com.masterbikes.ventas.repositories.VentaRepository;
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
class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @InjectMocks
    private VentaService ventaService;

    private VentaModel crearVenta(Long id, Long idCliente, String estado) {
        VentaModel venta = new VentaModel();
        venta.setId(id);
        venta.setIdCliente(idCliente);
        venta.setFechaVenta(LocalDate.now());
        venta.setTotal(new BigDecimal("150000.00"));
        venta.setEstado(estado);
        return venta;
    }

    @Test
    void obtenerTodas_debeRetornarTodasLasVentas() {
        // Given
        VentaModel v1 = crearVenta(1L, 1L, "completada");
        VentaModel v2 = crearVenta(2L, 2L, "pendiente");
        when(ventaRepository.findAll()).thenReturn(List.of(v1, v2));

        // When
        List<VentaModel> resultado = ventaService.obtenerTodas();

        // Then
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getEstado()).isEqualTo("completada");
        verify(ventaRepository, times(1)).findAll();
    }

    @Test
    void obtenerTodas_cuandoNoHayVentas_debeRetornarListaVacia() {
        // Given
        when(ventaRepository.findAll()).thenReturn(List.of());

        // When
        List<VentaModel> resultado = ventaService.obtenerTodas();

        // Then
        assertThat(resultado).isEmpty();
    }

    @Test
    void obtenerPorId_cuandoExiste_debeRetornarVenta() {
        // Given
        VentaModel venta = crearVenta(1L, 1L, "completada");
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));

        // When
        Optional<VentaModel> resultado = ventaService.obtenerPorId(1L);

        // Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getTotal()).isEqualByComparingTo(new BigDecimal("150000.00"));
    }

    @Test
    void obtenerPorId_cuandoNoExiste_debeRetornarVacio() {
        // Given
        when(ventaRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        Optional<VentaModel> resultado = ventaService.obtenerPorId(99L);

        // Then
        assertThat(resultado).isEmpty();
    }

    @Test
    void guardar_debeAlmacenarVentaConTotalCorrecto() {
        // Given
        VentaModel venta = crearVenta(null, 1L, "pendiente");
        VentaModel guardada = crearVenta(1L, 1L, "pendiente");
        when(ventaRepository.save(any(VentaModel.class))).thenReturn(guardada);

        // When
        VentaModel resultado = ventaService.guardar(venta);

        // Then
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getTotal()).isEqualByComparingTo(new BigDecimal("150000.00"));
        verify(ventaRepository, times(1)).save(venta);
    }

    @Test
    void eliminar_debeLlamarDeleteById() {
        // Given
        doNothing().when(ventaRepository).deleteById(1L);

        // When
        ventaService.eliminar(1L);

        // Then
        verify(ventaRepository, times(1)).deleteById(1L);
    }
}
