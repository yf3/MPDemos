import yf3.audio.NotationInterpreter;
import yf3.audio.SoundDataGenerator;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String test = "C.4 D.4 E.4 R F.4 G.4 G.4 G.4";
        SoundDataGenerator soundDataGenerator = new SoundDataGenerator();
        NotationInterpreter notationInterpreter = new NotationInterpreter();
        try {
            soundDataGenerator.buildData(notationInterpreter.retrieveNoteList(test));
            soundDataGenerator.produceWAV();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}