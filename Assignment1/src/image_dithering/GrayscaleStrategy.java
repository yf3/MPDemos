package image_dithering;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

abstract class GrayscaleStrategy implements ConvertingStrategy {

    Mat result;

    @Override
    public Mat retrieveResult(Mat source) {
        createMatrix(source);
        fillPixels(source);
        return result;
    }

    private void createMatrix(Mat source) {
        result = new Mat(source.rows(), source.cols(), CvType.CV_8UC1);
    }

    private void fillPixels(Mat source) {
        for (int i = 0; i < result.rows(); ++i) {
            for (int j = 0; j < result.cols(); ++j) {
                double red = source.get(i, j)[2];
                double green = source.get(i, j)[1];
                double blue = source.get(i, j)[0];
                double grayscaleValue = getGrayscaleValue(red, green, blue);
                result.put(i, j, grayscaleValue);
            }
        }
    }

    abstract double getGrayscaleValue(double red, double green, double blue);

}
