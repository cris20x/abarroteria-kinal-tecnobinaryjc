package main.java.com.tecnobinaryjc.abarroteria.kinal.service.usuario;

import main.java.com.tecnobinaryjc.abarroteria.kinal.repository.usuario.UsuarioRepository;

public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void registrar(String nombre, String apellido, String email,
                           String contrasena, int idRol) {

        usuarioRepository.registrar(nombre, apellido, email, contrasena, idRol);
    }
}
