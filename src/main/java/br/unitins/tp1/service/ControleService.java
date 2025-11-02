package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.ControleDTO;
import br.unitins.tp1.dto.ControleDTOResponse;

public interface ControleService {

    List<ControleDTOResponse> getAll();

    ControleDTOResponse create(ControleDTO dto);

    ControleDTOResponse update(Long id, ControleDTO dto);

    void delete(Long id);

    ControleDTOResponse findById(Long id);

    List<ControleDTOResponse> findAll();

    List<ControleDTOResponse> findByNome(String nome);
}
