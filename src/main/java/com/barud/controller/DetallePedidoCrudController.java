package com.barud.controller;

import com.barud.dto.ResponseDtoMapper;
import com.barud.dto.request.DetallePedidoRequestDto;
import com.barud.dto.response.DetallePedidoResponseDto;
import com.barud.service.DetallePedidoService;
import java.util.List;
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

    private final DetallePedidoService detallePedidoService;

    public DetallePedidoCrudController(DetallePedidoService detallePedidoService) {
        this.detallePedidoService = detallePedidoService;
    }

    @GetMapping
    public List<DetallePedidoResponseDto> listar() {
        return detallePedidoService.listarTodos().stream()
            .map(ResponseDtoMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoResponseDto> obtenerPorId(@PathVariable Integer id) {
        return detallePedidoService.obtenerPorId(id)
            .map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public DetallePedidoResponseDto crear(@RequestBody DetallePedidoRequestDto dto) {
        return ResponseDtoMapper.toDto(detallePedidoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedidoResponseDto> actualizar(@PathVariable Integer id, @RequestBody DetallePedidoRequestDto dto) {
        return detallePedidoService.actualizar(id, dto)
            .map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!detallePedidoService.eliminar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
