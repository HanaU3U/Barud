package com.barud.repository;

import com.barud.model.Cuenta;
import com.barud.model.enums.CuentaEstado;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CuentaRepository {

	private final JdbcTemplate jdbcTemplate;

	private final RowMapper<Cuenta> rowMapper = (rs, rowNum) -> new Cuenta(
		rs.getInt("id_cuenta"),
		rs.getInt("id_pedido"),
		rs.getBigDecimal("subtotal"),
		rs.getBigDecimal("impuestos"),
		rs.getBigDecimal("total"),
		CuentaEstado.fromValue(rs.getString("estado"))
	);

	public CuentaRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Iterable<Cuenta> findAll() {
		return jdbcTemplate.query(
			"SELECT id_cuenta, id_pedido, subtotal, impuestos, total, estado FROM cuenta ORDER BY id_cuenta",
			rowMapper
		);
	}

	public List<Cuenta> findByFilters(
		Integer idPedido,
		CuentaEstado estado,
		BigDecimal minTotal,
		BigDecimal maxTotal,
		int page,
		int size
	) {
		StringBuilder sql = new StringBuilder(
			"SELECT id_cuenta, id_pedido, subtotal, impuestos, total, estado FROM cuenta WHERE 1=1"
		);
		List<Object> params = new ArrayList<>();

		if (idPedido != null) {
			sql.append(" AND id_pedido = ?");
			params.add(idPedido);
		}
		if (estado != null) {
			sql.append(" AND estado = ?");
			params.add(estado.getDbValue());
		}
		if (minTotal != null) {
			sql.append(" AND total >= ?");
			params.add(minTotal);
		}
		if (maxTotal != null) {
			sql.append(" AND total <= ?");
			params.add(maxTotal);
		}

		sql.append(" ORDER BY id_cuenta LIMIT ? OFFSET ?");
		params.add(size);
		params.add(page * size);

		return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
	}

	public Optional<Cuenta> findById(Integer id) {
		List<Cuenta> result = jdbcTemplate.query(
			"SELECT id_cuenta, id_pedido, subtotal, impuestos, total, estado FROM cuenta WHERE id_cuenta = ?",
			rowMapper,
			id
		);
		return result.stream().findFirst();
	}

	public Optional<Cuenta> findByIdPedido(Integer idPedido) {
		List<Cuenta> result = jdbcTemplate.query(
			"SELECT id_cuenta, id_pedido, subtotal, impuestos, total, estado FROM cuenta WHERE id_pedido = ?",
			rowMapper,
			idPedido
		);
		return result.stream().findFirst();
	}

	public boolean existsById(Integer id) {
		Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM cuenta WHERE id_cuenta = ?", Integer.class, id);
		return count != null && count > 0;
	}

	public Cuenta save(Cuenta cuenta) {
		if (cuenta.getIdCuenta() == null) {
			Integer id = jdbcTemplate.queryForObject(
				"INSERT INTO cuenta (id_pedido, subtotal, impuestos, total, estado) VALUES (?, ?, ?, ?, ?) RETURNING id_cuenta",
				Integer.class,
				cuenta.getIdPedido(),
				cuenta.getSubtotal(),
				cuenta.getImpuestos(),
				cuenta.getTotal(),
				cuenta.getEstado().getDbValue()
			);
			cuenta.setIdCuenta(id);
			return cuenta;
		}

		jdbcTemplate.update(
			"UPDATE cuenta SET id_pedido = ?, subtotal = ?, impuestos = ?, total = ?, estado = ? WHERE id_cuenta = ?",
			cuenta.getIdPedido(),
			cuenta.getSubtotal(),
			cuenta.getImpuestos(),
			cuenta.getTotal(),
			cuenta.getEstado().getDbValue(),
			cuenta.getIdCuenta()
		);
		return cuenta;
	}

	public int updateEstadoById(Integer idCuenta, CuentaEstado estado) {
		return jdbcTemplate.update("UPDATE cuenta SET estado = ? WHERE id_cuenta = ?", estado.getDbValue(), idCuenta);
	}

	public void deleteById(Integer id) {
		jdbcTemplate.update("DELETE FROM cuenta WHERE id_cuenta = ?", id);
	}
}
