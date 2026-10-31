package com.masterbikes.inventario.services;

import com.masterbikes.inventario.models.InventarioModel;
import com.masterbikes.inventario.repositories.InventarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;

    private InventarioModel crearInventario(Long id, String producto, int stock, int stockMinimo) {
        InventarioModel item = new InventarioModel();
        item.setId(id);
        item.setNombreProducto(producto);
        item.setCategoria("Accesorios");
        item.setStock(stock);
        item.setStockMinimo(stockMinimo);
        return item;
    }

    @Test
    void obtenerTodos_debeRetornarTodosLosProductos() {
        // Given
        InventarioModel i1 = crearInventario(1L, "Casco MTB", 10, 3);
        InventarioModel i2 = crearInventario(2L, "Guantes", 5, 2);
        when(inventarioRepository.findAll()).thenReturn(List.of(i1, i2));

        // When
        List<InventarioModel> resultado = inventarioService.obtenerTodos();

        // Then
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNombreProducto()).isEqualTo("Casco MTB");
        verify(inventarioRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_cuandoExiste_debeRetornarProducto() {
        // Given
        InventarioModel item = crearInventario(1L, "Casco MTB", 10, 3);
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(item));

        // When
        Optional<InventarioModel> resultado = inventarioService.obtenerPorId(1L);

        // Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getStock()).isEqualTo(10);
        assertThat(resultado.get().getStockMinimo()).isEqualTo(3);
    }

    @Test
    void obtenerPorId_cuandoNoExiste_debeRetornarVacio() {
        // Given
        when(inventarioRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        Optional<InventarioModel> resultado = inventarioService.obtenerPorId(99L);

        // Then
        assertThat(resultado).isEmpty();
    }

    @Test
    void guardar_debeAlmacenarProducto() {
        // Given
        InventarioModel item = crearInventario(null, "Casco MTB", 10, 3);
        InventarioModel guardado = crearInventario(1L, "Casco MTB", 10, 3);
        when(inventarioRepository.save(any(InventarioModel.class))).thenReturn(guardado);

        // When
        InventarioModel resultado = inventarioService.guardar(item);

        // Then
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombreProducto()).isEqualTo("Casco MTB");
        verify(inventarioRepository, times(1)).save(item);
    }

    @Test
    void guardar_debeValidarQueStockNoSeaNegativo() {
        // Given
        InventarioModel item = crearInventario(null, "Luces LED", 0, 2);
        InventarioModel guardado = crearInventario(1L, "Luces LED", 0, 2);
        when(inventarioRepository.save(any(InventarioModel.class))).thenReturn(guardado);

        // When
        InventarioModel resultado = inventarioService.guardar(item);

        // Then
        assertThat(resultado.getStock()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void eliminar_debeLlamarDeleteById() {
        // Given
        doNothing().when(inventarioRepository).deleteById(1L);

        // When
        inventarioService.eliminar(1L);

        // Then
        verify(inventarioRepository, times(1)).deleteById(1L);
    }
}
