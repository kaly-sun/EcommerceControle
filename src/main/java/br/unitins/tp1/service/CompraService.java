package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.CompraDTO;
import br.unitins.tp1.dto.CompraResponseDTO;
import br.unitins.tp1.model.Compra;
import br.unitins.tp1.model.Controle;
import br.unitins.tp1.model.EnderecoEntrega;
import br.unitins.tp1.model.ItemCompra;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.CompraRepository;
import br.unitins.tp1.repository.ControleRepository;
import br.unitins.tp1.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CompraService {

    @Inject
    EnderecoEntregaService enderecoEntregaService;

    @Inject
    CompraRepository compraRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    ControleRepository controleRepository;

    @Transactional
    public CompraResponseDTO realizarCompra(CompraDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.idUsuario());
        if (usuario == null)
            throw new IllegalArgumentException("Usuário não encontrado: " + dto.idUsuario());

        Compra compra = new Compra();
        compra.setUsuario(usuario);
        compra.setFormaPagamento(dto.formaPagamento());

        EnderecoEntrega endereco = enderecoEntregaService.criar(dto.enderecoEntrega());
        compra.setEnderecoEntrega(endereco);

        List<ItemCompra> itens = dto.itens().stream().map(i -> {

            Controle controle = controleRepository.findById(i.idControle());
            if (controle == null)
                throw new IllegalArgumentException("Controle não encontrado: " + i.idControle());

            controle.debitarEstoque(i.quantidade());
            controleRepository.persist(controle);

            ItemCompra item = new ItemCompra();
            item.setControle(controle);
            item.setQuantidade(i.quantidade());
            item.setPrecoUnitario(controle.getPreco());

            return item;

        }).toList();

        itens.forEach(compra::addItem);
        compra.calcularTotal();

        compraRepository.persist(compra);

        return CompraResponseDTO.valueOf(compra);
    }

    public CompraResponseDTO buscarPorId(Long id) {
        Compra c = compraRepository.findById(id);
        return CompraResponseDTO.valueOf(c);
    }

    public List<CompraResponseDTO> listarTodos() {
        return compraRepository.findAll()
                .stream()
                .map(CompraResponseDTO::valueOf)
                .toList();
    }
}
