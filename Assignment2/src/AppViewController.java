import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;
import yf3.audio.AudioByteData;
import yf3.audio.AudioManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class AppViewController {

    private static final String TOKEN_SEPARATOR = " ";
    private static final String trackOneName = "Track1";
    private static final String trackTwoName = "Track2";

    private AudioManager trackOneManager;
    private AudioManager trackTwoManager;
    private AudioManager trackTogetherManager;

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
    private Button playTrackOneBtn;
    @FXML
    private Button showTrackOneBtn;
    @FXML
    private Button playTrackTwoBtn;
    @FXML
    private Button showTrackTwoBtn;
    @FXML
    private Button playTogetherBtn;

    private HashMap<String, AudioManager> trackMap;

    @FXML
    private void initialize() {
        trackOneManager = new AudioManager();
        trackTwoManager = new AudioManager();
        trackTogetherManager = new AudioManager();

        ObservableList<String> trackList = FXCollections.observableArrayList(trackOneName, trackTwoName);
        pitchLevelChooser.setItems(FXCollections.observableArrayList("2", "4", "6"));
        pitchLevelChooser.getSelectionModel().select("4");

        trackChooser.setItems(trackList);
        trackChooser.getSelectionModel().selectFirst();

        delayedTrackChooser.setItems(trackList);
        delayedTrackChooser.getSelectionModel().selectLast();

        fourierFrequencyChooser.setItems(FXCollections.observableArrayList(0, 100, 200, 500, 800));
        fourierFrequencyChooser.getSelectionModel().selectFirst();

        trackMap = new HashMap<>();
        trackMap.put(trackOneName, trackOneManager);
        trackMap.put(trackTwoName, trackTwoManager);
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
    public void onAddRestClicked() {
        trackContent.setText(trackContent.getText() + TOKEN_SEPARATOR + "R");
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
        AudioManager target = trackMap.get(trackChooser.getValue());
        try {
            target.buildAudioData(trackContent.getText(), fourierFrequencyChooser.getValue());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if (trackChooser.getValue().equals(trackOneName)) {
            playTrackOneBtn.setDisable(false);
        }
        else if (trackChooser.getValue().equals(trackTwoName)) {
            playTrackTwoBtn.setDisable(false);
        }
    }

    @FXML
    public void onPlayTrackOneClicked() {
        String wavPath = "./" + trackOneName + ".wav";
        trackOneManager.dataToWav(wavPath);
        AudioClip audioClip = new AudioClip(new File(wavPath).toURI().toString());
        audioClip.play();
    }

    @FXML
    public void onPlayTrackTwoClicked() {
        String wavPath = "./" + trackTwoName + ".wav";
        trackTwoManager.dataToWav(wavPath);
        AudioClip audioClip = new AudioClip(new File(wavPath).toURI().toString());
        audioClip.play();
    }

    @FXML
    public void onPlayTogetherClicked() throws IOException {
        AudioManager delayedVersion = new AudioManager(trackMap.get(delayedTrackChooser.getValue()));
        int delaySeconds = Integer.parseInt(delayInputField.getText());
        delayedVersion.getAudioByteData().addDelayInFront(delaySeconds);

        AudioManager undelayedOne = new AudioManager(
                (delayedTrackChooser.getValue().equals(trackTwoName)) ? trackMap.get(trackOneName) : trackMap.get(trackTwoName)
        );

        if (undelayedOne.getAudioByteData().getDataLength() > delayedVersion.getAudioByteData().getDataLength()) {
            delayedVersion.getAudioByteData().paddingAtEnd(undelayedOne.getAudioByteData().getDataLength());
        }
        else {
            undelayedOne.getAudioByteData().paddingAtEnd(delayedVersion.getAudioByteData().getDataLength());
        }

        AudioByteData togetherData = AudioByteData.addData(delayedVersion.getAudioByteData(), undelayedOne.getAudioByteData());
        trackTogetherManager.setAudioByteData(togetherData);

        String wavPath = "./" + "Together.wav";
        trackTogetherManager.dataToWav(wavPath);
        AudioClip audioClip = new AudioClip(new File(wavPath).toURI().toString());
        audioClip.play();
    }

}
