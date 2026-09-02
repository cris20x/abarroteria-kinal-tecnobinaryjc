package main.java.com.cristech.abarroteria.kinal.service.usuario;

import main.java.com.cristech.abarroteria.kinal.repository.usuario.UsuarioRepository;

/**
 *
 * @author Dell
 */
public class UsuarioService {
    
    private UsuarioRepository usuarioRepository;
    
    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }
    
}
