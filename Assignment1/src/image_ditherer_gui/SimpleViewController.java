package image_ditherer_gui;

import image_dithering.ImageData;
import image_dithering.LumaGrayscaleStrategy;
import image_dithering.OrderedDithering;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import java.io.ByteArrayInputStream;
import java.io.File;

import static org.opencv.imgcodecs.Imgcodecs.imwrite;

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
        view.getLeftApplyButton().setOnAction(event -> applyStrategyLeft());
//        view.getRightApplyButton().setOnAction(event -> applyStrategy());
        view.getLeftExportButton().setOnAction(event -> exportOutput());
        view.getRightExportButton().setOnAction(event -> exportOutput());
    }

    private void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("jpg image(jpg, jpeg)", "*.jpg", "*.jpeg", "*.JPG", "*.JPEG"));
        File file = fileChooser.showOpenDialog(view.getScene().getWindow());
        if (file != null) {
            updateOriginalImage(file);
            createGrayscaleCopy();
        }
    }

    private void updateOriginalImage(File file) {
        importImage(file);
        showImagePreview();
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

    private void applyStrategyLeft() {
        String option = view.getLeftStrategyChooserValue();
        if (option.equals("Grayscale")) {
            showAppliedPreview(ImageData.retrieveImageStream(imageData.getGrayscaleCopy()));
        }
        else {
            imageData.pickBinaryConverterStrategy(option);
            createOutputMatrix();
            showAppliedPreview(ImageData.retrieveImageStream(imageData.getOutputImage()));
        }
    }

    private void createOutputMatrix() {
        imageData.grayscaleToBinary();
    }

    private void showAppliedPreview(ByteArrayInputStream imageStream) {
        view.setOutputPreviewLeft(new Image(imageStream));
    }

    private void exportOutput() {
        imwrite("/home/francis/testExport.jpg", imageData.getOutputImage());
    }

}
