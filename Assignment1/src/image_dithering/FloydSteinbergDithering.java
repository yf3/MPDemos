package image_dithering;

import org.opencv.core.Mat;

public class FloydSteinbergDithering extends ErrorDiffusionDithering {

    @Override
    void diffuseError(Mat output, double error, int currentRow, int currentCol) {
        MatPoint[] matPoints = new MatPoint[4];
        matPoints[0] = new MatPoint(currentRow, currentCol + 1, 7.0);
        matPoints[1] = new MatPoint(currentRow + 1, currentCol - 1, 3.0);
        matPoints[2] = new MatPoint(currentRow + 1, currentCol, 5.0);
        matPoints[3] = new MatPoint(currentRow + 1, currentCol - 1, 1.0);
        for (MatPoint p : matPoints) {
            if (!isOutOfBounds(output, p)) {
                output.get(p.getRow(), p.getCol())[0] += (p.getWeight() / 16);
            }
        }
    }

}
