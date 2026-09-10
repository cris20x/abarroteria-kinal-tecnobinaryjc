package main.java.com.tecnobinaryjc.abarroteria.kinal.repository.usuario;

import javafx.collections.ObservableList;
import main.java.com.tecnobinaryjc.abarroteria.kinal.model.Usuario;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import main.java.com.tecnobinaryjc.abarroteria.kinal.config.DataBaseConnection;
import javafx.collections.FXCollections;

// Acceso a datos para usuarios (listado, verificación de email y registro).
public class UsuarioRepository {

    // Trae todos los usuarios registrados.
    public ObservableList<Usuario> listUsuario() {

        String sql = "SELECT id_usuario, nombre, apellido, email, contrasena_hash, id_rol FROM usuarios";
        ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();

        try (PreparedStatement pst = DataBaseConnection.getDataBaseConnection().prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                listaUsuarios.add(new Usuario(
                        rs.getString("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("contrasena_hash"),
                        rs.getInt("id_rol")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar a los usuarios: " + e.getMessage());
        }

        return listaUsuarios;
    }

    // Verifica si ya existe un usuario con el email dado.
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
    
    // Inserta un nuevo usuario en la base de datos.
    public boolean registrarUsuario(Usuario usuario){
        
        String sql = "insert into usuarios (id_usuario, nombre, apellido, email, contrasena_hash, id_rol) values (?, ?, ?, ?, ?, ?)";
        
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
