package com.barud.service;

import com.barud.dto.request.PedidoRequestDto;
import com.barud.model.Pedido;
import com.barud.model.enums.MesaEstado;
import com.barud.model.enums.PedidoEstado;
import com.barud.repository.MesaRepository;
import com.barud.repository.PedidoRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;

    public PedidoService(PedidoRepository pedidoRepository, MesaRepository mesaRepository) {
        this.pedidoRepository = pedidoRepository;
        this.mesaRepository = mesaRepository;
    }

    public List<Pedido> listarConFiltros(
        Integer idMesa,
        Integer idMesero,
        PedidoEstado estado,
        LocalDateTime fechaDesde,
        LocalDateTime fechaHasta,
        int page,
        int size
    ) {
        int safePage = Math.max(page, 0);
        int safeSize = size <= 0 ? 10 : Math.min(size, 100);
        return pedidoRepository.findByFilters(idMesa, idMesero, estado, fechaDesde, fechaHasta, safePage, safeSize);
    }

    public Optional<Pedido> obtenerPorId(Integer id) {
        return pedidoRepository.findById(id);
    }

    public Pedido crear(PedidoRequestDto dto) {
        Pedido pedido = new Pedido(null, dto.idMesa(), dto.idMesero(), dto.fechaHora(), dto.numeroPersonas(), dto.estado());
        return pedidoRepository.save(pedido);
    }

    public Optional<Pedido> actualizar(Integer id, PedidoRequestDto dto) {
        if (!pedidoRepository.existsById(id)) {
            return Optional.empty();
        }
        Pedido pedido = new Pedido(id, dto.idMesa(), dto.idMesero(), dto.fechaHora(), dto.numeroPersonas(), dto.estado());
        return Optional.of(pedidoRepository.save(pedido));
    }

    public boolean eliminar(Integer id) {
        if (!pedidoRepository.existsById(id)) {
            return false;
        }
        pedidoRepository.deleteById(id);
        return true;
    }

    @Transactional
    public Optional<Pedido> cerrarPedidoYLiberarMesa(Integer idPedido) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(idPedido);
        if (pedidoOpt.isEmpty()) {
            return Optional.empty();
        }

        Pedido pedido = pedidoOpt.get();
        pedidoRepository.updateEstadoById(idPedido, PedidoEstado.CERRADO);
        mesaRepository.updateEstadoById(pedido.getIdMesa(), MesaEstado.DISPONIBLE);

        return pedidoRepository.findById(idPedido);
    }
}
