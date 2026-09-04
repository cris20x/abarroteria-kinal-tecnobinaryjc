package main.java.com.TecnoBinaryJC.abarroteria.kinal.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.java.com.TecnoBinaryJC.abarroteria.kinal.controller.DashboardController;
import main.java.com.TecnoBinaryJC.abarroteria.kinal.controller.LoginController;
import main.java.com.TecnoBinaryJC.abarroteria.kinal.controller.RegisterController;
import main.java.com.TecnoBinaryJC.abarroteria.kinal.repository.AuthRepository;
import main.java.com.TecnoBinaryJC.abarroteria.kinal.repository.ProductoRepository;
import main.java.com.TecnoBinaryJC.abarroteria.kinal.repository.usuario.UsuarioRepository;
import main.java.com.TecnoBinaryJC.abarroteria.kinal.service.AuthService;
import main.java.com.TecnoBinaryJC.abarroteria.kinal.service.dashboard.DashboardService;
import main.java.com.TecnoBinaryJC.abarroteria.kinal.service.usuario.UsuarioService;

public class SceneManager {
    
    final String FXML_PATH = "/main/resources/view/";

    private final Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }
    
    public void showLoginView() throws Exception{
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/login-view.fxml"));
        
        loader.setControllerFactory(
                
        clazz -> {
            if(clazz == LoginController.class){    
                AuthRepository authRepository = new AuthRepository();
                AuthService authService = new AuthService(authRepository);
                return new LoginController(authService, this);
            }
            
            try{
                return clazz.getDeclaredConstructor().newInstance();
            }catch(Exception e){   
                throw new RuntimeException("Error al crear el constructor: " + e.getMessage());
            }
            
        });
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);  
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
        
    }
    
    public void showRegisterView() throws Exception{
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/register-view.fxml"));
        
        loader.setControllerFactory(
                
        clazz -> {
            if(clazz == RegisterController.class){    
                UsuarioRepository usuarioRepository = new UsuarioRepository();
                UsuarioService usuarioService = new UsuarioService(usuarioRepository);
                return new RegisterController(usuarioService, this);
            }
            
            try{
                return clazz.getDeclaredConstructor().newInstance();
            }catch(Exception e){   
                throw new RuntimeException("Error al crear el constructor: " + e.getMessage());
            }
            
        });
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
        
    }
    
    public void showDashboardView()throws Exception{
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/dashboard-view.fxml"));
                
        loader.setControllerFactory(
        clazz -> {
if (clazz == DashboardController.class) {
                    ProductoRepository productoRepository = new ProductoRepository();
                    DashboardService dashboardService = new DashboardService(productoRepository);
                    return new DashboardController(dashboardService, this);
                }
                try {
                    return clazz.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Error al crear el constructor " + e.getMessage());
                }
            });
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
        stage.toFront();
        stage.requestFocus();
    }
    
    
    public void showAlertInfo(String head, String title, String content, AlertType type){
        Alert alert = new Alert(type);
        alert.initOwner(this.stage);
        alert.setTitle(title);
        alert.setHeaderText(head);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
}