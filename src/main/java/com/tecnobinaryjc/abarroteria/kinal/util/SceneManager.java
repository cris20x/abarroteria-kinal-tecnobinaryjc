package main.java.com.tecnobinaryjc.abarroteria.kinal.util;

import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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

public class SceneManager {

    final String FXML_PATH = "/main/resources/view/";

    private final Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

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
        applyStageSize(800, 500);
        stage.centerOnScreen();
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

        stage.setScene(scene);
        applyStageSize(760, 520);
        stage.centerOnScreen();
        stage.show();
    }

    public void showRegisterView() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/main/resources/view/register-view.fxml")
        );

        loader.setControllerFactory(clazz -> {
            if (clazz == RegisterController.class) {
                UsuarioRepository usuarioRepository = new UsuarioRepository();
                UsuarioService usuarioService = new UsuarioService(usuarioRepository);
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

        stage.setScene(scene);
        applyStageSize(760, 600);
        stage.centerOnScreen();
        stage.show();
    }

    public void showDashboardView() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/main/resources/view/dashboard-view.fxml")
        );

        loader.setControllerFactory(clazz -> {
            if (clazz == DashboardController.class) {
                ProductoRepository productoRepository = new ProductoRepository();
                DashboardService dashboardService =
                        new DashboardService(productoRepository);

                return new DashboardController(dashboardService, this);
            }

            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(
                        "Error al crear el constructor " + e.getMessage()
                );
            }
        });

        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        applyStageSize(820, 560);
        stage.centerOnScreen();
        stage.show();
        stage.toFront();
        stage.requestFocus();
    }

    private void applyStageSize(double minWidth, double minHeight) {
        stage.setMinWidth(minWidth);
        stage.setMinHeight(minHeight);

        if (stage.getWidth() < minWidth || stage.getHeight() < minHeight) {
            stage.setWidth(Math.max(minWidth, stage.getWidth()));
            stage.setHeight(Math.max(minHeight, stage.getHeight()));
        }

        stage.setResizable(true);
    }

    public void showAlertInfo(
            String head,
            String title,
            String content,
            AlertType type) {

        Alert alert = new Alert(type);

        alert.initOwner(this.stage);
        alert.setTitle(title);
        alert.setHeaderText(head);
        alert.setContentText(content);

        if (alert.getDialogPane().getScene() != null) {
            alert.getDialogPane().getScene().getStylesheets().add(
                    getClass().getResource(
                            "/main/resources/css/login-view.css"
                    ).toExternalForm()
            );
        }

        alert.showAndWait();
    }

    public boolean showConfirmacion(
            String head,
            String title,
            String content) {

        Alert alert = new Alert(AlertType.INFORMATION);

        alert.initOwner(this.stage);
        alert.setTitle(title);
        alert.setHeaderText(head);
        alert.setContentText(content);

        alert.getButtonTypes().setAll(
                ButtonType.YES,
                ButtonType.NO
        );

        if (alert.getDialogPane().getScene() != null) {
            alert.getDialogPane().getScene().getStylesheets().add(
                    getClass().getResource(
                            "/main/resources/css/login-view.css"
                    ).toExternalForm()
            );
        }

        Optional<ButtonType> resultado = alert.showAndWait();

        return resultado.isPresent()
                && resultado.get() == ButtonType.YES;
    }
}