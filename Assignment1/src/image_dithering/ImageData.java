package image_dithering;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import static org.opencv.imgcodecs.Imgcodecs.*;
import java.io.File;

public class ImageData {

    private File imageFile;
    private Mat originalImage;

    public ImageData() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public void setOriginalImage() {
        try {
            originalImage = imread(imageFile.getPath());
        }
        catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
