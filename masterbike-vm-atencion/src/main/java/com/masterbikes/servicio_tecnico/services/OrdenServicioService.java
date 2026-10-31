package com.masterbikes.servicio_tecnico.services;

import com.masterbikes.servicio_tecnico.client.ClienteClient;
import com.masterbikes.servicio_tecnico.dtos.request.OrdenServicioRequest;
import com.masterbikes.servicio_tecnico.dtos.response.ClienteResponse;
import com.masterbikes.servicio_tecnico.dtos.response.OrdenServicioResponse;
import com.masterbikes.servicio_tecnico.exceptions.NotFoundException;
import com.masterbikes.servicio_tecnico.exceptions.RemoteServiceException;
import com.masterbikes.servicio_tecnico.models.OrdenServicioModel;
import com.masterbikes.servicio_tecnico.repositories.OrdenServicioRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrdenServicioService {

    private final OrdenServicioRepository ordenServicioRepository;
    private final ClienteClient clienteClient;

    public OrdenServicioService(OrdenServicioRepository ordenServicioRepository, ClienteClient clienteClient) {
        this.ordenServicioRepository = ordenServicioRepository;
        this.clienteClient = clienteClient;
    }

    public List<OrdenServicioResponse> obtenerTodas() {
        return ordenServicioRepository.findAll()
                .stream()
                .map(this::mapToResponseConCliente)
                .toList();
    }

    public OrdenServicioResponse obtenerPorId(Long id) {
        OrdenServicioModel ordenServicio = ordenServicioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe la orden de servicio con id: " + id));

        return mapToResponseConCliente(ordenServicio);
    }

    public List<OrdenServicioResponse> obtenerPorCliente(Long idCliente) {
        return ordenServicioRepository.findByIdCliente(idCliente)
                .stream()
                .map(this::mapToResponseConCliente)
                .toList();
    }

    public OrdenServicioResponse guardar(OrdenServicioRequest request) {
        ClienteResponse cliente = obtenerClienteDesdeServicio(request.getIdCliente());

        OrdenServicioModel ordenServicio = new OrdenServicioModel();
        ordenServicio.setFechaIngreso(request.getFechaIngreso());
        ordenServicio.setHoraIngreso(request.getHoraIngreso());
        ordenServicio.setCosto(request.getCosto());
        ordenServicio.setIdCliente(request.getIdCliente());
        ordenServicio.setProblema(request.getProblema());

        OrdenServicioModel guardada = ordenServicioRepository.save(ordenServicio);

        return mapToResponse(guardada, cliente);
    }

    public OrdenServicioResponse actualizar(Long id, OrdenServicioRequest request) {
        OrdenServicioModel ordenServicio = ordenServicioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe la orden de servicio con id: " + id));

        ClienteResponse cliente = obtenerClienteDesdeServicio(request.getIdCliente());

        ordenServicio.setFechaIngreso(request.getFechaIngreso());
        ordenServicio.setHoraIngreso(request.getHoraIngreso());
        ordenServicio.setCosto(request.getCosto());
        ordenServicio.setIdCliente(request.getIdCliente());
        ordenServicio.setProblema(request.getProblema());

        OrdenServicioModel actualizada = ordenServicioRepository.save(ordenServicio);

        return mapToResponse(actualizada, cliente);
    }

    public void eliminar(Long id) {
        OrdenServicioModel ordenServicio = ordenServicioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe la orden de servicio con id: " + id));

        ordenServicioRepository.delete(ordenServicio);
    }

    private ClienteResponse obtenerClienteDesdeServicio(Long idCliente) {
        try {
            return clienteClient.obtenerClientePorId(idCliente);
        } catch (FeignException.NotFound e) {
            throw new NotFoundException("No existe el cliente con id: " + idCliente);
        } catch (FeignException e) {
            throw new RemoteServiceException("Error al comunicarse con el microservicio de clientes");
        }
    }

    private OrdenServicioResponse mapToResponseConCliente(OrdenServicioModel ordenServicio) {
        ClienteResponse cliente = obtenerClienteDesdeServicio(ordenServicio.getIdCliente());
        return mapToResponse(ordenServicio, cliente);
    }

    private OrdenServicioResponse mapToResponse(OrdenServicioModel ordenServicio, ClienteResponse cliente) {
        return OrdenServicioResponse.builder()
                .id(ordenServicio.getId())
                .fechaIngreso(ordenServicio.getFechaIngreso())
                .horaIngreso(ordenServicio.getHoraIngreso())
                .costo(ordenServicio.getCosto())
                .idCliente(ordenServicio.getIdCliente())
                .problema(ordenServicio.getProblema())
                .cliente(cliente)
                .build();
    }
}
