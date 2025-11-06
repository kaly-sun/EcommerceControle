package br.unitins.tp1.dto;

import java.time.LocalDate;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ControleDTO(
    String nome,
    Double preco,
    String cor,
    Integer estoque,
    String descricao,

    @Schema(type = SchemaType.STRING, example = "01/04/2025")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dataLancamento,

    Long idMarca,
    Long idPlataforma,
    Long idModelo,
    Long idEspecificacaoTecnica,

    // ✅ Correto — lista de IDs das categorias
    List<Long> idsCategorias
) {}
