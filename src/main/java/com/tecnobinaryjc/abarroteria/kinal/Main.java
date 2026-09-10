package main.java.com.tecnobinaryjc.abarroteria.kinal;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.com.tecnobinaryjc.abarroteria.kinal.util.SceneManager;

// Punto de entrada de la aplicación JavaFX.
public class Main extends Application {

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/main/resources/img/LogoAbarroteriaTCBinary.png")));

        SceneManager sceneManager = new SceneManager(stage);
        
        sceneManager.showSplashView();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}