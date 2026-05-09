package com.barud.controller;

import com.barud.dto.ResponseDtoMapper;
import com.barud.dto.response.DivisionCuentaResponseDto;
import com.barud.model.DivisionCuenta;
import com.barud.repository.DivisionCuentaRepository;
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
@RequestMapping("/api/divisiones-cuenta")
public class DivisionCuentaCrudController {

    private final DivisionCuentaRepository divisionCuentaRepository;

    public DivisionCuentaCrudController(DivisionCuentaRepository divisionCuentaRepository) {
        this.divisionCuentaRepository = divisionCuentaRepository;
    }

    @GetMapping
    public List<DivisionCuentaResponseDto> listar() {
        return StreamSupport.stream(divisionCuentaRepository.findAll().spliterator(), false)
            .map(ResponseDtoMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DivisionCuentaResponseDto> obtenerPorId(@PathVariable Integer id) {
        Optional<DivisionCuenta> divisionCuenta = divisionCuentaRepository.findById(id);
        return divisionCuenta.map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public DivisionCuentaResponseDto crear(@RequestBody DivisionCuenta divisionCuenta) {
        divisionCuenta.setIdDivision(null);
        return ResponseDtoMapper.toDto(divisionCuentaRepository.save(divisionCuenta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DivisionCuentaResponseDto> actualizar(@PathVariable Integer id, @RequestBody DivisionCuenta divisionCuenta) {
        if (!divisionCuentaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        divisionCuenta.setIdDivision(id);
        return ResponseEntity.ok(ResponseDtoMapper.toDto(divisionCuentaRepository.save(divisionCuenta)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!divisionCuentaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        divisionCuentaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
