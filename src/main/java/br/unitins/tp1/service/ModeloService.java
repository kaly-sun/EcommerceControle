package br.unitins.tp1.service;

import java.util.List;
import br.unitins.tp1.dto.ModeloDTO;
import br.unitins.tp1.dto.ModeloDTOResponse;

public interface ModeloService {
    List<ModeloDTOResponse> listarTodos();
    ModeloDTOResponse buscarPorId(Long id);
    ModeloDTOResponse criar(ModeloDTO dto);
    ModeloDTOResponse atualizar(Long id, ModeloDTO dto);
    void deletar(Long id);
}
