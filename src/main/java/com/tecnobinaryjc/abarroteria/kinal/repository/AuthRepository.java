package main.java.com.tecnobinaryjc.abarroteria.kinal.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.java.com.tecnobinaryjc.abarroteria.kinal.config.DataBaseConnection;
import main.java.com.tecnobinaryjc.abarroteria.kinal.dto.request.LoginDTORequest;
import main.java.com.tecnobinaryjc.abarroteria.kinal.dto.response.LoginDTOResponse;

// Acceso a datos para autenticación de usuarios.
public class AuthRepository {

    // Busca un usuario por email y trae su hash y rol para el login.
    public LoginDTOResponse findUserByEmail(LoginDTORequest request) {

        String sql = "SELECT u.nombre, u.apellido, u.contrasena_hash, r.nombre_rol FROM usuarios u INNER JOIN roles r ON u.id_rol = r.id_rol WHERE LOWER(TRIM(u.email)) = LOWER(TRIM(?))";

        try (PreparedStatement pstm = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {

            pstm.setString(1, request.getEmail());

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return new LoginDTOResponse(
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("contrasena_hash"),
                            rs.getString("nombre_rol")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el usuario: " + e.getMessage());
        }

        return null;
    }

}
