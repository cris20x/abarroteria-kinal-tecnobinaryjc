package main.java.com.tecnobinaryjc.abarroteria.kinal.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.tecnobinaryjc.abarroteria.kinal.dto.request.LoginDTORequest;
import main.java.com.tecnobinaryjc.abarroteria.kinal.dto.response.LoginDTOResponse;
import main.java.com.tecnobinaryjc.abarroteria.kinal.service.AuthService;
import main.java.com.tecnobinaryjc.abarroteria.kinal.util.SceneManager;

// Controlador de la pantalla de inicio de sesión.
public class LoginController implements Initializable {
    
    private final AuthService authService;
    private final SceneManager sceneManager;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private TextField txtFieldEmail ;
    @FXML
    private PasswordField txtFieldPassword;

    public LoginController(AuthService authService, SceneManager sceneManager) {
        this.authService = authService;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Sin configuración inicial adicional.
    }

    // Navega a la pantalla de registro.
    public void handleRegistrar() throws Exception {
        sceneManager.showRegisterView();
    }

    // Valida los campos e intenta iniciar sesión.
    public void handleLogin() throws Exception {
        String email = txtFieldEmail.getText() == null ? "" : txtFieldEmail.getText().trim();
        String password = txtFieldPassword.getText() == null ? "" : txtFieldPassword.getText();
        if (email.isBlank() || password.isBlank()) {
            sceneManager.showAlertInfo("Campos incompletos", "Completa la información", "Ingresa tu correo electrónico y contraseña.", Alert.AlertType.WARNING);
            return;
        } else {
            try{
            LoginDTOResponse response = authService.login(new LoginDTORequest(email, password));

            if(response == null){
                sceneManager.showAlertInfo("Error al iniciar", "Campos invalidos", "No se ha podido iniciar sesión", Alert.AlertType.ERROR);
            }else{
                sceneManager.showAlertInfo("Bienvenido " + response.getNombre(), "Es bueno verte", "Inicio de sesión correcto", Alert.AlertType.CONFIRMATION);
                sceneManager.showDashboardView();
            }

            }catch(RuntimeException e){
                sceneManager.showAlertInfo("Error al iniciar sesión", "Verifica tus credenciales", "No se ha podido iniciar sesión. Revisa tu correo y contraseña.", Alert.AlertType.ERROR);
            }
        }
    }

}