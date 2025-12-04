package br.unitins.tp1.service;

import br.unitins.tp1.dto.LoginDTO;
import br.unitins.tp1.dto.TokenResponseDTO;

public interface AuthService {
    TokenResponseDTO login(LoginDTO dto);
}
