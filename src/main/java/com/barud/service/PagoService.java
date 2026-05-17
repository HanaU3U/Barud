package com.barud.service;

import com.barud.dto.request.PagoRequestDto;
import com.barud.model.Pago;
import com.barud.repository.PagoRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public List<Pago> listarTodos() {
        return StreamSupport.stream(pagoRepository.findAll().spliterator(), false).toList();
    }

    public Optional<Pago> obtenerPorId(Integer id) {
        return pagoRepository.findById(id);
    }

    public Pago crear(PagoRequestDto dto) {
        Pago pago = new Pago(null, dto.idCuenta(), dto.metodo(), dto.monto(), dto.fecha());
        return pagoRepository.save(pago);
    }

    public Optional<Pago> actualizar(Integer id, PagoRequestDto dto) {
        if (!pagoRepository.existsById(id)) {
            return Optional.empty();
        }
        Pago pago = new Pago(id, dto.idCuenta(), dto.metodo(), dto.monto(), dto.fecha());
        return Optional.of(pagoRepository.save(pago));
    }

    public boolean eliminar(Integer id) {
        if (!pagoRepository.existsById(id)) {
            return false;
        }
        pagoRepository.deleteById(id);
        return true;
    }
}
