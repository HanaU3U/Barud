package com.barud.service;

import com.barud.dto.request.CuentaRequestDto;
import com.barud.model.Cuenta;
import com.barud.model.enums.CuentaEstado;
import com.barud.model.enums.PedidoEstado;
import com.barud.repository.CuentaRepository;
import com.barud.repository.PedidoRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final PedidoRepository pedidoRepository;

    public CuentaService(CuentaRepository cuentaRepository, PedidoRepository pedidoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public List<Cuenta> listarConFiltros(
        Integer idPedido,
        CuentaEstado estado,
        BigDecimal minTotal,
        BigDecimal maxTotal,
        int page,
        int size
    ) {
        int safePage = Math.max(page, 0);
        int safeSize = size <= 0 ? 10 : Math.min(size, 100);
        return cuentaRepository.findByFilters(idPedido, estado, minTotal, maxTotal, safePage, safeSize);
    }

    public Optional<Cuenta> obtenerPorId(Integer id) {
        return cuentaRepository.findById(id);
    }

    public Cuenta crear(CuentaRequestDto dto) {
        Optional<Cuenta> existente = cuentaRepository.findByIdPedido(dto.idPedido());
        if (existente.isPresent()) {
            throw new IllegalStateException("Ya existe una cuenta para el pedido " + dto.idPedido());
        }
        Cuenta cuenta = new Cuenta(null, dto.idPedido(), dto.subtotal(), dto.impuestos(), dto.total(), dto.estado());
        return cuentaRepository.save(cuenta);
    }

    public Optional<Cuenta> actualizar(Integer id, CuentaRequestDto dto) {
        if (!cuentaRepository.existsById(id)) {
            return Optional.empty();
        }

        Optional<Cuenta> existente = cuentaRepository.findByIdPedido(dto.idPedido());
        if (existente.isPresent() && !existente.get().getIdCuenta().equals(id)) {
            throw new IllegalStateException("Ya existe una cuenta para el pedido " + dto.idPedido());
        }

        Cuenta cuenta = new Cuenta(id, dto.idPedido(), dto.subtotal(), dto.impuestos(), dto.total(), dto.estado());
        return Optional.of(cuentaRepository.save(cuenta));
    }

    public boolean eliminar(Integer id) {
        if (!cuentaRepository.existsById(id)) {
            return false;
        }
        cuentaRepository.deleteById(id);
        return true;
    }

    @Transactional
    public Optional<Cuenta> cerrarCuentaYCerrarPedido(Integer idCuenta) {
        Optional<Cuenta> cuentaOpt = cuentaRepository.findById(idCuenta);
        if (cuentaOpt.isEmpty()) {
            return Optional.empty();
        }

        Cuenta cuenta = cuentaOpt.get();
        cuentaRepository.updateEstadoById(cuenta.getIdCuenta(), CuentaEstado.CERRADA);
        pedidoRepository.updateEstadoById(cuenta.getIdPedido(), PedidoEstado.CERRADO);

        return cuentaRepository.findById(idCuenta);
    }
}
