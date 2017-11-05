package image_ditherer_gui;

import image_dithering.ImageData;
import image_dithering.LumaGrayscaleStrategy;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import java.io.ByteArrayInputStream;
import java.io.File;

import static org.opencv.imgcodecs.Imgcodecs.imwrite;

public class SimpleViewController {

    private SimpleView view;
    private ImageData imageDataLeft;
    private ImageData imageDataRight;

    public SimpleViewController(ImageData imageDataLeft, ImageData imageDataRight, SimpleView view) {
        this.imageDataLeft = imageDataLeft;
        this.imageDataRight = imageDataRight;
        this.view = view;
        registerHandlers();
    }

    private void registerHandlers() {
        view.getButtonImport().setOnAction(event -> chooseFile());
        view.getLeftApplyButton().setOnAction(event -> applyStrategyLeft());
        view.getRightApplyButton().setOnAction(event -> applyStrategyRight());
        view.getLeftExportButton().setOnAction(event -> exportOutputLeft());
        view.getRightExportButton().setOnAction(event -> exportOutputRight());
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
        imageDataLeft.setImageFile(file);
        imageDataLeft.readOriginalImage();
        imageDataRight.setImageFile(file);
        imageDataRight.readOriginalImage();
    }

    private void showImagePreview() {
        String imageURI = imageDataLeft.getImageFile().toURI().toString();
        view.setOriginalPreview( new Image(imageURI) );
    }

    private void createGrayscaleCopy() {
        imageDataLeft.pickGrayscaleStrategy(new LumaGrayscaleStrategy());
        imageDataLeft.colorToGrayscale();
        imageDataRight.pickGrayscaleStrategy(new LumaGrayscaleStrategy());
        imageDataRight.colorToGrayscale();
    }

    private void applyStrategyLeft() {
        String option = view.getLeftStrategyChooserValue();
        if (option.equals("Grayscale")) {
            showAppliedPreviewLeft(ImageData.retrieveImageStream(imageDataLeft.getGrayscaleCopy()));
        }
        else {
            imageDataLeft.pickBinaryConverterStrategy(option);
            createOutputMatrix(imageDataLeft);
            showAppliedPreviewLeft(ImageData.retrieveImageStream(imageDataLeft.getOutputMatrix()));
        }
    }

    private void applyStrategyRight() {
        String option = view.getRightStrategyChooserValue();
        if (option.equals("Grayscale")) {
            showAppliedPreviewRight(ImageData.retrieveImageStream(imageDataRight.getGrayscaleCopy()));
        }
        else {
            imageDataRight.pickBinaryConverterStrategy(option);
            createOutputMatrix(imageDataRight);
            showAppliedPreviewRight(ImageData.retrieveImageStream(imageDataRight.getOutputMatrix()));
        }
    }

    private void createOutputMatrix(ImageData imageData) {
        imageData.grayscaleToBinary();
    }

    private void showAppliedPreviewLeft(ByteArrayInputStream imageStream) {
        view.setOutputPreviewLeft(new Image(imageStream));
    }

    private void showAppliedPreviewRight(ByteArrayInputStream imageStream) {
        view.setOutputPreviewRight(new Image(imageStream));
    }

    private void exportOutputLeft() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("jpg image(jpg, jpeg)", "*.jpg", "*.jpeg", "*.JPG", "*.JPEG"));
        File savedFile = fileChooser.showSaveDialog(view.getScene().getWindow());
        if (savedFile != null) {
            if (view.getLeftStrategyChooserValue().equals("Grayscale")) {
                imwrite(savedFile.getPath(), imageDataLeft.getGrayscaleCopy());
            }
            else {
                imwrite(savedFile.getPath(), imageDataLeft.getOutputMatrix());
            }
        }
    }

    private void exportOutputRight() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("jpg image(jpg, jpeg)", "*.jpg", "*.jpeg", "*.JPG", "*.JPEG"));
        File savedFile = fileChooser.showSaveDialog(view.getScene().getWindow());
        if (savedFile != null) {
            if (view.getRightStrategyChooserValue().equals("Grayscale")) {
                imwrite(savedFile.getPath(), imageDataRight.getGrayscaleCopy());
            }
            else {
                imwrite(savedFile.getPath(), imageDataRight.getOutputMatrix());
            }
        }
    }
}
