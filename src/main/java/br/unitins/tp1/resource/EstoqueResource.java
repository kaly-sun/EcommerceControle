package br.unitins.tp1.resource;

import br.unitins.tp1.dto.EstoqueUpdateDTO;
import br.unitins.tp1.service.EstoqueService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/estoque")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstoqueResource {

    @Inject
    EstoqueService estoqueService;

    @GET
    @Path("/{idControle}")
    public Integer consultar(@PathParam("idControle") Long id) {
        return estoqueService.consultarEstoque(id);
    }

    @PUT
    @Path("/adicionar/{idControle}")
    public void adicionar(@PathParam("idControle") Long id, EstoqueUpdateDTO dto) {
        estoqueService.adicionarEstoque(id, dto.quantidade());
    }

    @PUT
    @Path("/debitar/{idControle}")
    public void debitar(@PathParam("idControle") Long id, EstoqueUpdateDTO dto) {
        estoqueService.debitarEstoque(id, dto.quantidade());
    }
}
