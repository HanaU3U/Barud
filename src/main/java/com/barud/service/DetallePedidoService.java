package com.barud.service;

import com.barud.dto.request.DetallePedidoRequestDto;
import com.barud.model.DetallePedido;
import com.barud.repository.DetallePedidoRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

@Service
public class DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;

    public DetallePedidoService(DetallePedidoRepository detallePedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
    }

    public List<DetallePedido> listarTodos() {
        return StreamSupport.stream(detallePedidoRepository.findAll().spliterator(), false).toList();
    }

    public Optional<DetallePedido> obtenerPorId(Integer id) {
        return detallePedidoRepository.findById(id);
    }

    public DetallePedido crear(DetallePedidoRequestDto dto) {
        DetallePedido detalle = new DetallePedido(
            null,
            dto.idPedido(),
            dto.idProducto(),
            dto.cantidad(),
            dto.precioUnitario(),
            dto.estado()
        );
        return detallePedidoRepository.save(detalle);
    }

    public Optional<DetallePedido> actualizar(Integer id, DetallePedidoRequestDto dto) {
        if (!detallePedidoRepository.existsById(id)) {
            return Optional.empty();
        }
        DetallePedido detalle = new DetallePedido(
            id,
            dto.idPedido(),
            dto.idProducto(),
            dto.cantidad(),
            dto.precioUnitario(),
            dto.estado()
        );
        return Optional.of(detallePedidoRepository.save(detalle));
    }

    public boolean eliminar(Integer id) {
        if (!detallePedidoRepository.existsById(id)) {
            return false;
        }
        detallePedidoRepository.deleteById(id);
        return true;
    }
}
