package main.java.com.TecnoBinaryJC.abarroteria.kinal.service.usuario;

import main.java.com.TecnoBinaryJC.abarroteria.kinal.model.Usuario;
import main.java.com.TecnoBinaryJC.abarroteria.kinal.repository.usuario.UsuarioRepository;
import main.java.com.TecnoBinaryJC.abarroteria.kinal.security.jbcrypt.BCrypt;

/**
 *
 * @author Dell
 */
public class UsuarioService {
    
    private UsuarioRepository usuarioRepository;
    
    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }
    
    public void registrar(String nombre, String apellido, String email, String password, int idRol){
        
        if(nombre == null || nombre.isBlank()){
            throw new RuntimeException("El nombre es obligatorio.");
        }
        if(apellido == null || apellido.isBlank()){
            throw new RuntimeException("El apellido es obligatorio.");
        }
        if(email == null || email.isBlank()){
            throw new RuntimeException("El correo electrónico es obligatorio.");
        }
        if(password == null || password.isBlank()){
            throw new RuntimeException("La contraseña es obligatoria.");
        }
        if(password.length() < 6){
            throw new RuntimeException("La contraseña debe tener al menos 6 caracteres.");
        }
        if (idRol != 1 && idRol != 2) {
            throw new RuntimeException("El rol seleccionado no es válido.");
        }
        email = email.trim().toLowerCase();
        nombre = nombre.trim();
        apellido = apellido.trim();
        
        if(usuarioRepository.existeEmail(email)){
            throw new RuntimeException("Ya existe una cuenta registrada con ese correo.");
        }
        
        String idUsuario = java.util.UUID.randomUUID().toString();
        String contrasenaHash = BCrypt.hashpw(password, BCrypt.gensalt());
        
        Usuario usuario = new Usuario(idUsuario, nombre, apellido, email, contrasenaHash, idRol);
        
        boolean registrado = usuarioRepository.registrarUsuario(usuario);
        
        if(!registrado){
            throw new RuntimeException("No se pudo completar el registro. Intenta de nuevo.");
        }
    }
    
}
