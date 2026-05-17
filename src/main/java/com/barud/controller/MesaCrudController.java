package com.barud.controller;

import com.barud.dto.ResponseDtoMapper;
import com.barud.dto.request.MesaRequestDto;
import com.barud.dto.response.MesaResponseDto;
import com.barud.service.MesaService;
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
@RequestMapping("/api/mesas")
public class MesaCrudController {

    private final MesaService mesaService;

    public MesaCrudController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @GetMapping
    public List<MesaResponseDto> listar() {
        return mesaService.listarTodas().stream()
            .map(ResponseDtoMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MesaResponseDto> obtenerPorId(@PathVariable Integer id) {
        return mesaService.obtenerPorId(id)
            .map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public MesaResponseDto crear(@RequestBody MesaRequestDto dto) {
        return ResponseDtoMapper.toDto(mesaService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MesaResponseDto> actualizar(@PathVariable Integer id, @RequestBody MesaRequestDto dto) {
        return mesaService.actualizar(id, dto)
            .map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!mesaService.eliminar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
