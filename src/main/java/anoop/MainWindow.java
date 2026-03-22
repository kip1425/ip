package anoop;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Anoop anoop;

    private Image userImage = new Image(this.getClass().getResourceAsStream(
            "/images/Bob.jpg"));
    private Image anoopImage = new Image(this.getClass().getResourceAsStream(
            "/images/AnoopBot.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Anoop instance */
    public void setAnoop(Anoop anoop) {
        this.anoop = anoop;

        dialogContainer.getChildren().addAll(
                DialogBox.getAnoopDialog(anoop.greet(), anoopImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = anoop.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getAnoopDialog(response, anoopImage)
        );
        userInput.clear();

        if (anoop.consumeExitRequest()) {
            Stage stage = (Stage) dialogContainer.getScene().getWindow();
            stage.close();
        }
    }
}

