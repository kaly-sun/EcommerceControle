package br.unitins.tp1.dto;

import br.unitins.tp1.model.EnderecoEntrega;

public record EnderecoEntregaResponseDTO(
    Long id,
    String cep,
    String logradouro,
    String numero,
    String bairro,
    String cidade,
    String estado,
    String complemento
) {

    public static EnderecoEntregaResponseDTO valueOf(EnderecoEntrega endereco) {
        return new EnderecoEntregaResponseDTO(
            endereco.getId(),
            endereco.getCep(),
            endereco.getLogradouro(),
            endereco.getNumero(),
            endereco.getBairro(),
            endereco.getCidade(),
            endereco.getEstado(),
            endereco.getComplemento()
        );
    }
}
