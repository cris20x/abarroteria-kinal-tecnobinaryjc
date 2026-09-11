package main.java.com.tecnobinaryjc.abarroteria.kinal.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import main.java.com.tecnobinaryjc.abarroteria.kinal.util.SceneManager;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class SplashViewController implements Initializable {
/*PANTALLA DE CARGA INICIAL */
    @FXML private ImageView imgMain;
    @FXML private ImageView imgRed;
    @FXML private ImageView imgBlue;
    @FXML private ProgressBar progressBar;

    private final SceneManager sceneManager;
    private final Random random = new Random();

    public SplashViewController(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ColorAdjust redFilter = new ColorAdjust();
        redFilter.setHue(-0.5);      
        redFilter.setSaturation(1.0); 
        imgRed.setEffect(redFilter);

        ColorAdjust blueFilter = new ColorAdjust();
        blueFilter.setHue(0.2);       
        blueFilter.setSaturation(1.0); 
        imgBlue.setEffect(blueFilter);


        Timeline glitchTimeline = new Timeline(
            new KeyFrame(Duration.millis(60), e -> applyRgbGlitch())
        );
        glitchTimeline.setCycleCount(Timeline.INDEFINITE);
        glitchTimeline.play();


        Timeline progressTimeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
            new KeyFrame(Duration.seconds(1.5), new KeyValue(progressBar.progressProperty(), 1.0))
        );

        progressTimeline.setOnFinished(event -> {
            glitchTimeline.stop();

            imgRed.setTranslateX(0);
            imgBlue.setTranslateX(0);
            try {
                sceneManager.showLoginView();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        progressTimeline.play();
    }


    private void applyRgbGlitch() {
        if (random.nextInt(10) > 4) { 
            double redOffset = -45 + random.nextDouble() * 90;
            double blueOffset = -45 + random.nextDouble() * 90;

            imgRed.setTranslateX(redOffset);
            imgBlue.setTranslateX(blueOffset);
        } else {

            imgRed.setTranslateX(0);
            imgBlue.setTranslateX(0);
        }
    }
}