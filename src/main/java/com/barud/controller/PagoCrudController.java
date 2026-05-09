package com.barud.controller;

import com.barud.dto.ResponseDtoMapper;
import com.barud.dto.response.PagoResponseDto;
import com.barud.model.Pago;
import com.barud.repository.PagoRepository;
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
@RequestMapping("/api/pagos")
public class PagoCrudController {

    private final PagoRepository pagoRepository;

    public PagoCrudController(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @GetMapping
    public List<PagoResponseDto> listar() {
        return StreamSupport.stream(pagoRepository.findAll().spliterator(), false)
            .map(ResponseDtoMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDto> obtenerPorId(@PathVariable Integer id) {
        Optional<Pago> pago = pagoRepository.findById(id);
        return pago.map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public PagoResponseDto crear(@RequestBody Pago pago) {
        pago.setIdPago(null);
        return ResponseDtoMapper.toDto(pagoRepository.save(pago));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoResponseDto> actualizar(@PathVariable Integer id, @RequestBody Pago pago) {
        if (!pagoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pago.setIdPago(id);
        return ResponseEntity.ok(ResponseDtoMapper.toDto(pagoRepository.save(pago)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!pagoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pagoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
