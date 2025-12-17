package br.unitins.tp1.service;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.EnderecoEntregaResponseDTO;
import br.unitins.tp1.dto.ItemPedidoDTO;
import br.unitins.tp1.dto.ItemPedidoResponseDTO;
import br.unitins.tp1.dto.PagamentoDTO;
import br.unitins.tp1.dto.PagamentoResponseDTO;
import br.unitins.tp1.dto.PedidoDTO;
import br.unitins.tp1.dto.PedidoResponseDTO;
import br.unitins.tp1.exception.ValidationException;
import br.unitins.tp1.model.CartaoSalvo;
import br.unitins.tp1.model.Controle;
import br.unitins.tp1.model.EnderecoEntrega;
import br.unitins.tp1.model.ItemPedido;
import br.unitins.tp1.model.Pagamento;
import br.unitins.tp1.model.Pedido;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.CartaoSalvoRepository;
import br.unitins.tp1.repository.ControleRepository;
import br.unitins.tp1.repository.EnderecoEntregaRepository;
import br.unitins.tp1.repository.PagamentoRepository;
import br.unitins.tp1.repository.PedidoRepository;
import br.unitins.tp1.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject EnderecoEntregaRepository enderecoRepository;
    @Inject PedidoRepository pedidoRepository;
    @Inject ControleRepository controleRepository;
    @Inject UsuarioRepository usuarioRepository;
    @Inject PagamentoRepository pagamentoRepository;
    @Inject CartaoSalvoRepository cartaoSalvoRepository;

    @Inject EstoqueService estoqueService;
    @Inject ItemPedidoService itemPedidoService;

    @Override
    @Transactional
    public PedidoResponseDTO createParaUsuario(PedidoDTO dto, Long idUsuarioToken) {

        Usuario usuario = usuarioRepository.findById(idUsuarioToken);
        if (usuario == null)
            throw ValidationException.of("usuario", "Usuário autenticado não encontrado.");

        EnderecoEntrega endereco;

        if (dto.idEnderecoEntrega() == null && dto.endereco() == null)
            throw ValidationException.of("endereco", "Informe um idEnderecoEntrega ou um endereço.");

        if (dto.idEnderecoEntrega() != null && dto.endereco() != null)
            throw ValidationException.of("endereco", "Informe apenas um endereço.");

        if (dto.idEnderecoEntrega() != null) {
            endereco = enderecoRepository.findById(dto.idEnderecoEntrega());

            if (endereco == null)
                throw ValidationException.of("endereco", "Endereço não encontrado.");

            if (!endereco.getUsuario().getId().equals(idUsuarioToken))
                throw ValidationException.of("endereco", "Endereço não pertence ao usuário.");
        } else {
            EnderecoEntrega novo = new EnderecoEntrega();
            novo.setUsuario(usuario);
            novo.setRua(dto.endereco().logradouro());
            novo.setNumero(dto.endereco().numero());
            novo.setComplemento(dto.endereco().complemento());
            novo.setBairro(dto.endereco().bairro());
            novo.setCidade(dto.endereco().cidade());
            novo.setUf(dto.endereco().estado());
            novo.setCep(dto.endereco().cep());

            enderecoRepository.persist(novo);
            endereco = novo;
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEnderecoEntrega(endereco);
        pedido.setObservacao(dto.observacao());
        pedido.setStatus("PAGAMENTO_PENDENTE");

        pedidoRepository.persist(pedido);

        for (ItemPedidoDTO itemDTO : dto.itens()) {

            Controle controle = controleRepository.findById(itemDTO.idControle());
            if (controle == null)
                throw ValidationException.of("controle", "Controle não encontrado.");

            ItemPedido item = new ItemPedido();
            item.setControle(controle);
            item.setQuantidade(itemDTO.quantidade());
            item.setPrecoUnitario(BigDecimal.valueOf(controle.getPreco()));
            item.setPedido(pedido);

            pedido.addItem(item);
            itemPedidoService.salvar(item);
        }

        pedido.recalcTotal();

        for (ItemPedido item : pedido.getItens()) {
            estoqueService.debitarEstoque(
                    item.getControle().getId(),
                    item.getQuantidade()
            );
        }

        Pagamento pagamento = processarPagamento(dto.pagamento(), pedido, usuario, idUsuarioToken);

        pagamentoRepository.persist(pagamento);
        pedido.setPagamento(pagamento);

        return toResponseDTO(pedido);
    }

    @Override
    @Transactional
    public PedidoResponseDTO findById(Long idPedido) {

        Pedido pedido = pedidoRepository.findById(idPedido);
        if (pedido == null)
            throw ValidationException.of("id", "Pedido não encontrado.");

        return toResponseDTO(pedido);
    }

    @Override
    @Transactional
    public List<PedidoResponseDTO> findByUsuario(Long idUsuario) {

        Usuario usuario = usuarioRepository.findById(idUsuario);
        if (usuario == null)
            throw ValidationException.of("usuario", "Usuário não encontrado.");

        return pedidoRepository.findByUsuario(idUsuario)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PedidoResponseDTO findByIdSeguro(Long idPedido, Long idUsuarioToken, boolean isAdmin) {

        Pedido pedido = pedidoRepository.findById(idPedido);
        if (pedido == null)
            throw ValidationException.of("id", "Pedido não encontrado.");

        if (!isAdmin && !pedido.getUsuario().getId().equals(idUsuarioToken))
            throw ValidationException.of("acesso", "Acesso negado.");

        return toResponseDTO(pedido);
    }

    @Override
    @Transactional
    public void cancelar(Long idPedido) {

        Pedido pedido = pedidoRepository.findById(idPedido);
        if (pedido == null)
            throw ValidationException.of("id", "Pedido não encontrado.");

        if ("PAGO".equalsIgnoreCase(pedido.getStatus()))
            throw ValidationException.of("status", "Pedido já pago.");

        pedido.setStatus("CANCELADO");
        pedido.getPagamento().setStatus("REJEITADO");

        for (ItemPedido item : pedido.getItens()) {
            estoqueService.adicionarEstoque(
                    item.getControle().getId(),
                    item.getQuantidade()
            );
        }
    }

    @Override
    @Transactional
    public void cancelarSeguro(Long idPedido, Long idUsuario) {

        Pedido pedido = pedidoRepository.findById(idPedido);
        if (pedido == null)
            throw ValidationException.of("id", "Pedido não encontrado.");

        if (!pedido.getUsuario().getId().equals(idUsuario))
            throw ValidationException.of("acesso", "Acesso negado.");

        cancelar(idPedido);
    }

    private Pagamento processarPagamento(
            PagamentoDTO dto,
            Pedido pedido,
            Usuario usuario,
            Long idUsuarioToken
    ) {
        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setMetodoPagamento(dto.metodoPagamento().toUpperCase());
        pagamento.setStatus("PENDENTE");
        pagamento.setValor(pedido.getTotal());

        switch (pagamento.getMetodoPagamento()) {

            case "PIX" -> pagamento.setCodigoPagamento(null);

            case "BOLETO" -> {
                String linha = "34191.79001 01043.510047 91020.150008 1 000000" + pedido.getId();
                String pdfBase64 = Base64.getEncoder()
                        .encodeToString(("BOLETO-" + pedido.getId()).getBytes());
                pagamento.setCodigoPagamento(linha + "|PDF:" + pdfBase64);
            }

            case "CARTAO" -> {
                if (dto.idCartaoSalvo() != null) {

                    CartaoSalvo cartao =
                            cartaoSalvoRepository.buscarDoUsuario(dto.idCartaoSalvo(), idUsuarioToken);

                    if (cartao == null)
                        throw ValidationException.of("cartao", "Cartão inválido.");

                    pagamento.setCodigoPagamento("TRANS-" + UUID.randomUUID());
                    pagamento.setUltimos4(cartao.getUltimos4());
                    pagamento.setBandeira(cartao.getBandeira());

                } else {
                    throw ValidationException.of("cartao", "Informe os dados do cartão.");
                }
            }

            default -> throw ValidationException.of("metodo", "Método inválido.");
        }

        return pagamento;
    }

    private PedidoResponseDTO toResponseDTO(Pedido pedido) {

        List<ItemPedidoResponseDTO> itensDTO =
                pedido.getItens().stream()
                        .map(item -> new ItemPedidoResponseDTO(
                                item.getControle().getId(),
                                item.getControle().getNome(),
                                item.getQuantidade(),
                                item.getPrecoUnitario(),
                                item.getSubtotal()
                        ))
                        .collect(Collectors.toList());

        Pagamento p = pedido.getPagamento();

        PagamentoResponseDTO pagamentoDTO =
                new PagamentoResponseDTO(
                        p.getId(),
                        p.getMetodoPagamento(),
                        p.getStatus(),
                        p.getValor(),
                        p.getCodigoPagamento(),
                        p.getDataCriacao(),
                        p.getUltimos4(),
                        p.getBandeira()
                );

        EnderecoEntregaResponseDTO enderecoDTO =
                pedido.getEnderecoEntrega() != null
                        ? new EnderecoEntregaResponseDTO(
                                pedido.getEnderecoEntrega().getId(),
                                pedido.getEnderecoEntrega().getRua(),
                                pedido.getEnderecoEntrega().getNumero(),
                                pedido.getEnderecoEntrega().getComplemento(),
                                pedido.getEnderecoEntrega().getBairro(),
                                pedido.getEnderecoEntrega().getCidade(),
                                pedido.getEnderecoEntrega().getUf(),
                                pedido.getEnderecoEntrega().getCep()
                        )
                        : null;

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getDataCriacao(),
                pedido.getTotal(),
                pedido.getStatus(),
                pedido.getObservacao(),
                enderecoDTO,
                itensDTO,
                pagamentoDTO
        );
    }
}
