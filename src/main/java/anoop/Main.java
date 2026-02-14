package anoop;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Anoop anoop = new Anoop();

    @Override
    public void start(Stage stage) {
        assert stage != null : "stage must not be null";
        try {
            java.net.URL fxml = Main.class.getResource("/view/MainWindow.fxml");
            assert fxml != null : "MainWindow.fxml must be present on classpath";
            FXMLLoader fxmlLoader = new FXMLLoader(fxml);
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setAnoop(anoop);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
