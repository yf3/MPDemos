package yf3.audio;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AudioByteData {

    private static final int SAMPLE_FREQUENCY = 44100;
    private static final float BUFFER_DURATION_IN_SECONDS = 0.5F;
    private static final int BYTES_PER_FRAME = 4;
    private static final int DISABLED_FREQUENCY = 0;

    private byte[] data;
    private int fourierModulatorFrequency;

    AudioByteData() {
        this.fourierModulatorFrequency = DISABLED_FREQUENCY;
    }

    AudioByteData(byte[] otherData) {
        this.data = otherData.clone();
        this.fourierModulatorFrequency = DISABLED_FREQUENCY;
    }

    void setFourierModulatorFrequency(int frequency) {
        fourierModulatorFrequency = frequency;
    }

    void buildData(ArrayList<Note> noteArrayList) throws IOException, NullPointerException {
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
                noteData[baseAddress + 0] = (byte) (value & 0xff);
                noteData[baseAddress + 1] = (byte) ((value >>> 8) & 0xff);
                noteData[baseAddress + 2] = (byte) (value & 0xff);
                noteData[baseAddress + 3] = (byte) ((value >>> 8) & 0xff);
            }
        }
        return noteData;
    }

    void produceWAV(String outputPath) throws IOException {
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, SAMPLE_FREQUENCY,
                BYTES_PER_FRAME * 8,1, BYTES_PER_FRAME, SAMPLE_FREQUENCY, false);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        AudioInputStream audioInputStream = new AudioInputStream(inputStream, format, data.length/format.getFrameSize());
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File(outputPath));
    }

    public void playDataByClip(byte[] audioData) {
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, SAMPLE_FREQUENCY,
                BYTES_PER_FRAME * 8,1, BYTES_PER_FRAME, SAMPLE_FREQUENCY, false);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(audioData);
        AudioInputStream audioInputStream = new AudioInputStream(inputStream, format, data.length/format.getFrameSize());
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (clip != null) {
                clip.stop();
                clip.close();
            }
        }
    }

    public void addDelayInFront(int delaySeconds) throws IOException {
        if (delaySeconds == 0) return;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int delaySize = BYTES_PER_FRAME * delaySeconds * SAMPLE_FREQUENCY;
        byte[] delay = new byte[delaySize];
        outputStream.write(delay);
        outputStream.write(data);
        data = outputStream.toByteArray();
    }

    public void paddingAtEnd(int longLength) throws IOException {
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        byte[] newPart = new byte[longLength - data.length];
        resultStream.write(data);
        resultStream.write(newPart);
        data = resultStream.toByteArray();
    }

    public static AudioByteData addData(AudioByteData data1, AudioByteData data2) {
        byte[] resultData = new byte[data1.getDataLength()];
        for (int i = 0; i < resultData.length; ++i) {
            resultData[i] = (byte) (data1.getData()[i] + data2.getData()[i]);
        }
        return new AudioByteData(resultData);
    }

    byte[] getData() {
        return data;
    }

    public int getDataLength() {
        return data.length;
    }

}
