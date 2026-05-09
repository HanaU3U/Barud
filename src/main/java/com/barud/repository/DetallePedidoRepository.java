package com.barud.repository;

import com.barud.model.DetallePedido;
import com.barud.model.enums.DetallePedidoEstado;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class DetallePedidoRepository {

	private final JdbcTemplate jdbcTemplate;

	private final RowMapper<DetallePedido> rowMapper = (rs, rowNum) -> new DetallePedido(
		rs.getInt("id_detalle"),
		rs.getInt("id_pedido"),
		rs.getInt("id_producto"),
		rs.getInt("cantidad"),
		rs.getBigDecimal("precio_unitario"),
		DetallePedidoEstado.fromValue(rs.getString("estado"))
	);

	public DetallePedidoRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Iterable<DetallePedido> findAll() {
		return jdbcTemplate.query(
			"SELECT id_detalle, id_pedido, id_producto, cantidad, precio_unitario, estado FROM detalle_pedido ORDER BY id_detalle",
			rowMapper
		);
	}

	public Optional<DetallePedido> findById(Integer id) {
		List<DetallePedido> result = jdbcTemplate.query(
			"SELECT id_detalle, id_pedido, id_producto, cantidad, precio_unitario, estado FROM detalle_pedido WHERE id_detalle = ?",
			rowMapper,
			id
		);
		return result.stream().findFirst();
	}

	public boolean existsById(Integer id) {
		Integer count = jdbcTemplate.queryForObject(
			"SELECT COUNT(1) FROM detalle_pedido WHERE id_detalle = ?",
			Integer.class,
			id
		);
		return count != null && count > 0;
	}

	public DetallePedido save(DetallePedido detallePedido) {
		if (detallePedido.getIdDetalle() == null) {
			Integer id = jdbcTemplate.queryForObject(
				"INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio_unitario, estado) VALUES (?, ?, ?, ?, ?) RETURNING id_detalle",
				Integer.class,
				detallePedido.getIdPedido(),
				detallePedido.getIdProducto(),
				detallePedido.getCantidad(),
				detallePedido.getPrecioUnitario(),
				detallePedido.getEstado().getDbValue()
			);
			detallePedido.setIdDetalle(id);
			return detallePedido;
		}

		jdbcTemplate.update(
			"UPDATE detalle_pedido SET id_pedido = ?, id_producto = ?, cantidad = ?, precio_unitario = ?, estado = ? WHERE id_detalle = ?",
			detallePedido.getIdPedido(),
			detallePedido.getIdProducto(),
			detallePedido.getCantidad(),
			detallePedido.getPrecioUnitario(),
			detallePedido.getEstado().getDbValue(),
			detallePedido.getIdDetalle()
		);
		return detallePedido;
	}

	public void deleteById(Integer id) {
		jdbcTemplate.update("DELETE FROM detalle_pedido WHERE id_detalle = ?", id);
	}
}
