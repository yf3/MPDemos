package yf3.audio;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SoundDataGenerator {

    private static final int SAMPLE_FREQUENCY = 44100;
    private static final float BUFFER_DURATION_IN_SECONDS = 0.25F;
    private static final int BYTES_PER_FRAME = 4;
    private static final int DISABLED_FREQUENCY = 0;

    private byte[] data;
    private int fourierModulatorFrequency;

    public SoundDataGenerator() {
        this.fourierModulatorFrequency = DISABLED_FREQUENCY;
    }

    public void setFourierModulatorFrequency(int frequency) {
        fourierModulatorFrequency = frequency;
    }

    public void buildData(ArrayList<Note> noteArrayList) throws IOException, NullPointerException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (Note element : noteArrayList) {
            outputStream.write(retrieveNoteData(element));
        }
        data = outputStream.toByteArray();
    }

    private byte[] retrieveNoteData(Note note) {
        byte[] noteData;
        int signalFrequency = note.getFrequency();
        int samplesPerBuffer =
                (int) (BUFFER_DURATION_IN_SECONDS * SAMPLE_FREQUENCY);

        int samplesPerPeriod = SAMPLE_FREQUENCY / signalFrequency;

        int periodsPerBuffer = samplesPerBuffer / samplesPerPeriod;
        int bufferLength = samplesPerBuffer * BYTES_PER_FRAME;
        noteData = new byte[bufferLength];

        for (int period = 0; period < periodsPerBuffer; ++period) {
            for (int frame = 0; frame < samplesPerPeriod; ++frame) {
                double fourierModulator = (fourierModulatorFrequency == DISABLED_FREQUENCY) ?
                        1 : Math.cos(2.0 * Math.PI * fourierModulatorFrequency * ((double) frame / (double) SAMPLE_FREQUENCY));
                int value = (int) ( note.getAmplitude() *
                        Math.sin(2.0 * Math.PI * (double)frame / (double)samplesPerPeriod) * fourierModulator );
                int baseAddress = (period * samplesPerPeriod + frame) * BYTES_PER_FRAME;
                noteData[baseAddress+0] = (byte) (value & 0xff);
                noteData[baseAddress+1] = (byte) ((value >>> 8) & 0xff);
                noteData[baseAddress+2] = (byte) (value & 0xff);
                noteData[baseAddress+3] = (byte) ((value >>> 8) & 0xff);
            }
        }
        return noteData;
    }

    public void produceWAV(String outputPath) throws IOException {
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, SAMPLE_FREQUENCY,
                BYTES_PER_FRAME * 8,1, BYTES_PER_FRAME, SAMPLE_FREQUENCY, false);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        AudioInputStream ais = new AudioInputStream(inputStream, format, data.length/format.getFrameSize());
        AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(outputPath));
    }
    
}
