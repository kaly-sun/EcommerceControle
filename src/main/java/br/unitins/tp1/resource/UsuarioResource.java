package br.unitins.tp1.resource;

import br.unitins.tp1.dto.UsuarioDTO;
import br.unitins.tp1.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService service;

    // CADASTRO DO CLIENTE (sem login)
    @POST
    @Path("/cadastro")
    public Response cadastrar(UsuarioDTO dto) {
        return Response.ok(service.cadastrar(dto)).build();
    }

    // LISTAR USUÁRIOS – apenas admins
    @GET
    @RolesAllowed({"ADMIN"})
    public Response listarUsuarios() {
        return Response.ok(service.listarTodos()).build();
    }
}
