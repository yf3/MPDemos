package image_dithering;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class UniformSizeOrdered extends BayerMatrixDithering {

    UniformSizeOrdered(BayerMatrixType bayerMatrixType) {
        super(bayerMatrixType);
    }

    @Override
    protected Mat initializeOutput(Mat source) {
        return new Mat(source.rows(), source.cols(), CvType.CV_8UC1);
    }

    @Override
    protected void fillPixels(Mat source, Mat output) {
        int maxLevel = bayerMatrixLength * bayerMatrixLength + 1;
        double levelRange = COLOR_WHITE / maxLevel;
        for (int i = 0; i < output.cols(); ++i) {
            for (int j = 0; j < output.rows(); ++j) {
                int x = i % bayerMatrixLength;
                int y = j % bayerMatrixLength;
                double levelValue = (bayerMatrix[x][y] + 1) * levelRange;
                double result = (source.get(i, j)[0] > levelValue) ? COLOR_WHITE : COLOR_BLACK;
                output.put(i, j, result);
            }
        }
    }
}
