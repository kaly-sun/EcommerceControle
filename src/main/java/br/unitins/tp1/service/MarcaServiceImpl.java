package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.MarcaDTO;
import br.unitins.tp1.dto.MarcaDTOResponse;
import br.unitins.tp1.model.Marca;
import br.unitins.tp1.repository.MarcaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MarcaServiceImpl implements MarcaService {

    @Inject
    MarcaRepository repository;

    @Override
    public List<MarcaDTOResponse> listarTodos() {
        return repository.listAll()
                .stream()
                .map(MarcaDTOResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public MarcaDTOResponse buscarPorId(Long id) {
        Marca marca = repository.findById(id);
        if (marca == null) {
            return null;
        }
        return new MarcaDTOResponse(marca);
    }

    @Override
    public List<MarcaDTOResponse> buscarPorNome(String nome) {
        return repository.findByNome(nome)
                .stream()
                .map(MarcaDTOResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MarcaDTOResponse criar(MarcaDTO dto) {
        Marca nova = new Marca();
        nova.setNome(dto.nome());
        nova.setPaisOrigem(dto.paisOrigem());
        nova.setAnoFundacao(dto.anoFundacao());
        nova.setSiteOficial(dto.siteOficial());
        nova.setLogo(dto.logo());

        repository.persist(nova);
        return new MarcaDTOResponse(nova);
    }

    @Override
    @Transactional
    public MarcaDTOResponse atualizar(Long id, MarcaDTO dto) {
        Marca existente = repository.findById(id);
        if (existente == null) {
            return null;
        }

        existente.setNome(dto.nome());
        existente.setPaisOrigem(dto.paisOrigem());
        existente.setAnoFundacao(dto.anoFundacao());
        existente.setSiteOficial(dto.siteOficial());
        existente.setLogo(dto.logo());

        return new MarcaDTOResponse(existente);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        Marca marca = repository.findById(id);
        if (marca != null) {
            repository.delete(marca);
        }
    }
}
