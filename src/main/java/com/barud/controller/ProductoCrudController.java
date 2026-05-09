package com.barud.controller;

import com.barud.dto.ResponseDtoMapper;
import com.barud.dto.response.ProductoResponseDto;
import com.barud.model.Producto;
import com.barud.model.enums.ProductoTipo;
import com.barud.service.ProductoService;
import java.math.BigDecimal;
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
@RequestMapping("/api/productos")
public class ProductoCrudController {

    private final ProductoService productoService;

    public ProductoCrudController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<ProductoResponseDto> listar(
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) ProductoTipo tipo,
        @RequestParam(required = false) BigDecimal minPrecio,
        @RequestParam(required = false) BigDecimal maxPrecio,
        @RequestParam(required = false) Integer minStock,
        @RequestParam(required = false) Boolean disponibles,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return productoService
            .listarConFiltros(nombre, tipo, minPrecio, maxPrecio, minStock, disponibles, page, size)
            .stream()
            .map(ResponseDtoMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> obtenerPorId(@PathVariable Integer id) {
        Optional<Producto> producto = productoService.obtenerPorId(id);
        return producto.map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductoResponseDto crear(@RequestBody Producto producto) {
        return ResponseDtoMapper.toDto(productoService.crear(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> actualizar(@PathVariable Integer id, @RequestBody Producto producto) {
        return productoService.actualizar(id, producto)
            .map(ResponseDtoMapper::toDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!productoService.eliminar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
