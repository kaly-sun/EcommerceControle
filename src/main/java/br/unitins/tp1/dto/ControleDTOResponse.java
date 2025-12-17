package br.unitins.tp1.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.model.Controle;

public record ControleDTOResponse(
    Long id,
    String nome,
    Double preco,
    Integer estoque,
    String descricao,
    LocalDate dataLancamento,

    String marca,
    String plataforma,
    String modelo,

    Long idEspecificacaoTecnica,
    List<String> categorias
) {
    public static ControleDTOResponse valueOf(Controle controle) {
        return new ControleDTOResponse(
            controle.getId(),
            controle.getNome(),
            controle.getPreco(),
            controle.getEstoque(),
            controle.getDescricao(),
            controle.getDataLancamento(),
            controle.getMarca() != null ? controle.getMarca().getNome() : null,
            controle.getPlataforma() != null ? controle.getPlataforma().getNome() : null,
            controle.getModelo() != null ? controle.getModelo().getNome() : null,
            controle.getEspecificacaoTecnica() != null
                ? controle.getEspecificacaoTecnica().getId()
                : null,
            controle.getCategorias() != null
                ? controle.getCategorias()
                    .stream()
                    .map(c -> c.getNome())
                    .collect(Collectors.toList())
                : List.of()
        );
    }
}
