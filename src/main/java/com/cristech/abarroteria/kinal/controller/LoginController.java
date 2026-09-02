package main.java.com.cristech.abarroteria.kinal.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.cristech.abarroteria.kinal.dto.request.LoginDTORequest;
import main.java.com.cristech.abarroteria.kinal.dto.response.LoginDTOResponse;
import main.java.com.cristech.abarroteria.kinal.service.AuthService;
import main.java.com.cristech.abarroteria.kinal.util.SceneManager;

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
        // TODO
    }
    
    public void handleLogin()throws Exception{
        if(txtFieldEmail.getText().isEmpty() || txtFieldEmail.getText().isBlank()){
            sceneManager.showAlertInfo("Hay campos sin llenas", "No puedes dejar campos en blanco", "Intenta de nuevo", Alert.AlertType.INFORMATION);
        }else{
            try{
            LoginDTOResponse response = authService.login(new LoginDTORequest(txtFieldEmail.getText(), txtFieldPassword.getText()));
            
            if(response == null){
            
          sceneManager.showAlertInfo("Error al iniciar", "Campos invalidos", "No se ha podido iniciar sesión", Alert.AlertType.ERROR);

            
            }else{
               System.out.println(response);
            sceneManager.showAlertInfo("Bienvenido" + response.getNombre(), "Es bueno verte", "Inicio de sesión correcto", Alert.AlertType.CONFIRMATION);
            sceneManager.showDashboardView();
            
            }
             
            }catch(RuntimeException e){
                e.printStackTrace();
                sceneManager.showAlertInfo("Error al iniciar sesión", "Verificar campos", "No se ha podido iniciar sesión", Alert.AlertType.ERROR);
            }
            
            
        }
        
    }
    
    /*public void handleLogin() {

        try {

            // Validar correo
            if (txtFieldEmail.getText() == null
                    || txtFieldEmail.getText().isBlank()) {

                throw new RuntimeException("El correo electrónico es obligatorio.");
            }

            // Validar contraseña
            if (txtFieldPassword.getText() == null
                    || txtFieldPassword.getText().isBlank()) {

                throw new RuntimeException("La contraseña es obligatoria.");
            }

            // Crear solicitud de login
            LoginDTORequest request = new LoginDTORequest(
                    txtFieldEmail.getText().trim(),
                    txtFieldPassword.getText()
            );

            // Intentar iniciar sesión
            LoginDTOResponse response = authService.login(request);

            // Mostrar información del usuario
            System.out.println(
                    "Nombre del usuario que inició sesión: "
                    + response.getNombre()
                    + " "
                    + response.getNombreRol()
            );

        } catch (Exception e) {

            System.out.println("Error al iniciar sesión: " + e.getMessage());

        }
    }*/
    
}