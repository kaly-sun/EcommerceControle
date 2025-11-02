package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.EspecificacaoTecDTO;
import br.unitins.tp1.dto.EspecificacaoTecDTOResponse;

public interface EspecificacaoTecService {
    List<EspecificacaoTecDTOResponse> getAll();
    EspecificacaoTecDTOResponse findById(Long id);
    EspecificacaoTecDTOResponse create(EspecificacaoTecDTO dto);
    EspecificacaoTecDTOResponse update(Long id, EspecificacaoTecDTO dto);
    void delete(Long id);
}
