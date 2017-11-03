package image_dithering;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.util.EnumMap;

public class OrderedDithering implements ConvertingStrategy {

    private int bayerMatrixLength;
    BayerMatrixType bayerMatrixType;
    Mat output;

    public enum BayerMatrixType {
        SIZE_2X2(2),
        SIZE_4X4(4),
        SIZE_8X8(8);

        private int length;
        BayerMatrixType(int length) {
            this.length = length;
        }

        public int getLength() {
            return length;
        }
    }

    static class BayerMatrix {
        static EnumMap<BayerMatrixType, int[][]> standardBayer = new EnumMap<>(BayerMatrixType.class);
        final static int[][] STANDARD_2X2 = {{0, 2}, {3, 1}};
        final static int[][] STANDARD_4X4 = {{0, 8, 2, 10}, {12, 4, 14, 6}, {3, 11, 1, 9}, {15, 7, 13, 5}};
        final static int[][] STANDARD_8X8 = {
                {0, 32, 8, 40, 2, 34, 10, 42},
                {48, 16, 56, 24, 50, 18, 58, 26},
                {12, 44, 4, 36, 14, 46, 6, 38},
                {60, 28, 52, 20, 62, 30, 54, 22},
                { 3, 35, 11, 43, 1, 33, 9, 41},
                {51, 19, 59, 27, 49, 17, 57, 25},
                {15, 47, 7, 39, 13, 45, 5, 37},
                {63, 31, 55, 23, 61, 29, 53, 21} };
        //        final static int MAX_LENGTH = BayerMatrixType.SIZE_8X8.getLength();
        static {
            standardBayer.put(BayerMatrixType.SIZE_2X2, STANDARD_2X2);
            standardBayer.put(BayerMatrixType.SIZE_4X4, STANDARD_4X4);
            standardBayer.put(BayerMatrixType.SIZE_8X8, STANDARD_8X8);
        }
    }

    public OrderedDithering(BayerMatrixType bayerMatrixType) {
        bayerMatrixLength = bayerMatrixType.getLength();
        this.bayerMatrixType = bayerMatrixType;
    }

    @Override
    public Mat retrieveResult(Mat source) {
        initializeOutput(source);
        fillPixels(source);
        return output;
    }

    private void fillPixels(Mat source) {
        int maxLevel = bayerMatrixLength * bayerMatrixLength + 1;
        double levelRange = 0xff / maxLevel;

        for (int i = 0; i < output.rows(); i += bayerMatrixLength) {
            for (int j = 0; j < output.cols(); j += bayerMatrixLength) {
                for (int a = 0; a < bayerMatrixLength; ++a) {
                    for (int b = 0; b < bayerMatrixLength; ++b) {
                        double level_value = levelRange * (BayerMatrix.standardBayer.get(bayerMatrixType)[a][b] + 1);
                        double grayscaleValue = source.get(i / bayerMatrixLength, j / bayerMatrixLength)[0];
                        double result = (grayscaleValue > level_value) ? 0xff : 0;
                        output.put(i + a, j + b, result);
                    }
                }
            }
        }
    }


    private void initializeOutput(Mat source) {
        output = new Mat(source.rows() * bayerMatrixLength, source.cols() * bayerMatrixLength, CvType.CV_8UC1);
    }

}
