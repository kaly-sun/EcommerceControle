package br.unitins.tp1.service;

import br.unitins.tp1.model.Controle;
import br.unitins.tp1.repository.ControleRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EstoqueService {

    @Inject
    ControleRepository controleRepository;

    public Integer consultarEstoque(Long idControle) {
        Controle controle = controleRepository.findById(idControle);
        if (controle == null)
            throw new IllegalArgumentException("Controle não encontrado: " + idControle);

        return controle.getEstoque();
    }

    @Transactional
    public void debitarEstoque(Long idControle, int quantidade) {
        Controle controle = controleRepository.findById(idControle);
        if (controle == null)
            throw new IllegalArgumentException("Controle não encontrado: " + idControle);

        Integer estoqueAtual = controle.getEstoque();
        if (estoqueAtual == null) estoqueAtual = 0;

        if (estoqueAtual < quantidade)
            throw new RuntimeException("Estoque insuficiente para o controle: " + controle.getNome());

        controle.setEstoque(estoqueAtual - quantidade);
    }

    @Transactional
    public void adicionarEstoque(Long idControle, int quantidade) {
        Controle controle = controleRepository.findById(idControle);
        if (controle == null)
            throw new IllegalArgumentException("Controle não encontrado: " + idControle);

        Integer estoqueAtual = controle.getEstoque();
        if (estoqueAtual == null) estoqueAtual = 0;

        controle.setEstoque(estoqueAtual + quantidade);
    }
}
