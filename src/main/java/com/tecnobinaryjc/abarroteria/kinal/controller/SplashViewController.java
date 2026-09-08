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
        // 1. Teñir las capas para el efecto anaglifo (Rojo y Azul/Cian)
        ColorAdjust redFilter = new ColorAdjust();
        redFilter.setHue(-0.9);      // Tono Rojo
        redFilter.setSaturation(1.0); // Máxima saturación
        imgRed.setEffect(redFilter);

        ColorAdjust blueFilter = new ColorAdjust();
        blueFilter.setHue(0.8);       // Tono Azul / Cyan
        blueFilter.setSaturation(1.0); // Máxima saturación
        imgBlue.setEffect(blueFilter);

        // 2. Animación de desfase horizontal para el Glitch RGB
        Timeline glitchTimeline = new Timeline(
            new KeyFrame(Duration.millis(50), e -> applyRgbGlitch())
        );
        glitchTimeline.setCycleCount(Timeline.INDEFINITE);
        glitchTimeline.play();

        // 3. Llenado de barra de carga (2.5 segundos)
        Timeline progressTimeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
            new KeyFrame(Duration.seconds(3.0), new KeyValue(progressBar.progressProperty(), 1.0))
        );

        progressTimeline.setOnFinished(event -> {
            glitchTimeline.stop();
            // Restaurar posiciones al finalizar
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

    /**
     * Aplica desfases horizontales rápidos e impredecibles a las capas roja y azul.
     */
    private void applyRgbGlitch() {
        if (random.nextInt(10) > 4) { // Frecuencia del glitch
            // Desfases horizontales entre -8px y +8px
            double redOffset = -8 + random.nextDouble() * 16;
            double blueOffset = -8 + random.nextDouble() * 16;

            imgRed.setTranslateX(redOffset);
            imgBlue.setTranslateX(blueOffset);
        } else {
            // Regresan al centro
            imgRed.setTranslateX(0);
            imgBlue.setTranslateX(0);
        }
    }
}