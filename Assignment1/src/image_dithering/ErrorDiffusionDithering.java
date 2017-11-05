package image_dithering;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

abstract class ErrorDiffusionDithering implements ConvertingStrategy {

    final static double BLACK_LIMIT = 127.0;
    final static double COLOR_WHITE = 255.0;
    final static double COLOR_BLACK = 0.0;

    class MatPoint {
        int row;
        int col;
        double weight;
        MatPoint(int row, int col, double weight) {
            this.row = row;
            this.col = col;
            this.weight = weight;
        }
        int getRow() {
            return row;
        }
        int getCol() {
            return col;
        }
        double getWeight() {
            return weight;
        }
    }

    @Override
    public Mat retrieveResult(Mat source) {
        Mat output = new Mat(source.rows(), source.cols(), CvType.CV_8UC1, Scalar.all(0));
        for (int i = 0; i < output.rows(); ++i) {
            for (int j = 0; j < output.cols(); ++j) {
                double pixelValue = source.get(i, j)[0] + output.get(i, j)[0];
                double result = (pixelValue > BLACK_LIMIT ? COLOR_WHITE : COLOR_BLACK);
                output.put(i, j, result);
                double error = pixelValue - result;
                diffuseError(output, error, i, j);
            }
        }
        return output;
    }

    abstract void diffuseError(Mat output, double error, int currentRow, int currentCol);

    boolean isOutOfBounds(Mat output, MatPoint point) {
        int row = point.getRow(), col = point.getCol();
        return ( row < 0 || row > (output.rows()-1) || col < 0 || col > (output.cols()-1) );
    }
}
