package main.java.com.tecnobinaryjc.abarroteria.kinal.repository.usuario;

import javafx.collections.ObservableList;
import main.java.com.tecnobinaryjc.abarroteria.kinal.model.Usuario;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import main.java.com.tecnobinaryjc.abarroteria.kinal.config.DataBaseConnection;
public class UsuarioRepository {
    
    public UsuarioRepository(){
        
    }
    
    public ObservableList<Usuario> listUsuario(){
        
        String sql = "select * from usuarios;";
        
        try(PreparedStatement pst = DataBaseConnection.getDataBaseConnection().prepareCall(sql)){
            
            ObservableList<Usuario> listaUsuarios;
            
            return null;
            
        }catch(SQLException e){
            
            System.out.println("Error al listar a los usuarios: " + e.getMessage());
            
        }
        
        return null;
    }
    
}
