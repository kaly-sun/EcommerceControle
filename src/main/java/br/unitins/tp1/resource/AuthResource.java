package br.unitins.tp1.resource;

import br.unitins.tp1.dto.LoginDTO;
import br.unitins.tp1.dto.TokenResponseDTO;
import br.unitins.tp1.service.AuthService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService service;

    @POST
    public TokenResponseDTO login(LoginDTO dto) {
        return service.login(dto);
    }
}
