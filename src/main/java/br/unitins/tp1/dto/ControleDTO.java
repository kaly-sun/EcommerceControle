package br.unitins.tp1.dto;

import java.time.LocalDate;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ControleDTO(

    @Schema(example = "Controle DualSense")
    String nome,

    @Schema(example = "499.90")
    Double preco,

    @Schema(example = "Branco")
    String cor,

    @Schema(example = "10")
    Integer estoque,

    @Schema(example = "Controle oficial do PlayStation 5")
    String descricao,

    @Schema(type = SchemaType.STRING, example = "01/04/2025")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dataLancamento,

    @Schema(example = "1")
    Long idMarca,

    @Schema(example = "1")
    Long idPlataforma,

    @Schema(example = "1")
    Long idModelo,

    @Schema(example = "1")
    Long idEspecificacaoTecnica,

    @Schema(
        description = "Lista de IDs das categorias",
        example = "[1, 3]"
    )
    List<Long> idsCategorias

) {}
