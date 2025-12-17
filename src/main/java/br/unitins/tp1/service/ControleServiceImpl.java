package br.unitins.tp1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.ControleDTO;
import br.unitins.tp1.dto.ControleDTOResponse;
import br.unitins.tp1.model.Categoria;
import br.unitins.tp1.model.Controle;
import br.unitins.tp1.model.EspecificacaoTecnica;
import br.unitins.tp1.model.Marca;
import br.unitins.tp1.model.Modelo;
import br.unitins.tp1.model.Plataforma;
import br.unitins.tp1.repository.CategoriaRepository;
import br.unitins.tp1.repository.ControleRepository;
import br.unitins.tp1.repository.EspecificacaoTecRepository;
import br.unitins.tp1.repository.MarcaRepository;
import br.unitins.tp1.repository.ModeloRepository;
import br.unitins.tp1.repository.PlataformaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ControleServiceImpl implements ControleService {

    @Inject
    ControleRepository repository;

    @Inject
    MarcaRepository marcaRepository;

    @Inject
    PlataformaRepository plataformaRepository;

    @Inject
    CategoriaRepository categoriaRepository;

    @Inject
    ModeloRepository modeloRepository;

    @Inject
    EspecificacaoTecRepository especificacaoTecnicaRepository;

    @Override
    public List<ControleDTOResponse> findAll() {
        return repository.listAll()
                .stream()
                .map(ControleDTOResponse::valueOf)
                .toList();
    }

    @Override
    public List<ControleDTOResponse> getAll() {
        return repository.listAll()
                .stream()
                .map(ControleDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public ControleDTOResponse findById(Long id) {
        Controle controle = repository.findById(id);
        if (controle == null)
            return null;
        return ControleDTOResponse.valueOf(controle);
    }

    @Override
    public List<ControleDTOResponse> findByNome(String nome) {
        return repository.findByNome(nome)
                .stream()
                .map(ControleDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<ControleDTOResponse> findByCategoria(String nome) {
        return repository.findByCategoria(nome)
                .stream()
                .map(ControleDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ControleDTOResponse create(ControleDTO dto) {
        Controle controle = new Controle();
        updateData(controle, dto);
        repository.persist(controle);
        return ControleDTOResponse.valueOf(controle);
    }

    @Override
    @Transactional
    public ControleDTOResponse update(Long id, ControleDTO dto) {
        Controle controle = repository.findById(id);
        if (controle == null)
            throw new IllegalArgumentException("Controle com ID " + id + " não encontrado.");
        updateData(controle, dto);
        return ControleDTOResponse.valueOf(controle);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Controle controle = repository.findById(id);
        if (controle == null)
            throw new IllegalArgumentException("Controle com ID " + id + " não encontrado.");
        repository.delete(controle);
    }

    private void updateData(Controle controle, ControleDTO dto) {

        controle.setNome(dto.nome());
        controle.setPreco(dto.preco());
        controle.setCor(dto.cor());
        controle.setEstoque(dto.estoque());
        controle.setDescricao(dto.descricao());
        controle.setDataLancamento(dto.dataLancamento());

        if (dto.idMarca() != null) {
            Marca marca = marcaRepository.findById(dto.idMarca());
            if (marca == null)
                throw new IllegalArgumentException("Marca não encontrada.");
            controle.setMarca(marca);
        } else {
            controle.setMarca(null);
        }

        if (dto.idPlataforma() != null) {
            Plataforma plataforma = plataformaRepository.findById(dto.idPlataforma());
            if (plataforma == null)
                throw new IllegalArgumentException("Plataforma não encontrada.");
            controle.setPlataforma(plataforma);
        } else {
            controle.setPlataforma(null);
        }

        if (dto.idModelo() != null) {
            Modelo modelo = modeloRepository.findById(dto.idModelo());
            if (modelo == null)
                throw new IllegalArgumentException("Modelo não encontrado.");
            controle.setModelo(modelo);
        } else {
            controle.setModelo(null);
        }

        if (dto.idEspecificacaoTecnica() != null) {
            EspecificacaoTecnica esp = especificacaoTecnicaRepository.findById(dto.idEspecificacaoTecnica());
            if (esp == null)
                throw new IllegalArgumentException("Especificação técnica não encontrada.");
            controle.setEspecificacaoTecnica(esp);
        } else {
            controle.setEspecificacaoTecnica(null);
        }

        if (controle.getCategorias() == null)
            controle.setCategorias(new ArrayList<>());

        controle.getCategorias().clear();

        if (dto.idsCategorias() != null) {
            for (Long idCategoria : dto.idsCategorias()) {
                Categoria categoria = categoriaRepository.findById(idCategoria);
                if (categoria == null)
                    throw new IllegalArgumentException("Categoria não encontrada.");
                controle.getCategorias().add(categoria);
            }
        }
    }
}
