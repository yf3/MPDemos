package image_dithering;

import org.opencv.core.Mat;

public class OrderedDithering implements ConvertingStrategy {

    Mat output;

    enum MatrixSize {
        SIZE_2X2(2),
        SIZE_4X4(4),
        SIZE_8X8(8);

        private int length;
        MatrixSize(int length) {
            this.length = length;
        }

        int getLength() {
            return length;
        }
    }

    @Override
    public Mat retrieveResult(Mat source) {
        return output;
    }

}
