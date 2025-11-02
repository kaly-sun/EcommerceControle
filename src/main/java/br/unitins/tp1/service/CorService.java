package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.CorDTO;
import br.unitins.tp1.dto.CorDTOResponse;

public interface CorService {

    CorDTOResponse insert(CorDTO dto);

    CorDTOResponse update(Long id, CorDTO dto);

    void delete(Long id);

    CorDTOResponse findById(Long id);

    List<CorDTOResponse> findAll();
}
