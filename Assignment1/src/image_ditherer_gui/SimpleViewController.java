package image_ditherer_gui;

import image_dithering.ImageData;
import image_dithering.LumaGrayscaleStrategy;
import image_dithering.OrderedDithering;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;

public class SimpleViewController {

    private SimpleView view;
    private ImageData imageData;

    public SimpleViewController(ImageData imageData, SimpleView view) {
        this.imageData = imageData;
        this.view = view;
        registerHandlers();
    }

    private void registerHandlers() {
        view.getButtonImport().setOnAction(event -> chooseFile());
        view.getButtonConvert().setOnAction(event -> convertAndShow());
    }

    private void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("jpg image(jpg, jpeg)", "*.jpg", "*.jpeg", "*.JPG", "*.JPEG"));
        File file = fileChooser.showOpenDialog(view.getScene().getWindow());
        if (file != null) {
            importImage(file);
            showImagePreview();
            createGrayscaleCopy();
        }
    }

    private void convertAndShow() {
        createOutputMatrix();
        showOutputPreview();
    }

    private void importImage(File file) {
        imageData.setImageFile(file);
        imageData.readOriginalImage();
    }

    private void showImagePreview() {
        String imageURI = imageData.getImageFile().toURI().toString();
        view.setOriginalPreview( new Image(imageURI) );
    }

    private void createGrayscaleCopy() {
        imageData.pickGrayscaleStrategy(new LumaGrayscaleStrategy());
        imageData.colorToGrayscale();
    }

    private void createOutputMatrix() {
        imageData.pickBinaryConverterStrategy(new OrderedDithering(OrderedDithering.BayerMatrixType.SIZE_8X8));
        imageData.grayscaleToBinary();
    }

    private void showOutputPreview() {
        ByteArrayInputStream imageStream = ImageData.retrieveImageStream(imageData.getOutputImage());
        view.setOutputPreview(new Image(imageStream));
    }

}
