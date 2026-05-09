package com.barud.controller;

import com.barud.dto.ResponseDtoMapper;
import com.barud.dto.response.CuentaResponseDto;
import com.barud.model.Cuenta;
import com.barud.model.enums.CuentaEstado;
import com.barud.service.CuentaService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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
@RequestMapping("/api/cuentas")
public class CuentaCrudController {

    private final CuentaService cuentaService;

    public CuentaCrudController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping
    public List<CuentaResponseDto> listar(
        @RequestParam(required = false) Integer idPedido,
        @RequestParam(required = false) CuentaEstado estado,
        @RequestParam(required = false) BigDecimal minTotal,
        @RequestParam(required = false) BigDecimal maxTotal,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return cuentaService.listarConFiltros(idPedido, estado, minTotal, maxTotal, page, size).stream()
            .map(ResponseDtoMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponseDto> obtenerPorId(@PathVariable Integer id) {
        Optional<Cuenta> cuenta = cuentaService.obtenerPorId(id);
        return cuenta.map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Cuenta cuenta) {
        try {
            return ResponseEntity.ok(ResponseDtoMapper.toDto(cuentaService.crear(cuenta)));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(409).body(Map.of("message", ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Cuenta cuenta) {
        try {
            return cuentaService.actualizar(id, cuenta)
                .map(ResponseDtoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(409).body(Map.of("message", ex.getMessage()));
        }
    }

    @PutMapping("/{id}/cerrar")
    public ResponseEntity<CuentaResponseDto> cerrarCuentaYCerrarPedido(@PathVariable Integer id) {
        return cuentaService.cerrarCuentaYCerrarPedido(id)
            .map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!cuentaService.eliminar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
