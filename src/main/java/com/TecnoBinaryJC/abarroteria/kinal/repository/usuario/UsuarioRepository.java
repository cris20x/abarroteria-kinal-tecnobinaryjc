package main.java.com.TecnoBinaryJC.abarroteria.kinal.repository.usuario;

import javafx.collections.ObservableList;
import main.java.com.TecnoBinaryJC.abarroteria.kinal.model.Usuario;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import main.java.com.TecnoBinaryJC.abarroteria.kinal.config.DataBaseConnection;
public class UsuarioRepository {
    
    public UsuarioRepository(){
        
    }
    
    public ObservableList<Usuario> listUsuario(){
        
        String sql = "select * from usuarios;";
        
        try(PreparedStatement pst = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)){
            
            ObservableList<Usuario> listaUsuarios;
            
            return null;
            
        }catch(SQLException e){
            
            System.out.println("Error al listar a los usuarios: " + e.getMessage());
            
        }
        
        return null;
    }
    
    public boolean existeEmail(String email){
        
        String sql = "select 1 from usuarios where email = ? limit 1";
        
        try(PreparedStatement pst = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)){
            
            pst.setString(1, email);
            
            try(ResultSet rs = pst.executeQuery()){
                return rs.next();
            }
            
        }catch(SQLException e){
            
            System.out.println("Error al verificar el correo: " + e.getMessage());
            return false;
            
        }
    }
    
    public boolean registrarUsuario(Usuario usuario){
        
        String sql = "insert into usuarios (idusuarios, nombre, apellido, email, contrasena_hash, id_rol) values (?, ?, ?, ?, ?, ?)";
        
        try(PreparedStatement pst = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)){
            
            pst.setString(1, usuario.getIdUsuario());
            pst.setString(2, usuario.getNombre());
            pst.setString(3, usuario.getApellido());
            pst.setString(4, usuario.getEmail());
            pst.setString(5, usuario.getContrasena());
            pst.setInt(6, usuario.getIdRol());
            
            return pst.executeUpdate() > 0;
            
        }catch(SQLException e){
            
            System.out.println("Error al registrar el usuario: " + e.getMessage());
            return false;
            
        }
    }
    
}
