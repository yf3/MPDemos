package image_dithering;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;

import static org.opencv.imgcodecs.Imgcodecs.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.LinkedHashMap;

public class ImageData {

    private File imageFile;
    private Mat originalImage;
    private Mat grayscaleCopy;
    private Mat outputImage;

    private ConvertingStrategy grayscaleConverter;
    private ConvertingStrategy binaryImageConverter;

    public static final LinkedHashMap<String, ConvertingStrategy> strategyMap = new LinkedHashMap<>();
    static {
        strategyMap.put("Grayscale (Luma)", new LumaGrayscaleStrategy());
        strategyMap.put("Ordered Dithering 2x2", new OrderedDithering(BayerMatrixDithering.BayerMatrixType.SIZE_2X2));
        strategyMap.put("Ordered Dithering 4x4", new OrderedDithering(BayerMatrixDithering.BayerMatrixType.SIZE_4X4));
        strategyMap.put("Ordered Dithering 8x8", new OrderedDithering(BayerMatrixDithering.BayerMatrixType.SIZE_8X8));
        strategyMap.put("Uniform Ordered Dithering 2x2", new UniformSizeOrdered(BayerMatrixDithering.BayerMatrixType.SIZE_2X2));
        strategyMap.put("Uniform Ordered Dithering 4x4", new UniformSizeOrdered(BayerMatrixDithering.BayerMatrixType.SIZE_4X4));
        strategyMap.put("Uniform Ordered Dithering 8x8", new UniformSizeOrdered(BayerMatrixDithering.BayerMatrixType.SIZE_8X8));
        strategyMap.put("Floyd-Steinberg Dithering", new FloydSteinbergDithering());
    }

    public ImageData() {

    }


    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public void readOriginalImage() {
        originalImage = imread(imageFile.getPath());
    }

    public Mat getGrayscaleCopy() {
        return grayscaleCopy;
    }

    public Mat getOutputMatrix() {
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

    public void pickBinaryConverterStrategy(String strategyName) {
        binaryImageConverter = strategyMap.get(strategyName);
    }

    public void grayscaleToBinary() {
        if (binaryImageConverter != null) {
            outputImage = binaryImageConverter.retrieveResult(grayscaleCopy);
        }
    }

    public static ByteArrayInputStream retrieveImageStream(Mat target) {
        MatOfByte matOfByte = new MatOfByte();
        imencode(".jpg", target, matOfByte);
        return new ByteArrayInputStream(matOfByte.toArray());
    }

}
