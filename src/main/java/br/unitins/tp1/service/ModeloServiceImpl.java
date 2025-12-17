package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.ModeloDTO;
import br.unitins.tp1.dto.ModeloDTOResponse;
import br.unitins.tp1.model.Marca;
import br.unitins.tp1.model.Modelo;
import br.unitins.tp1.model.Plataforma;
import br.unitins.tp1.repository.MarcaRepository;
import br.unitins.tp1.repository.ModeloRepository;
import br.unitins.tp1.repository.PlataformaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ModeloServiceImpl implements ModeloService {

    @Inject
    ModeloRepository repository;

    @Inject
    MarcaRepository marcaRepository;

    @Inject
    PlataformaRepository plataformaRepository;

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

        if (dto.idMarca() == null) {
            throw new IllegalArgumentException("Id da marca é obrigatório");
        }

        if (dto.idPlataforma() == null) {
            throw new IllegalArgumentException("Id da plataforma é obrigatório");
        }

        Marca marca = marcaRepository.findById(dto.idMarca());
        if (marca == null)
            throw new NotFoundException("Marca não encontrada");

        Plataforma plataforma = plataformaRepository.findById(dto.idPlataforma());
        if (plataforma == null)
            throw new NotFoundException("Plataforma não encontrada");

        Modelo modelo = new Modelo();
        modelo.setNome(dto.nome());
        modelo.setVersao(dto.versao());
        modelo.setAnoLancamento(dto.anoLancamento());
        modelo.setDescricao(dto.descricao());
        modelo.setCodigoReferencia(dto.codigoReferencia());
        modelo.setAtivo(dto.ativo());
        modelo.setMarca(marca);
        modelo.setPlataforma(plataforma);

        repository.persist(modelo);

        return ModeloDTOResponse.valueOf(modelo);
    }

    @Override
    @Transactional
    public ModeloDTOResponse atualizar(Long id, ModeloDTO dto) {

        Modelo modelo = repository.findById(id);
        if (modelo == null)
            return null;

        if (dto.idMarca() == null) {
            throw new IllegalArgumentException("Id da marca é obrigatório");
        }

        if (dto.idPlataforma() == null) {
            throw new IllegalArgumentException("Id da plataforma é obrigatório");
        }

        Marca marca = marcaRepository.findById(dto.idMarca());
        if (marca == null)
            throw new NotFoundException("Marca não encontrada");

        Plataforma plataforma = plataformaRepository.findById(dto.idPlataforma());
        if (plataforma == null)
            throw new NotFoundException("Plataforma não encontrada");

        modelo.setNome(dto.nome());
        modelo.setVersao(dto.versao());
        modelo.setAnoLancamento(dto.anoLancamento());
        modelo.setDescricao(dto.descricao());
        modelo.setCodigoReferencia(dto.codigoReferencia());
        modelo.setAtivo(dto.ativo());
        modelo.setMarca(marca);
        modelo.setPlataforma(plataforma);

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
}
