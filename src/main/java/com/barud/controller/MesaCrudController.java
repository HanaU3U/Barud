package com.barud.controller;

import com.barud.dto.ResponseDtoMapper;
import com.barud.dto.response.MesaResponseDto;
import com.barud.model.Mesa;
import com.barud.repository.MesaRepository;
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
@RequestMapping("/api/mesas")
public class MesaCrudController {

    private final MesaRepository mesaRepository;

    public MesaCrudController(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    @GetMapping
    public List<MesaResponseDto> listar() {
        return StreamSupport.stream(mesaRepository.findAll().spliterator(), false)
            .map(ResponseDtoMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MesaResponseDto> obtenerPorId(@PathVariable Integer id) {
        Optional<Mesa> mesa = mesaRepository.findById(id);
        return mesa.map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public MesaResponseDto crear(@RequestBody Mesa mesa) {
        mesa.setIdMesa(null);
        return ResponseDtoMapper.toDto(mesaRepository.save(mesa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MesaResponseDto> actualizar(@PathVariable Integer id, @RequestBody Mesa mesa) {
        if (!mesaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        mesa.setIdMesa(id);
        return ResponseEntity.ok(ResponseDtoMapper.toDto(mesaRepository.save(mesa)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!mesaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        mesaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
