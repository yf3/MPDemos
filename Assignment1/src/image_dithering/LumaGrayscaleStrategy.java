package image_dithering;

public class LumaGrayscaleStrategy extends GrayscaleStrategy {

    @Override
    double getGrayscaleValue(double red, double green, double blue) {
        return red * 0.3 + green * 0.59 + blue * 0.11;
    }

}
