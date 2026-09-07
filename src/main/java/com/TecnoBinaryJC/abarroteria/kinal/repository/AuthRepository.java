package main.java.com.TecnoBinaryJC.abarroteria.kinal.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.java.com.TecnoBinaryJC.abarroteria.kinal.config.DataBaseConnection;
import main.java.com.TecnoBinaryJC.abarroteria.kinal.dto.request.LoginDTORequest;
import main.java.com.TecnoBinaryJC.abarroteria.kinal.dto.response.LoginDTOResponse;

public class AuthRepository {
    
    public LoginDTOResponse findUserByEmail(LoginDTORequest request){
        
        String sql = "SELECT u.nombre, u.apellido, u.contrasena_hash, r.nombre_rol FROM usuarios u INNER JOIN roles r ON u.id_rol = r.id_rol WHERE LOWER(TRIM(u.email)) = LOWER(TRIM(?))";
        
        try(PreparedStatement pstm = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)){
            
            pstm.setString(1, request.getEmail());
            ResultSet rs = pstm.executeQuery();
            
            if(rs.next()){
                
                return new LoginDTOResponse(
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("contrasena_hash"),
                rs.getString("nombre_rol")
                );
                
            }
            
        }catch(SQLException e){
                    System.out.println(e.getMessage());
                    System.out.println("Error al buscar el usuario.");
                    
                    }
        System.out.println("XD");
        return null;
    }
    
}
