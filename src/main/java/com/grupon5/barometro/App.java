package com.grupon5.barometro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        App.stage =stage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("vistaBarometro.fxml"));
        fxmlLoader.setResources(ResourceBundle
            .getBundle("com.grupon5.barometro.i18n/cadenas", Locale.getDefault()));
        Parent raiz = fxmlLoader.load();

        scene = new Scene(raiz);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public static Stage getPrimaryStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch();
    }

}
