package yf3.audio;

class Note {

    private int frequency;
    private int amplitude;

    Note(int frequency, int amplitude) {
        this.frequency = frequency;
        this.amplitude = amplitude;
    }

    int getFrequency() {
        return frequency;
    }

    int getAmplitude() {
        return amplitude;
    }

}
