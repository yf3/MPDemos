import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class AppViewController {

    private static final String TOKEN_SEPARATOR = " ";
    public static final int MAX_TRACK_LENGTH = 30;
    private int currentTrackLength;

    @FXML
    private TextArea trackContent;
    @FXML
    private Rectangle cKey;
    @FXML
    private Rectangle dKey;
    @FXML
    private Rectangle eKey;
    @FXML
    private Rectangle fKey;
    @FXML
    private Rectangle gKey;
    @FXML
    private Rectangle aKey;
    @FXML
    private Rectangle bKey;
    @FXML
    private ChoiceBox<String> trackChooser;
    @FXML
    private ChoiceBox<String> pitchLevelChooser;

    @FXML
    private void initialize() {
        pitchLevelChooser.setItems(FXCollections.observableArrayList("2", "4"));
        pitchLevelChooser.getSelectionModel().selectLast();

        trackChooser.setItems(FXCollections.observableArrayList("Track1", "Track2"));
        trackChooser.getSelectionModel().selectFirst();

    }

    @FXML
    public void onKeyClicked(MouseEvent e) {
        String noteToken = createNoteToken(e);
        trackContent.setText(trackContent.getText() + TOKEN_SEPARATOR + noteToken);
    }

    private String createNoteToken(MouseEvent e) {
        Rectangle key = (Rectangle) e.getSource();
        return key.getId() + "." + pitchLevelChooser.getValue();
    }

    @FXML
    public void onSaveClicked() {
        trackContent.getText();
    }

}
