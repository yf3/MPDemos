package yf3.audio;

import java.io.IOException;

public class AudioManager {

    private String audioName;
    private AudioByteData audioByteData;
    private boolean isDataBuilt;

    public AudioManager(String name) {
        audioName = name;
        audioByteData = new AudioByteData();
        isDataBuilt = false;
    }

    public void buildAudioData(String input) throws IOException {
        audioByteData.buildData(NotationInterpreter.retrieveNoteList(input));
        isDataBuilt = true;
    }

    public void setAudioData(byte[] audioData) {
        audioByteData = new AudioByteData(audioData);
    }

    public void dataToWav(String outputPath) {
        try {
            audioByteData.produceWAV(outputPath);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addDelayInFront(int delaySeconds) {
        try {
            audioByteData.addDelayInFront(delaySeconds);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
