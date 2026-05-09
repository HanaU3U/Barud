package com.barud.repository;

import com.barud.model.Producto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductoRepository {

	private final JdbcTemplate jdbcTemplate;

	private final RowMapper<Producto> rowMapper = (rs, rowNum) -> new Producto(
		rs.getInt("id_producto"),
		rs.getString("nombre"),
		rs.getString("tipo"),
		rs.getBigDecimal("precio"),
		rs.getInt("stock")
	);

	public ProductoRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Iterable<Producto> findAll() {
		return jdbcTemplate.query(
			"SELECT id_producto, nombre, tipo, precio, stock FROM producto ORDER BY id_producto",
			rowMapper
		);
	}

	public List<Producto> findByFilters(
		String nombre,
		String tipo,
		BigDecimal minPrecio,
		BigDecimal maxPrecio,
		Integer minStock,
		Boolean disponibles,
		int page,
		int size
	) {
		StringBuilder sql = new StringBuilder(
			"SELECT id_producto, nombre, tipo, precio, stock FROM producto WHERE 1=1"
		);
		List<Object> params = new ArrayList<>();

		if (nombre != null && !nombre.isBlank()) {
			sql.append(" AND LOWER(nombre) LIKE LOWER(?)");
			params.add("%" + nombre + "%");
		}
		if (tipo != null && !tipo.isBlank()) {
			sql.append(" AND tipo = ?");
			params.add(tipo);
		}
		if (minPrecio != null) {
			sql.append(" AND precio >= ?");
			params.add(minPrecio);
		}
		if (maxPrecio != null) {
			sql.append(" AND precio <= ?");
			params.add(maxPrecio);
		}
		if (minStock != null) {
			sql.append(" AND stock >= ?");
			params.add(minStock);
		}
		if (disponibles != null) {
			if (Boolean.TRUE.equals(disponibles)) {
				sql.append(" AND stock > 0");
			} else {
				sql.append(" AND stock = 0");
			}
		}

		sql.append(" ORDER BY id_producto DESC LIMIT ? OFFSET ?");
		params.add(size);
		params.add(page * size);

		return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
	}

	public Optional<Producto> findById(Integer id) {
		List<Producto> result = jdbcTemplate.query(
			"SELECT id_producto, nombre, tipo, precio, stock FROM producto WHERE id_producto = ?",
			rowMapper,
			id
		);
		return result.stream().findFirst();
	}

	public boolean existsById(Integer id) {
		Integer count = jdbcTemplate.queryForObject(
			"SELECT COUNT(1) FROM producto WHERE id_producto = ?",
			Integer.class,
			id
		);
		return count != null && count > 0;
	}

	public Producto save(Producto producto) {
		if (producto.getIdProducto() == null) {
			Integer id = jdbcTemplate.queryForObject(
				"INSERT INTO producto (nombre, tipo, precio, stock) VALUES (?, ?, ?, ?) RETURNING id_producto",
				Integer.class,
				producto.getNombre(),
				producto.getTipo(),
				producto.getPrecio(),
				producto.getStock()
			);
			producto.setIdProducto(id);
			return producto;
		}

		jdbcTemplate.update(
			"UPDATE producto SET nombre = ?, tipo = ?, precio = ?, stock = ? WHERE id_producto = ?",
			producto.getNombre(),
			producto.getTipo(),
			producto.getPrecio(),
			producto.getStock(),
			producto.getIdProducto()
		);
		return producto;
	}

	public void deleteById(Integer id) {
		jdbcTemplate.update("DELETE FROM producto WHERE id_producto = ?", id);
	}
}
