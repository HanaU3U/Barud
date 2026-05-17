package com.barud.controller;

import com.barud.dto.ResponseDtoMapper;
import com.barud.dto.request.DivisionCuentaRequestDto;
import com.barud.dto.response.DivisionCuentaResponseDto;
import com.barud.service.DivisionCuentaService;
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
@RequestMapping("/api/divisiones-cuenta")
public class DivisionCuentaCrudController {

    private final DivisionCuentaService divisionCuentaService;

    public DivisionCuentaCrudController(DivisionCuentaService divisionCuentaService) {
        this.divisionCuentaService = divisionCuentaService;
    }

    @GetMapping
    public List<DivisionCuentaResponseDto> listar() {
        return divisionCuentaService.listarTodas().stream()
            .map(ResponseDtoMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DivisionCuentaResponseDto> obtenerPorId(@PathVariable Integer id) {
        return divisionCuentaService.obtenerPorId(id)
            .map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public DivisionCuentaResponseDto crear(@RequestBody DivisionCuentaRequestDto dto) {
        return ResponseDtoMapper.toDto(divisionCuentaService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DivisionCuentaResponseDto> actualizar(@PathVariable Integer id, @RequestBody DivisionCuentaRequestDto dto) {
        return divisionCuentaService.actualizar(id, dto)
            .map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!divisionCuentaService.eliminar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
