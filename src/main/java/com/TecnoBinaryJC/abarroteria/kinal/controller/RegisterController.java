package main.java.com.TecnoBinaryJC.abarroteria.kinal.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
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
    @FXML
    private ComboBox<String> comboBoxRol;

    public RegisterController(UsuarioService usuarioService, SceneManager sceneManager) {
        this.usuarioService = usuarioService;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxRol.setItems(FXCollections.observableArrayList(
                "1. Administrador", "2. Cliente"
        ));
        comboBoxRol.setValue("2. Cliente");
    }

    @FXML
    public void handleRegistrar() throws Exception {

        String nombre = txtFieldNombre.getText();
        String apellido = txtFieldApellido.getText();
        String email = txtFieldEmail.getText();
        String password = txtFieldPassword.getText();
        String confirmPassword = txtFieldConfirmPassword.getText();
        String rolSeleccionado = comboBoxRol.getValue();

        if (rolSeleccionado == null || rolSeleccionado.isBlank()) {
            sceneManager.showAlertInfo("Rol requerido", "Selecciona un rol", "Debes seleccionar Administrador o Cliente.", Alert.AlertType.WARNING);
            return;
        }
        int idRol = rolSeleccionado.startsWith("1.") ? 1 : 2;

        if (password == null || password.isBlank() || !password.equals(confirmPassword)) {
            sceneManager.showAlertInfo(
                    "Contraseñas distintas",
                    "Revisa la contraseña",
                    "La contraseña y su confirmación no coinciden.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        try {
            usuarioService.registrar(nombre, apellido, email, password, idRol);

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
