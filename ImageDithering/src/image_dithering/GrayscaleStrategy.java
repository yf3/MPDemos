package image_dithering;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

abstract class GrayscaleStrategy implements ConvertingStrategy {

    @Override
    public Mat retrieveResult(Mat source) {
        Mat result = createMatrix(source);
        fillPixels(source, result);
        return result;
    }

    private Mat createMatrix(Mat source) {
        return new Mat(source.rows(), source.cols(), CvType.CV_8UC1);
    }

    private void fillPixels(Mat source, Mat output) {
        for (int i = 0; i < output.rows(); ++i) {
            for (int j = 0; j < output.cols(); ++j) {
                double red = source.get(i, j)[2];
                double green = source.get(i, j)[1];
                double blue = source.get(i, j)[0];
                double grayscaleValue = getGrayscaleValue(red, green, blue);
                output.put(i, j, grayscaleValue);
            }
        }
    }

    abstract double getGrayscaleValue(double red, double green, double blue);

}
