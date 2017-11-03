package image_dithering;

import org.opencv.core.Mat;

public interface ConvertingStrategy {
    default Mat retrieveResult(Mat source) {
        return null;
    }
}
