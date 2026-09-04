package main.java.com.TecnoBinaryJC.abarroteria.kinal.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.TecnoBinaryJC.abarroteria.kinal.service.usuario.UsuarioService;
import main.java.com.TecnoBinaryJC.abarroteria.kinal.util.SceneManager;

public class RegisterController implements Initializable {

    private final UsuarioService usuarioService;
    private final SceneManager sceneManager;

    @FXML
    private TextField txtFieldNombre;
    @FXML
    private TextField txtFieldApellido;
    @FXML
    private TextField txtFieldEmail;
    @FXML
    private PasswordField txtFieldPassword;
    @FXML
    private PasswordField txtFieldConfirmPassword;

    public RegisterController(UsuarioService usuarioService, SceneManager sceneManager) {
        this.usuarioService = usuarioService;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void handleRegistrar() throws Exception {

        String nombre = txtFieldNombre.getText();
        String apellido = txtFieldApellido.getText();
        String email = txtFieldEmail.getText();
        String password = txtFieldPassword.getText();
        String confirmPassword = txtFieldConfirmPassword.getText();

        if (password == null || !password.equals(confirmPassword)) {
            sceneManager.showAlertInfo(
                    "Contraseñas distintas",
                    "Revisa la contraseña",
                    "La contraseña y su confirmación no coinciden.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        try {
            usuarioService.registrar(nombre, apellido, email, password);

            sceneManager.showAlertInfo(
                    "Registro exitoso",
                    "Cuenta creada",
                    "Ya puedes iniciar sesión con tu correo y contraseña.",
                    Alert.AlertType.INFORMATION
            );

            sceneManager.showLoginView();

        } catch (RuntimeException e) {
            sceneManager.showAlertInfo(
                    "No se pudo registrar",
                    "Verifica los datos",
                    e.getMessage(),
                    Alert.AlertType.ERROR
            );
        }
    }

    @FXML
    public void handleVolver() throws Exception {
        sceneManager.showLoginView();
    }

}
