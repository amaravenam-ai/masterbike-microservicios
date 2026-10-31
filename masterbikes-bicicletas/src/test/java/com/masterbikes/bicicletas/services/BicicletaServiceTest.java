package com.masterbikes.bicicletas.services;

import com.masterbikes.bicicletas.models.BicicletaModel;
import com.masterbikes.bicicletas.repositories.BicicletaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BicicletaServiceTest {

    @Mock
    private BicicletaRepository bicicletaRepository;

    @InjectMocks
    private BicicletaService bicicletaService;

    private BicicletaModel crearBicicleta(Long id, String codigo, String estado) {
        BicicletaModel bicicleta = new BicicletaModel();
        bicicleta.setId(id);
        bicicleta.setCodigo(codigo);
        bicicleta.setTipo("Mountain Bike");
        bicicleta.setEstado(estado);
        bicicleta.setTarifaHora(new BigDecimal("2500.00"));
        return bicicleta;
    }

    @Test
    void obtenerTodos_debeRetornarTodasLasBicicletas() {
        // Given
        BicicletaModel b1 = crearBicicleta(1L, "BIC-001", "disponible");
        BicicletaModel b2 = crearBicicleta(2L, "BIC-002", "arrendada");
        when(bicicletaRepository.findAll()).thenReturn(List.of(b1, b2));

        // When
        List<BicicletaModel> resultado = bicicletaService.obtenerTodas();

        // Then
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getCodigo()).isEqualTo("BIC-001");
        verify(bicicletaRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_cuandoExiste_debeRetornarBicicleta() {
        // Given
        BicicletaModel bicicleta = crearBicicleta(1L, "BIC-001", "disponible");
        when(bicicletaRepository.findById(1L)).thenReturn(Optional.of(bicicleta));

        // When
        Optional<BicicletaModel> resultado = bicicletaService.obtenerPorId(1L);

        // Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getCodigo()).isEqualTo("BIC-001");
        assertThat(resultado.get().getEstado()).isEqualTo("disponible");
    }

    @Test
    void obtenerPorId_cuandoNoExiste_debeRetornarVacio() {
        // Given
        when(bicicletaRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        Optional<BicicletaModel> resultado = bicicletaService.obtenerPorId(99L);

        // Then
        assertThat(resultado).isEmpty();
    }

    @Test
    void guardar_debeAlmacenarBicicletaConTarifaCorrecta() {
        // Given
        BicicletaModel bicicleta = crearBicicleta(null, "BIC-003", "disponible");
        BicicletaModel guardada = crearBicicleta(3L, "BIC-003", "disponible");
        when(bicicletaRepository.save(any(BicicletaModel.class))).thenReturn(guardada);

        // When
        BicicletaModel resultado = bicicletaService.guardar(bicicleta);

        // Then
        assertThat(resultado.getId()).isEqualTo(3L);
        assertThat(resultado.getTarifaHora()).isEqualByComparingTo(new BigDecimal("2500.00"));
        verify(bicicletaRepository, times(1)).save(bicicleta);
    }

    @Test
    void eliminar_debeLlamarDeleteById() {
        // Given
        doNothing().when(bicicletaRepository).deleteById(1L);

        // When
        bicicletaService.eliminar(1L);

        // Then
        verify(bicicletaRepository, times(1)).deleteById(1L);
    }
}
