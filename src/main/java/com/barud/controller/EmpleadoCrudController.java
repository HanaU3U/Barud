package com.barud.controller;

import com.barud.dto.ResponseDtoMapper;
import com.barud.dto.request.EmpleadoRequestDto;
import com.barud.dto.response.EmpleadoResponseDto;
import com.barud.model.enums.EmpleadoEstado;
import com.barud.model.enums.EmpleadoRol;
import com.barud.service.EmpleadoService;
import java.time.LocalDate;
import java.util.List;
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
@RequestMapping("/api/empleados")
public class EmpleadoCrudController {

    private final EmpleadoService empleadoService;

    public EmpleadoCrudController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public List<EmpleadoResponseDto> listar(
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) EmpleadoRol rol,
        @RequestParam(required = false) EmpleadoEstado estado,
        @RequestParam(required = false) LocalDate fechaDesde,
        @RequestParam(required = false) LocalDate fechaHasta
    ) {
        return empleadoService.listarConFiltros(nombre, rol, estado, fechaDesde, fechaHasta).stream()
            .map(ResponseDtoMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDto> obtenerPorId(@PathVariable Integer id) {
        return empleadoService.obtenerPorId(id)
            .map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmpleadoResponseDto crear(@RequestBody EmpleadoRequestDto dto) {
        return ResponseDtoMapper.toDto(empleadoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDto> actualizar(@PathVariable Integer id, @RequestBody EmpleadoRequestDto dto) {
        return empleadoService.actualizar(id, dto)
            .map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!empleadoService.eliminar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
