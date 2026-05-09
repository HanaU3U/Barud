package com.barud.repository;

import com.barud.model.Pedido;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PedidoRepository {

	private final JdbcTemplate jdbcTemplate;

	private final RowMapper<Pedido> rowMapper = (rs, rowNum) -> new Pedido(
		rs.getInt("id_pedido"),
		rs.getInt("id_mesa"),
		rs.getInt("id_mesero"),
		rs.getTimestamp("fecha_hora") == null ? null : rs.getTimestamp("fecha_hora").toLocalDateTime(),
		rs.getInt("numero_personas"),
		rs.getString("estado")
	);

	public PedidoRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Iterable<Pedido> findAll() {
		return jdbcTemplate.query(
			"SELECT id_pedido, id_mesa, id_mesero, fecha_hora, numero_personas, estado FROM pedido ORDER BY id_pedido",
			rowMapper
		);
	}

	public List<Pedido> findByFilters(
		Integer idMesa,
		Integer idMesero,
		String estado,
		LocalDateTime fechaDesde,
		LocalDateTime fechaHasta,
		int page,
		int size
	) {
		StringBuilder sql = new StringBuilder(
			"SELECT id_pedido, id_mesa, id_mesero, fecha_hora, numero_personas, estado FROM pedido WHERE 1=1"
		);
		List<Object> params = new ArrayList<>();

		if (idMesa != null) {
			sql.append(" AND id_mesa = ?");
			params.add(idMesa);
		}
		if (idMesero != null) {
			sql.append(" AND id_mesero = ?");
			params.add(idMesero);
		}
		if (estado != null && !estado.isBlank()) {
			sql.append(" AND estado = ?");
			params.add(estado);
		}
		if (fechaDesde != null) {
			sql.append(" AND fecha_hora >= ?");
			params.add(fechaDesde);
		}
		if (fechaHasta != null) {
			sql.append(" AND fecha_hora <= ?");
			params.add(fechaHasta);
		}

		sql.append(" ORDER BY id_pedido DESC LIMIT ? OFFSET ?");
		params.add(size);
		params.add(page * size);

		return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
	}

	public Optional<Pedido> findById(Integer id) {
		List<Pedido> result = jdbcTemplate.query(
			"SELECT id_pedido, id_mesa, id_mesero, fecha_hora, numero_personas, estado FROM pedido WHERE id_pedido = ?",
			rowMapper,
			id
		);
		return result.stream().findFirst();
	}

	public boolean existsById(Integer id) {
		Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM pedido WHERE id_pedido = ?", Integer.class, id);
		return count != null && count > 0;
	}

	public Pedido save(Pedido pedido) {
		if (pedido.getIdPedido() == null) {
			Integer id = jdbcTemplate.queryForObject(
				"INSERT INTO pedido (id_mesa, id_mesero, fecha_hora, numero_personas, estado) VALUES (?, ?, ?, ?, ?) RETURNING id_pedido",
				Integer.class,
				pedido.getIdMesa(),
				pedido.getIdMesero(),
				pedido.getFechaHora(),
				pedido.getNumeroPersonas(),
				pedido.getEstado()
			);
			pedido.setIdPedido(id);
			return pedido;
		}

		jdbcTemplate.update(
			"UPDATE pedido SET id_mesa = ?, id_mesero = ?, fecha_hora = ?, numero_personas = ?, estado = ? WHERE id_pedido = ?",
			pedido.getIdMesa(),
			pedido.getIdMesero(),
			pedido.getFechaHora(),
			pedido.getNumeroPersonas(),
			pedido.getEstado(),
			pedido.getIdPedido()
		);
		return pedido;
	}

	public int updateEstadoById(Integer idPedido, String estado) {
		return jdbcTemplate.update("UPDATE pedido SET estado = ? WHERE id_pedido = ?", estado, idPedido);
	}

	public void deleteById(Integer id) {
		jdbcTemplate.update("DELETE FROM pedido WHERE id_pedido = ?", id);
	}
}
