package com.barud.service;

import com.barud.dto.request.DivisionCuentaRequestDto;
import com.barud.model.DivisionCuenta;
import com.barud.repository.DivisionCuentaRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

@Service
public class DivisionCuentaService {

    private final DivisionCuentaRepository divisionCuentaRepository;

    public DivisionCuentaService(DivisionCuentaRepository divisionCuentaRepository) {
        this.divisionCuentaRepository = divisionCuentaRepository;
    }

    public List<DivisionCuenta> listarTodas() {
        return StreamSupport.stream(divisionCuentaRepository.findAll().spliterator(), false).toList();
    }

    public Optional<DivisionCuenta> obtenerPorId(Integer id) {
        return divisionCuentaRepository.findById(id);
    }

    public DivisionCuenta crear(DivisionCuentaRequestDto dto) {
        DivisionCuenta division = new DivisionCuenta(null, dto.idCuenta(), dto.descripcion(), dto.monto());
        return divisionCuentaRepository.save(division);
    }

    public Optional<DivisionCuenta> actualizar(Integer id, DivisionCuentaRequestDto dto) {
        if (!divisionCuentaRepository.existsById(id)) {
            return Optional.empty();
        }
        DivisionCuenta division = new DivisionCuenta(id, dto.idCuenta(), dto.descripcion(), dto.monto());
        return Optional.of(divisionCuentaRepository.save(division));
    }

    public boolean eliminar(Integer id) {
        if (!divisionCuentaRepository.existsById(id)) {
            return false;
        }
        divisionCuentaRepository.deleteById(id);
        return true;
    }
}
