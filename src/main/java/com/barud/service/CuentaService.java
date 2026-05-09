package com.barud.service;

import com.barud.model.Cuenta;
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

    public List<Cuenta> listarConFiltros(Integer idPedido, String estado, BigDecimal minTotal, BigDecimal maxTotal, int page, int size) {
        int safePage = Math.max(page, 0);
        int safeSize = size <= 0 ? 10 : Math.min(size, 100);
        return cuentaRepository.findByFilters(idPedido, estado, minTotal, maxTotal, safePage, safeSize);
    }

    public Optional<Cuenta> obtenerPorId(Integer id) {
        return cuentaRepository.findById(id);
    }

    public Cuenta crear(Cuenta cuenta) {
        cuenta.setIdCuenta(null);
        return cuentaRepository.save(cuenta);
    }

    public Optional<Cuenta> actualizar(Integer id, Cuenta cuenta) {
        if (!cuentaRepository.existsById(id)) {
            return Optional.empty();
        }
        cuenta.setIdCuenta(id);
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
        cuentaRepository.updateEstadoById(cuenta.getIdCuenta(), "Cerrada");
        pedidoRepository.updateEstadoById(cuenta.getIdPedido(), "Cerrado");

        return cuentaRepository.findById(idCuenta);
    }
}
