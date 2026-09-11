package main.java.com.tecnobinaryjc.abarroteria.kinal.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.java.com.tecnobinaryjc.abarroteria.kinal.controller.DashboardController;
import main.java.com.tecnobinaryjc.abarroteria.kinal.controller.LoginController;
import main.java.com.tecnobinaryjc.abarroteria.kinal.controller.RegisterController;
import main.java.com.tecnobinaryjc.abarroteria.kinal.controller.SplashViewController;
import main.java.com.tecnobinaryjc.abarroteria.kinal.repository.AuthRepository;
import main.java.com.tecnobinaryjc.abarroteria.kinal.repository.ProductoRepository;
import main.java.com.tecnobinaryjc.abarroteria.kinal.repository.usuario.UsuarioRepository;
import main.java.com.tecnobinaryjc.abarroteria.kinal.service.AuthService;
import main.java.com.tecnobinaryjc.abarroteria.kinal.service.dashboard.DashboardService;
import main.java.com.tecnobinaryjc.abarroteria.kinal.service.usuario.UsuarioService;

// Centraliza la carga de vistas FXML y el cambio de pantallas.
public class SceneManager {

    final String FXML_PATH = "/main/resources/view/";

    private final Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    // Muestra la pantalla de carga inicial.
    public void showSplashView() throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/main/resources/view/SplashView.fxml")
        );

        loader.setControllerFactory(clazz -> {
            if (clazz == SplashViewController.class) {
                return new SplashViewController(this);
            }

            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(
                        "Error al crear el constructor: " + e.getMessage()
                );
            }
        });

        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(500);
        stage.setResizable(true);
        stage.show();
    }

    public void showLoginView() throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/main/resources/view/login-view.fxml")
        );

        loader.setControllerFactory(clazz -> {

            if (clazz == LoginController.class) {
                AuthRepository authRepository = new AuthRepository();
                AuthService authService = new AuthService(authRepository);

                return new LoginController(authService, this);
            }

            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(
                        "Error al crear el constructor: " + e.getMessage()
                );
            }
        });

        Parent root = loader.load();
        Scene scene = new Scene(root);

        changeScene(scene, 760, 520);
    }

    public void showRegisterView() throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/main/resources/view/register-view.fxml")
        );

        loader.setControllerFactory(clazz -> {

            if (clazz == RegisterController.class) {
                UsuarioRepository usuarioRepository = new UsuarioRepository();
                UsuarioService usuarioService =
                        new UsuarioService(usuarioRepository);

                return new RegisterController(usuarioService, this);
            }

            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(
                        "Error al crear el constructor: " + e.getMessage()
                );
            }
        });

        Parent root = loader.load();
        Scene scene = new Scene(root);

        changeScene(scene, 760, 600);
    }

    public void showDashboardView() throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/main/resources/view/dashboard-view.fxml")
        );

        loader.setControllerFactory(clazz -> {

            if (clazz == DashboardController.class) {
                ProductoRepository productoRepository =
                        new ProductoRepository();

                DashboardService dashboardService =
                        new DashboardService(productoRepository);

                return new DashboardController(dashboardService, this);
            }

            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(
                        "Error al crear el constructor: " + e.getMessage()
                );
            }
        });

        Parent root = loader.load();
        Scene scene = new Scene(root);

        changeScene(scene, 820, 560);

        stage.toFront();
        stage.requestFocus();
    }


    private void changeScene(Scene newScene,
                              double minWidth,
                              double minHeight) {

        // Guardar estado actual de la ventana.
        double currentX = stage.getX();
        double currentY = stage.getY();
        double currentWidth = stage.getWidth();
        double currentHeight = stage.getHeight();

        boolean wasMaximized = stage.isMaximized();
        boolean wasFullScreen = stage.isFullScreen();

        // Cambiar la escena.
        stage.setScene(newScene);

        // Establecer únicamente los tamaños mínimos.
        // No se fuerza el tamaño actual de la ventana.
        stage.setMinWidth(minWidth);
        stage.setMinHeight(minHeight);
        stage.setResizable(true);

        /*
         * Si la ventana NO estaba maximizada ni en pantalla completa,
         * restaura exactamente su posición y tamaño anteriores.
         */
        if (!wasMaximized && !wasFullScreen) {
            stage.setX(currentX);
            stage.setY(currentY);
            stage.setWidth(Math.max(currentWidth, minWidth));
            stage.setHeight(Math.max(currentHeight, minHeight));
        }

        // Restaurar pantalla completa si estaba activa.
        if (wasFullScreen) {
            stage.setFullScreen(true);
        }

        // Restaurar maximizado si estaba activo.
        if (wasMaximized) {
            stage.setMaximized(true);
        }

        stage.show();
    }

    public void showAlertInfo(
            String head,
            String title,
            String content,
            AlertType type) {

        Alert alert = new Alert(type);

        // El Alert pertenece a la ventana principal.
        alert.initOwner(this.stage);

        alert.setTitle(title);
        alert.setHeaderText(head);
        alert.setContentText(content);

        if (alert.getDialogPane().getScene() != null) {

            alert.getDialogPane()
                    .getScene()
                    .getStylesheets()
                    .add(
                            getClass()
                                    .getResource(
                                            "/main/resources/css/login-view.css"
                                    )
                                    .toExternalForm()
                    );
        }

        alert.showAndWait();
    }
}