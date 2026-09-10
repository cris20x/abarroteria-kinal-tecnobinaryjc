package main.java.com.tecnobinaryjc.abarroteria.kinal.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import main.java.com.tecnobinaryjc.abarroteria.kinal.service.usuario.UsuarioService;
import main.java.com.tecnobinaryjc.abarroteria.kinal.util.SceneManager;

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
    private ComboBox<String> comboBoxExtension;

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
                "1. Administrador",
                "2. Cliente"
        ));

        comboBoxRol.setValue("2. Cliente");

        comboBoxExtension.setItems(FXCollections.observableArrayList(
                "@gmail.com",
                "@kinal.edu.gt"
        ));

        comboBoxExtension.setValue("@gmail.com");

        UnaryOperator<TextFormatter.Change> filtroCorreo = change -> {

            String texto = change.getControlNewText();

            if (texto.matches("[A-Za-z0-9.]*")) {
                return change;
            }

            return null;
        };

        txtFieldEmail.setTextFormatter(
                new TextFormatter<String>(filtroCorreo)
        );
    }

    @FXML
    public void handleRegistrar() throws Exception {

        String nombre = txtFieldNombre.getText().trim();
        String apellido = txtFieldApellido.getText().trim();
        String usuarioCorreo = txtFieldEmail.getText().trim();
        String extension = comboBoxExtension.getValue();
        String password = txtFieldPassword.getText();
        String confirmPassword = txtFieldConfirmPassword.getText();
        String rolSeleccionado = comboBoxRol.getValue();

        if (nombre.isBlank() || apellido.isBlank()) {

            sceneManager.showAlertInfo(
                    "Datos incompletos",
                    "Completa la información",
                    "Ingresa tu nombre y apellido.",
                    Alert.AlertType.WARNING
            );

            return;
        }

        if (usuarioCorreo.isBlank()) {

            sceneManager.showAlertInfo(
                    "Correo requerido",
                    "Ingresa tu correo",
                    "Debes escribir la parte inicial de tu correo electrónico.",
                    Alert.AlertType.WARNING
            );

            return;
        }

        if (!usuarioCorreo.matches("[A-Za-z0-9]+(?:[A-Za-z0-9.]*[A-Za-z0-9])?")) {

            sceneManager.showAlertInfo(
                    "Correo inválido",
                    "Revisa el correo",
                    "El correo solo puede contener letras, números y puntos. No escribas el símbolo @.",
                    Alert.AlertType.WARNING
            );

            return;
        }

        if (usuarioCorreo.contains("..")) {

            sceneManager.showAlertInfo(
                    "Correo inválido",
                    "Revisa el correo",
                    "El correo no puede contener puntos consecutivos.",
                    Alert.AlertType.WARNING
            );

            return;
        }

        if (extension == null || extension.isBlank()) {

            sceneManager.showAlertInfo(
                    "Extensión requerida",
                    "Selecciona una extensión",
                    "Debes seleccionar @gmail.com o @kinal.edu.gt.",
                    Alert.AlertType.WARNING
            );

            return;
        }

        String email = usuarioCorreo + extension;

        if (!email.matches("^[A-Za-z0-9]+(?:[A-Za-z0-9.]*[A-Za-z0-9])?@(gmail\\.com|kinal\\.edu\\.gt)$")) {

            sceneManager.showAlertInfo(
                    "Correo inválido",
                    "Extensión no permitida",
                    "Solo se permiten las extensiones @gmail.com y @kinal.edu.gt.",
                    Alert.AlertType.WARNING
            );

            return;
        }

        if (password == null || password.isBlank() || !password.equals(confirmPassword)) {

            sceneManager.showAlertInfo(
                    "Contraseñas distintas",
                    "Revisa la contraseña",
                    "La contraseña y su confirmación no coinciden.",
                    Alert.AlertType.WARNING
            );

            return;
        }

        if (rolSeleccionado == null || rolSeleccionado.isBlank()) {

            sceneManager.showAlertInfo(
                    "Rol requerido",
                    "Selecciona un rol",
                    "Debes seleccionar Administrador o Cliente.",
                    Alert.AlertType.WARNING
            );

            return;
        }

        int idRol = rolSeleccionado.startsWith("1.") ? 1 : 2;

        try {

            usuarioService.registrar(
                    nombre,
                    apellido,
                    email,
                    password,
                    idRol
            );

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