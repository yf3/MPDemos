package image_dithering;

import org.opencv.core.Mat;

import java.util.EnumMap;

abstract class BayerMatrixDithering implements ConvertingStrategy {

    final int bayerMatrixLength;
    final int[][] bayerMatrix;
    static double COLOR_WHITE = 255.0;
    static double COLOR_BLACK = 0;

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

    static class BayerMatrixData {
        static EnumMap<OrderedDithering.BayerMatrixType, int[][]> standardBayer = new EnumMap<>(OrderedDithering.BayerMatrixType.class);
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

        static {
            standardBayer.put(OrderedDithering.BayerMatrixType.SIZE_2X2, STANDARD_2X2);
            standardBayer.put(OrderedDithering.BayerMatrixType.SIZE_4X4, STANDARD_4X4);
            standardBayer.put(OrderedDithering.BayerMatrixType.SIZE_8X8, STANDARD_8X8);
        }
    }

    BayerMatrixDithering(BayerMatrixType bayerMatrixType) {
        bayerMatrixLength = bayerMatrixType.getLength();
        bayerMatrix = BayerMatrixData.standardBayer.get(bayerMatrixType);
    }

    @Override
    public Mat retrieveResult(Mat source) {
        Mat output = initializeOutput(source);
        fillPixels(source, output);
        return output;
    }

    abstract Mat initializeOutput(Mat source);
    abstract void fillPixels(Mat source, Mat output);

}
