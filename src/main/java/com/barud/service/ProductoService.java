package com.barud.service;

import com.barud.model.Producto;
import com.barud.model.enums.ProductoTipo;
import com.barud.repository.ProductoRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listarConFiltros(
        String nombre,
        ProductoTipo tipo,
        BigDecimal minPrecio,
        BigDecimal maxPrecio,
        Integer minStock,
        Boolean disponibles,
        int page,
        int size
    ) {
        int safePage = Math.max(page, 0);
        int safeSize = size <= 0 ? 10 : Math.min(size, 100);
        return productoRepository.findByFilters(nombre, tipo, minPrecio, maxPrecio, minStock, disponibles, safePage, safeSize);
    }

    public Optional<Producto> obtenerPorId(Integer id) {
        return productoRepository.findById(id);
    }

    public Producto crear(Producto producto) {
        producto.setIdProducto(null);
        return productoRepository.save(producto);
    }

    public Optional<Producto> actualizar(Integer id, Producto producto) {
        if (!productoRepository.existsById(id)) {
            return Optional.empty();
        }
        producto.setIdProducto(id);
        return Optional.of(productoRepository.save(producto));
    }

    public boolean eliminar(Integer id) {
        if (!productoRepository.existsById(id)) {
            return false;
        }
        productoRepository.deleteById(id);
        return true;
    }
}
