package main.java.com.tecnobinaryjc.abarroteria.kinal.repository.usuario;

import javafx.collections.ObservableList;
import main.java.com.tecnobinaryjc.abarroteria.kinal.model.Usuario;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import main.java.com.tecnobinaryjc.abarroteria.kinal.config.DataBaseConnection;

public class UsuarioRepository {

    public UsuarioRepository() {

    }

    public ObservableList<Usuario> listUsuario() {

        String sql = "SELECT * FROM usuarios;";

        try (PreparedStatement pst =
                DataBaseConnection.getDataBaseConnection().prepareCall(sql)) {

            ObservableList<Usuario> listaUsuarios;

            return null;

        } catch (SQLException e) {

            System.out.println("Error al listar a los usuarios: " + e.getMessage());

        }

        return null;
    }

    public void registrar(String nombre, String apellido, String email,
                          String contrasena, int idRol) {

        String sql = "INSERT INTO usuarios " +
                     "(nombre, apellido, email, contrasena_hash, id_rol) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pst =
                DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {

            pst.setString(1, nombre);
            pst.setString(2, apellido);
            pst.setString(3, email);
            pst.setString(4, contrasena);
            pst.setInt(5, idRol);

            pst.executeUpdate();

            System.out.println("Usuario registrado correctamente.");

        } catch (SQLException e) {

            System.out.println("Error al registrar usuario: " + e.getMessage());
        }
    }
}
