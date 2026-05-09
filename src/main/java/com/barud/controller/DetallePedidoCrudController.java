package com.barud.controller;

import com.barud.dto.ResponseDtoMapper;
import com.barud.dto.response.DetallePedidoResponseDto;
import com.barud.model.DetallePedido;
import com.barud.repository.DetallePedidoRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/detalles-pedido")
public class DetallePedidoCrudController {

    private final DetallePedidoRepository detallePedidoRepository;

    public DetallePedidoCrudController(DetallePedidoRepository detallePedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
    }

    @GetMapping
    public List<DetallePedidoResponseDto> listar() {
        return StreamSupport.stream(detallePedidoRepository.findAll().spliterator(), false)
            .map(ResponseDtoMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoResponseDto> obtenerPorId(@PathVariable Integer id) {
        Optional<DetallePedido> detallePedido = detallePedidoRepository.findById(id);
        return detallePedido.map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public DetallePedidoResponseDto crear(@RequestBody DetallePedido detallePedido) {
        detallePedido.setIdDetalle(null);
        return ResponseDtoMapper.toDto(detallePedidoRepository.save(detallePedido));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedidoResponseDto> actualizar(@PathVariable Integer id, @RequestBody DetallePedido detallePedido) {
        if (!detallePedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        detallePedido.setIdDetalle(id);
        return ResponseEntity.ok(ResponseDtoMapper.toDto(detallePedidoRepository.save(detallePedido)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!detallePedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        detallePedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
