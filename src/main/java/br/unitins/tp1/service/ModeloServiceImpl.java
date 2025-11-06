package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.ModeloDTO;
import br.unitins.tp1.dto.ModeloDTOResponse;
import br.unitins.tp1.model.Modelo;
import br.unitins.tp1.repository.ModeloRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ModeloServiceImpl implements ModeloService {

    @Inject
    ModeloRepository repository;

    @Override
    public List<ModeloDTOResponse> listarTodos() {
        return repository.listAll()
                .stream()
                .map(ModeloDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public ModeloDTOResponse buscarPorId(Long id) {
        Modelo modelo = repository.findById(id);
        if (modelo == null)
            return null;
        return ModeloDTOResponse.valueOf(modelo);
    }

    @Override
    @Transactional
    public ModeloDTOResponse criar(ModeloDTO dto) {
        Modelo modelo = new Modelo();
        preencher(modelo, dto);
        repository.persist(modelo);
        return ModeloDTOResponse.valueOf(modelo);
    }

    @Override
    @Transactional
    public ModeloDTOResponse atualizar(Long id, ModeloDTO dto) {
        Modelo modelo = repository.findById(id);
        if (modelo == null)
            return null;

        preencher(modelo, dto);
        return ModeloDTOResponse.valueOf(modelo);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        Modelo modelo = repository.findById(id);
        if (modelo != null) {
            repository.delete(modelo);
        }
    }

    private void preencher(Modelo modelo, ModeloDTO dto) {
        modelo.setNome(dto.nome());
        modelo.setVersao(dto.versao());
        modelo.setAnoLancamento(dto.anoLancamento());
        modelo.setDescricao(dto.descricao());
        modelo.setCodigoReferencia(dto.codigoReferencia());
        modelo.setAtivo(dto.ativo());
    }
}
