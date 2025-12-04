package br.unitins.tp1.resource;

import br.unitins.tp1.dto.CompraDTO;
import br.unitins.tp1.dto.CompraResponseDTO;
import br.unitins.tp1.service.CompraService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/compras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompraResource {

    @Inject
    CompraService compraService;

    @POST
    public Response criarCompra(CompraDTO dto) {
        CompraResponseDTO resp = compraService.realizarCompra(dto);
        return Response.status(Response.Status.CREATED).entity(resp).build();
    }

    @GET
    public List<CompraResponseDTO> listar() {
        return compraService.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") Long id) {
        CompraResponseDTO resp = compraService.buscarPorId(id);
        if (resp == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(resp).build();
    }
}
