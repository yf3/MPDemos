package yf3.audio;

import java.io.IOException;

public class AudioManager {

    private AudioByteData audioByteData;
    private boolean isDataBuilt;

    public AudioManager() {
        audioByteData = new AudioByteData();
        isDataBuilt = false;
    }

    public AudioManager(AudioManager other) {
        this.audioByteData = new AudioByteData(other.getAudioByteData().getData());
        this.isDataBuilt = other.isDataBuilt;
    }

    public AudioByteData getAudioByteData() {
        return audioByteData;
    }

    public void setAudioByteData(AudioByteData otherData) {
        this.audioByteData = new AudioByteData(otherData.getData());
    }

    public void buildAudioData(String input, int fourierFrequency) throws IOException {
        audioByteData.setFourierModulatorFrequency(fourierFrequency);
        audioByteData.buildData(NotationInterpreter.retrieveNoteList(input));
        isDataBuilt = true;
    }

    public void dataToWav(String outputPath) {
        try {
            audioByteData.produceWAV(outputPath);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
