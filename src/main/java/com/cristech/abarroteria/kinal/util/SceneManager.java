package main.java.com.cristech.abarroteria.kinal.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.com.cristech.abarroteria.kinal.controller.DashboardController;
import main.java.com.cristech.abarroteria.kinal.controller.LoginController;
import main.java.com.cristech.abarroteria.kinal.repository.AuthRepository;
import main.java.com.cristech.abarroteria.kinal.repository.ProductoRepository;
import main.java.com.cristech.abarroteria.kinal.repository.usuario.UsuarioRepository;
import main.java.com.cristech.abarroteria.kinal.service.AuthService;
import main.java.com.cristech.abarroteria.kinal.service.dashboard.DashboardService;
import main.java.com.cristech.abarroteria.kinal.service.usuario.UsuarioService;

public class SceneManager {
    
    final String FXML_PATH = "/main/resources/view/";
    private final int DASHBOARD_WIDTH = 1024;
    private final int DASHBOARD_HEIGHT = 768;

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
        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);  
        stage.initStyle(StageStyle.TRANSPARENT);
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
        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        stage.toFront();
        stage.requestFocus();
    }
    
    
    public void showAlertInfo(String head, String title, String content, AlertType type){
        Alert alert = new Alert(type);
        alert.initOwner(this.stage);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
}