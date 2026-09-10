package main.java.com.tecnobinaryjc.abarroteria.kinal.service;

import main.java.com.tecnobinaryjc.abarroteria.kinal.dto.request.LoginDTORequest;
import main.java.com.tecnobinaryjc.abarroteria.kinal.dto.response.LoginDTOResponse;
import main.java.com.tecnobinaryjc.abarroteria.kinal.repository.AuthRepository;
import main.java.com.tecnobinaryjc.abarroteria.kinal.security.jbcrypt.BCrypt;

// Lógica de negocio para el inicio de sesión.
public class AuthService {

    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    // Valida credenciales y retorna los datos del usuario si el login es correcto.
    public LoginDTOResponse login(LoginDTORequest request){

        if(request == null){
            throw new RuntimeException("Los datos están vacios");
        }else if(request.getEmail() == null || request.getPassword() == null){
            throw new RuntimeException("Uno o los dos campos están vacios");
        }else if(request.getEmail().isEmpty() || request.getPassword().isEmpty()){
            throw new RuntimeException("No puedes dejar campos en blanco");
        }

        request.setEmail(request.getEmail().trim().toLowerCase());
        LoginDTOResponse response = authRepository.findUserByEmail(request);

        if(response == null){
            throw new RuntimeException("Usuario no encontrado");
        }
        if(response.getContrasenaHash() == null){
            throw new RuntimeException("No se ha podido concretar la operación.");
        }

        if(BCrypt.checkpw(request.getPassword(), response.getContrasenaHash())){
            return response;
        }

        return null;
    }

}