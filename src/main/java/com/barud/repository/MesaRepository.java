package com.barud.repository;

import com.barud.model.Mesa;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MesaRepository {

	private final JdbcTemplate jdbcTemplate;

	private final RowMapper<Mesa> rowMapper = (rs, rowNum) -> new Mesa(
		rs.getInt("id_mesa"),
		rs.getInt("numero"),
		rs.getInt("capacidad"),
		rs.getString("estado")
	);

	public MesaRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Iterable<Mesa> findAll() {
		return jdbcTemplate.query("SELECT id_mesa, numero, capacidad, estado FROM mesa ORDER BY id_mesa", rowMapper);
	}

	public Optional<Mesa> findById(Integer id) {
		List<Mesa> result = jdbcTemplate.query(
			"SELECT id_mesa, numero, capacidad, estado FROM mesa WHERE id_mesa = ?",
			rowMapper,
			id
		);
		return result.stream().findFirst();
	}

	public boolean existsById(Integer id) {
		Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM mesa WHERE id_mesa = ?", Integer.class, id);
		return count != null && count > 0;
	}

	public Mesa save(Mesa mesa) {
		if (mesa.getIdMesa() == null) {
			Integer id = jdbcTemplate.queryForObject(
				"INSERT INTO mesa (numero, capacidad, estado) VALUES (?, ?, ?) RETURNING id_mesa",
				Integer.class,
				mesa.getNumero(),
				mesa.getCapacidad(),
				mesa.getEstado()
			);
			mesa.setIdMesa(id);
			return mesa;
		}

		jdbcTemplate.update(
			"UPDATE mesa SET numero = ?, capacidad = ?, estado = ? WHERE id_mesa = ?",
			mesa.getNumero(),
			mesa.getCapacidad(),
			mesa.getEstado(),
			mesa.getIdMesa()
		);
		return mesa;
	}

	public int updateEstadoById(Integer idMesa, String estado) {
		return jdbcTemplate.update("UPDATE mesa SET estado = ? WHERE id_mesa = ?", estado, idMesa);
	}

	public void deleteById(Integer id) {
		jdbcTemplate.update("DELETE FROM mesa WHERE id_mesa = ?", id);
	}
}
