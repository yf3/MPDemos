package yf3.audio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NotationInterpreter {

    public static final HashMap<String, Integer> FREQUENCY_MAP = new HashMap<>();
    private static final int DEFAULT_AMPLITUDE = 10000;

    static {
        FREQUENCY_MAP.put("C.2", 65);
        FREQUENCY_MAP.put("C#.2", 69);
        FREQUENCY_MAP.put("D.2", 73);
        FREQUENCY_MAP.put("D#.2", 78);
        FREQUENCY_MAP.put("E.2", 82);
        FREQUENCY_MAP.put("F.2", 87);
        FREQUENCY_MAP.put("F#.2", 93);
        FREQUENCY_MAP.put("G.2", 98);
        FREQUENCY_MAP.put("G#.2", 104);
        FREQUENCY_MAP.put("A.2", 110);
        FREQUENCY_MAP.put("A#.2", 117);
        FREQUENCY_MAP.put("B.2", 124);
        FREQUENCY_MAP.put("C.4", 262);
        FREQUENCY_MAP.put("C#.4", 278);
        FREQUENCY_MAP.put("D.4", 294);
        FREQUENCY_MAP.put("D#.4", 311);
        FREQUENCY_MAP.put("E.4", 330);
        FREQUENCY_MAP.put("F.4", 349);
        FREQUENCY_MAP.put("F#.4", 370);
        FREQUENCY_MAP.put("G.4", 392);
        FREQUENCY_MAP.put("G#.4", 415);
        FREQUENCY_MAP.put("A.4", 440);
        FREQUENCY_MAP.put("A#.4", 466);
        FREQUENCY_MAP.put("B.4", 494);
        FREQUENCY_MAP.put("C.6", 1047);
        FREQUENCY_MAP.put("C#.6", 1109);
        FREQUENCY_MAP.put("D.6", 1175);
        FREQUENCY_MAP.put("D#.6", 1245);
        FREQUENCY_MAP.put("E.6", 1319);
        FREQUENCY_MAP.put("F.6", 1397);
        FREQUENCY_MAP.put("F#.6", 1475);
        FREQUENCY_MAP.put("G.6", 1568);
        FREQUENCY_MAP.put("G#.6", 1661);
        FREQUENCY_MAP.put("A.6", 1760);
        FREQUENCY_MAP.put("A#.6", 1865);
        FREQUENCY_MAP.put("B.6", 1976);
    }

    static ArrayList<Note> retrieveNoteList(String input) {
        ArrayList<String> noteTokenList = new ArrayList<>(Arrays.asList(input.trim().split("\\s+")));
        ArrayList<Note> noteDataList = new ArrayList<>();
        buildDataFromTokens(noteTokenList, noteDataList);
        return noteDataList;
    }

    private static void buildDataFromTokens(ArrayList<String> noteTokenList, ArrayList<Note> noteDataList) {
        for (String aNoteTokenList : noteTokenList) {
            noteDataList.add(createNoteDataFromToken(aNoteTokenList));
        }
    }

    private static Note createNoteDataFromToken(String token) {
        if (token.charAt(0) == 'R') {
            return new Note(1, 0);
        }
        else {
            int soundFrequency = FREQUENCY_MAP.get(token);
            return new Note(soundFrequency, DEFAULT_AMPLITUDE);
        }
    }

}
