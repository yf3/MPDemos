package image_dithering;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class OrderedDithering extends BayerMatrixDithering {

    OrderedDithering(BayerMatrixType bayerMatrixType) {
        super(bayerMatrixType);
    }

    @Override
    protected Mat initializeOutput(Mat source) {
        return new Mat(source.rows() * bayerMatrixLength, source.cols() * bayerMatrixLength, CvType.CV_8UC1);
    }

    @Override
    protected void fillPixels(Mat source, Mat output) {
        int maxLevel = bayerMatrixLength * bayerMatrixLength + 1;
        double levelRange = COLOR_WHITE / maxLevel;

        for (int i = 0; i < output.rows(); i += bayerMatrixLength) {
            for (int j = 0; j < output.cols(); j += bayerMatrixLength) {
                for (int a = 0; a < bayerMatrixLength; ++a) {
                    for (int b = 0; b < bayerMatrixLength; ++b) {
                        double level_value = levelRange * bayerMatrix[a][b] + 1;
                        double grayscaleValue = source.get(i / bayerMatrixLength, j / bayerMatrixLength)[0];
                        double result = (grayscaleValue > level_value) ? COLOR_WHITE : COLOR_BLACK;
                        output.put(i + a, j + b, result);
                    }
                }
            }
        }
    }

}
