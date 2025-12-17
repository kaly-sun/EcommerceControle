package br.unitins.tp1.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoResponseDTO(

    Long id,
    String metodoPagamento,
    String status,
    BigDecimal valor,
    String codigoPagamento,
    LocalDateTime dataCriacao,
    String ultimos4,
    String bandeira

){}
