package br.unitins.tp1.resource;

import br.unitins.tp1.dto.PagamentoDTO;
import br.unitins.tp1.dto.PagamentoDTOResponse;
import br.unitins.tp1.service.PagamentoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/pagamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PagamentoResource {

    @Inject
    PagamentoService pagamentoService;

    @POST
    public PagamentoDTOResponse pagar(PagamentoDTO dto) {
        return pagamentoService.pagar(dto);
    }
}
