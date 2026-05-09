package com.barud.repository;

import com.barud.model.Pago;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PagoRepository {

	private final JdbcTemplate jdbcTemplate;

	private final RowMapper<Pago> rowMapper = (rs, rowNum) -> new Pago(
		rs.getInt("id_pago"),
		rs.getInt("id_cuenta"),
		rs.getString("metodo"),
		rs.getBigDecimal("monto"),
		rs.getTimestamp("fecha") == null ? null : rs.getTimestamp("fecha").toLocalDateTime()
	);

	public PagoRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Iterable<Pago> findAll() {
		return jdbcTemplate.query("SELECT id_pago, id_cuenta, metodo, monto, fecha FROM pago ORDER BY id_pago", rowMapper);
	}

	public Optional<Pago> findById(Integer id) {
		List<Pago> result = jdbcTemplate.query(
			"SELECT id_pago, id_cuenta, metodo, monto, fecha FROM pago WHERE id_pago = ?",
			rowMapper,
			id
		);
		return result.stream().findFirst();
	}

	public boolean existsById(Integer id) {
		Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM pago WHERE id_pago = ?", Integer.class, id);
		return count != null && count > 0;
	}

	public Pago save(Pago pago) {
		if (pago.getIdPago() == null) {
			Integer id = jdbcTemplate.queryForObject(
				"INSERT INTO pago (id_cuenta, metodo, monto, fecha) VALUES (?, ?, ?, ?) RETURNING id_pago",
				Integer.class,
				pago.getIdCuenta(),
				pago.getMetodo(),
				pago.getMonto(),
				pago.getFecha()
			);
			pago.setIdPago(id);
			return pago;
		}

		jdbcTemplate.update(
			"UPDATE pago SET id_cuenta = ?, metodo = ?, monto = ?, fecha = ? WHERE id_pago = ?",
			pago.getIdCuenta(),
			pago.getMetodo(),
			pago.getMonto(),
			pago.getFecha(),
			pago.getIdPago()
		);
		return pago;
	}

	public void deleteById(Integer id) {
		jdbcTemplate.update("DELETE FROM pago WHERE id_pago = ?", id);
	}
}
