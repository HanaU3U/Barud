package com.barud.controller;

import com.barud.dto.ResponseDtoMapper;
import com.barud.dto.response.EmpleadoResponseDto;
import com.barud.model.Empleado;
import com.barud.repository.EmpleadoRepository;
import java.time.LocalDate;
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
@RequestMapping("/api/empleados")
public class EmpleadoCrudController {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoCrudController(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @GetMapping
    public List<EmpleadoResponseDto> listar(
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) String rol,
        @RequestParam(required = false) String estado,
        @RequestParam(required = false) LocalDate fechaDesde,
        @RequestParam(required = false) LocalDate fechaHasta
    ) {
        return empleadoRepository.findByFilters(nombre, rol, estado, fechaDesde, fechaHasta).stream()
            .map(ResponseDtoMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDto> obtenerPorId(@PathVariable Integer id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        return empleado.map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmpleadoResponseDto crear(@RequestBody Empleado empleado) {
        empleado.setIdEmpleado(null);
        return ResponseDtoMapper.toDto(empleadoRepository.save(empleado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDto> actualizar(@PathVariable Integer id, @RequestBody Empleado empleado) {
        if (!empleadoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        empleado.setIdEmpleado(id);
        return ResponseEntity.ok(ResponseDtoMapper.toDto(empleadoRepository.save(empleado)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!empleadoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        empleadoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
