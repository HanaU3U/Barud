package com.barud.controller;

import com.barud.dto.ResponseDtoMapper;
import com.barud.dto.response.PedidoResponseDto;
import com.barud.model.Pedido;
import com.barud.service.PedidoService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoCrudController {

    private final PedidoService pedidoService;

    public PedidoCrudController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<PedidoResponseDto> listar(
        @RequestParam(required = false) Integer idMesa,
        @RequestParam(required = false) Integer idMesero,
        @RequestParam(required = false) String estado,
        @RequestParam(required = false) LocalDateTime fechaDesde,
        @RequestParam(required = false) LocalDateTime fechaHasta,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return pedidoService.listarConFiltros(idMesa, idMesero, estado, fechaDesde, fechaHasta, page, size).stream()
            .map(ResponseDtoMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> obtenerPorId(@PathVariable Integer id) {
        Optional<Pedido> pedido = pedidoService.obtenerPorId(id);
        return pedido.map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public PedidoResponseDto crear(@RequestBody Pedido pedido) {
        return ResponseDtoMapper.toDto(pedidoService.crear(pedido));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> actualizar(@PathVariable Integer id, @RequestBody Pedido pedido) {
        return pedidoService.actualizar(id, pedido)
            .map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/cerrar")
    public ResponseEntity<PedidoResponseDto> cerrarPedidoYLiberarMesa(@PathVariable Integer id) {
        return pedidoService.cerrarPedidoYLiberarMesa(id)
            .map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!pedidoService.eliminar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
