package com.barud.controller;

import com.barud.dto.ResponseDtoMapper;
import com.barud.dto.request.PagoRequestDto;
import com.barud.dto.response.PagoResponseDto;
import com.barud.service.PagoService;
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
@RequestMapping("/api/pagos")
public class PagoCrudController {

    private final PagoService pagoService;

    public PagoCrudController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public List<PagoResponseDto> listar() {
        return pagoService.listarTodos().stream()
            .map(ResponseDtoMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDto> obtenerPorId(@PathVariable Integer id) {
        return pagoService.obtenerPorId(id)
            .map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public PagoResponseDto crear(@RequestBody PagoRequestDto dto) {
        return ResponseDtoMapper.toDto(pagoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoResponseDto> actualizar(@PathVariable Integer id, @RequestBody PagoRequestDto dto) {
        return pagoService.actualizar(id, dto)
            .map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!pagoService.eliminar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
