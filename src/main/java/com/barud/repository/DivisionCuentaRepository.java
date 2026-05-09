package com.barud.repository;

import com.barud.model.DivisionCuenta;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class DivisionCuentaRepository {

	private final JdbcTemplate jdbcTemplate;

	private final RowMapper<DivisionCuenta> rowMapper = (rs, rowNum) -> new DivisionCuenta(
		rs.getInt("id_division"),
		rs.getInt("id_cuenta"),
		rs.getString("descripcion"),
		rs.getBigDecimal("monto")
	);

	public DivisionCuentaRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Iterable<DivisionCuenta> findAll() {
		return jdbcTemplate.query(
			"SELECT id_division, id_cuenta, descripcion, monto FROM division_cuenta ORDER BY id_division",
			rowMapper
		);
	}

	public Optional<DivisionCuenta> findById(Integer id) {
		List<DivisionCuenta> result = jdbcTemplate.query(
			"SELECT id_division, id_cuenta, descripcion, monto FROM division_cuenta WHERE id_division = ?",
			rowMapper,
			id
		);
		return result.stream().findFirst();
	}

	public boolean existsById(Integer id) {
		Integer count = jdbcTemplate.queryForObject(
			"SELECT COUNT(1) FROM division_cuenta WHERE id_division = ?",
			Integer.class,
			id
		);
		return count != null && count > 0;
	}

	public DivisionCuenta save(DivisionCuenta divisionCuenta) {
		if (divisionCuenta.getIdDivision() == null) {
			Integer id = jdbcTemplate.queryForObject(
				"INSERT INTO division_cuenta (id_cuenta, descripcion, monto) VALUES (?, ?, ?) RETURNING id_division",
				Integer.class,
				divisionCuenta.getIdCuenta(),
				divisionCuenta.getDescripcion(),
				divisionCuenta.getMonto()
			);
			divisionCuenta.setIdDivision(id);
			return divisionCuenta;
		}

		jdbcTemplate.update(
			"UPDATE division_cuenta SET id_cuenta = ?, descripcion = ?, monto = ? WHERE id_division = ?",
			divisionCuenta.getIdCuenta(),
			divisionCuenta.getDescripcion(),
			divisionCuenta.getMonto(),
			divisionCuenta.getIdDivision()
		);
		return divisionCuenta;
	}

	public void deleteById(Integer id) {
		jdbcTemplate.update("DELETE FROM division_cuenta WHERE id_division = ?", id);
	}
}
