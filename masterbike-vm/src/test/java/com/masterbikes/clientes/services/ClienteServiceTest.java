package com.masterbikes.clientes.services;

import com.masterbikes.clientes.models.ClienteModel;
import com.masterbikes.clientes.repositories.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private ClienteModel crearCliente(Long id, String rut, String nombre) {
        ClienteModel cliente = new ClienteModel();
        cliente.setId(id);
        cliente.setRut(rut);
        cliente.setNombre(nombre);
        cliente.setApellidos("Apellido Test");
        cliente.setCorreo("test@mail.com");
        cliente.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        return cliente;
    }

    @Test
    void obtenerTodos_debeRetornarListaCompleta() {
        // Given
        ClienteModel c1 = crearCliente(1L, "12345678-9", "Juan");
        ClienteModel c2 = crearCliente(2L, "98765432-1", "Maria");
        when(clienteRepository.findAll()).thenReturn(List.of(c1, c2));

        // When
        List<ClienteModel> resultado = clienteService.obtenerTodos();

        // Then
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Juan");
        assertThat(resultado.get(1).getRut()).isEqualTo("98765432-1");
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void obtenerTodos_cuandoNoHayClientes_debeRetornarListaVacia() {
        // Given
        when(clienteRepository.findAll()).thenReturn(List.of());

        // When
        List<ClienteModel> resultado = clienteService.obtenerTodos();

        // Then
        assertThat(resultado).isEmpty();
    }

    @Test
    void obtenerPorId_cuandoExiste_debeRetornarCliente() {
        // Given
        ClienteModel cliente = crearCliente(1L, "12345678-9", "Juan");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // When
        Optional<ClienteModel> resultado = clienteService.obtenerPorId(1L);

        // Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(1L);
        assertThat(resultado.get().getNombre()).isEqualTo("Juan");
    }

    @Test
    void obtenerPorId_cuandoNoExiste_debeRetornarVacio() {
        // Given
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        Optional<ClienteModel> resultado = clienteService.obtenerPorId(99L);

        // Then
        assertThat(resultado).isEmpty();
    }

    @Test
    void obtenerPorRut_cuandoExiste_debeRetornarCliente() {
        // Given
        ClienteModel cliente = crearCliente(1L, "12345678-9", "Juan");
        when(clienteRepository.findByRut("12345678-9")).thenReturn(Optional.of(cliente));

        // When
        Optional<ClienteModel> resultado = clienteService.obtenerPorRut("12345678-9");

        // Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getRut()).isEqualTo("12345678-9");
    }

    @Test
    void obtenerPorRut_cuandoNoExiste_debeRetornarVacio() {
        // Given
        when(clienteRepository.findByRut("00000000-0")).thenReturn(Optional.empty());

        // When
        Optional<ClienteModel> resultado = clienteService.obtenerPorRut("00000000-0");

        // Then
        assertThat(resultado).isEmpty();
    }

    @Test
    void guardar_debeAlmacenarYRetornarCliente() {
        // Given
        ClienteModel cliente = crearCliente(null, "12345678-9", "Juan");
        ClienteModel clienteGuardado = crearCliente(1L, "12345678-9", "Juan");
        when(clienteRepository.save(any(ClienteModel.class))).thenReturn(clienteGuardado);

        // When
        ClienteModel resultado = clienteService.guardar(cliente);

        // Then
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getRut()).isEqualTo("12345678-9");
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void eliminar_debeLlamarDeleteById() {
        // Given
        Long idAEliminar = 1L;
        doNothing().when(clienteRepository).deleteById(idAEliminar);

        // When
        clienteService.eliminar(idAEliminar);

        // Then
        verify(clienteRepository, times(1)).deleteById(idAEliminar);
    }
}
