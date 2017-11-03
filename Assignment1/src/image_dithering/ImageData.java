package image_dithering;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;

import static org.opencv.imgcodecs.Imgcodecs.*;

import java.io.ByteArrayInputStream;
import java.io.File;

public class ImageData {

    private File imageFile;
    private Mat originalImage;
    private Mat grayscaleCopy;
    private Mat outputImage;

    private ConvertingStrategy grayscaleConverter;
    private ConvertingStrategy binaryImageConverter;

    public ImageData() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public void readOriginalImage() {
        try {
            originalImage = imread(imageFile.getPath(), CvType.CV_64FC3);
        }
        catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public Mat getGrayscaleCopy() {
        return grayscaleCopy;
    }

    public Mat getOutputImage() {
        return outputImage;
    }

    public void pickGrayscaleStrategy(ConvertingStrategy strategy) {
        grayscaleConverter = strategy;
    }

    public void colorToGrayscale() {
        if (grayscaleConverter != null) {
            grayscaleCopy = grayscaleConverter.retrieveResult(originalImage);
        }
    }

    public void pickBinaryConverterStrategy(ConvertingStrategy strategy) {
        binaryImageConverter = strategy;
    }

    public void grayscaleToBinary() {
        if (binaryImageConverter != null) {
            outputImage = binaryImageConverter.retrieveResult(grayscaleCopy);
        }
    }

    public static ByteArrayInputStream retrieveImageStream(Mat target) {
        MatOfByte matOfByte = new MatOfByte();
        imencode(".bmp", target, matOfByte);
        return new ByteArrayInputStream(matOfByte.toArray());
    }

}
