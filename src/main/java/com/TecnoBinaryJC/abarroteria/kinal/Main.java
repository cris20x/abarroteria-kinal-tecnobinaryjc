package main.java.com.TecnoBinaryJC.abarroteria.kinal;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.com.TecnoBinaryJC.abarroteria.kinal.util.SceneManager;

public class Main extends Application{
    
    private Stage stage;    

    @Override
    public void start(Stage stage) throws Exception{
        this.stage = stage;
        SceneManager sceneManager = new SceneManager(stage);
        sceneManager.showLoginView();
        stage.show();
    }    

    public static void main(String[] args) {
        
        launch();
        
    }
    
}