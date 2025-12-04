package br.unitins.tp1.dto;

public record EnderecoEntregaDTO(
    String cep,
    String logradouro,
    String numero,
    String bairro,
    String cidade,
    String estado,
    String complemento
) {}
