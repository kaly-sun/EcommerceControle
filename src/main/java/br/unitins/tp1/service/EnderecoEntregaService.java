package br.unitins.tp1.service;

import br.unitins.tp1.dto.EnderecoEntregaDTO;
import br.unitins.tp1.model.EnderecoEntrega;
import br.unitins.tp1.repository.EnderecoEntregaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EnderecoEntregaService {

    @Inject
    EnderecoEntregaRepository repository;

    @Transactional
    public EnderecoEntrega criar(EnderecoEntregaDTO dto) {
        EnderecoEntrega e = new EnderecoEntrega();
        e.setCep(dto.cep());
        e.setLogradouro(dto.logradouro());
        e.setNumero(dto.numero());
        e.setBairro(dto.bairro());
        e.setCidade(dto.cidade());
        e.setEstado(dto.estado());
        e.setComplemento(dto.complemento());

        repository.persist(e);

        return e;
    }
}
