import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class AppViewController {

    private static final String TOKEN_SEPARATOR = " ";

    @FXML
    private ChoiceBox<String> trackChooser;
    @FXML
    private ChoiceBox<String> pitchLevelChooser;
    @FXML
    private ChoiceBox<String> delayedTrackChooser;
    @FXML
    private ChoiceBox<Integer> fourierFrequencyChooser;
    @FXML
    private TextArea trackContent;
    @FXML
    private TextField delayInputField;
    @FXML
    private Button playTrack1Btn;
    @FXML
    private Button showTrack1Btn;
    @FXML
    private Button playTrack2Btn;
    @FXML
    private Button showTrack2Btn;
    @FXML
    private Button playTogetherBtn;

    @FXML
    private void initialize() {
        ObservableList<String> trackList = FXCollections.observableArrayList("Track1", "Track2");
        pitchLevelChooser.setItems(FXCollections.observableArrayList("2", "4"));
        pitchLevelChooser.getSelectionModel().selectLast();

        trackChooser.setItems(trackList);
        trackChooser.getSelectionModel().selectFirst();

        delayedTrackChooser.setItems(trackList);
        delayedTrackChooser.getSelectionModel().selectLast();

        fourierFrequencyChooser.setItems(FXCollections.observableArrayList(0, 100, 200, 500, 800));
        fourierFrequencyChooser.getSelectionModel().selectFirst();
    }

    @FXML
    public void onKeyClicked(MouseEvent e) {
        String noteToken = createNoteToken(e);
        trackContent.setText(trackContent.getText() + TOKEN_SEPARATOR + noteToken);
    }

    @FXML
    public void onAddRestClicked() {
        trackContent.setText(trackContent.getText() + TOKEN_SEPARATOR + "R");
    }

    private String createNoteToken(MouseEvent e) {
        Rectangle key = (Rectangle) e.getSource();
        return key.getId() + "." + pitchLevelChooser.getValue();
    }

    @FXML
    public void onDeleteClicked() {
        String currentText = trackContent.getText();
        if (!currentText.isEmpty()) {
            trackContent.setText(currentText.substring(0, currentText.lastIndexOf(TOKEN_SEPARATOR)));
        }
    }

    @FXML
    public void onSaveClicked() {

    }

}
