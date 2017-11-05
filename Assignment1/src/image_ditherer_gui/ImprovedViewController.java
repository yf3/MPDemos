package image_ditherer_gui;

import image_dithering.ImageData;
import image_dithering.LumaGrayscaleStrategy;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;

import static org.opencv.imgcodecs.Imgcodecs.imwrite;

public class ImprovedViewController {

    private ObservableList<ImageData> imageDataContainer;
    private ImprovedView view;
    ObservableList<ConvertedCellUnit> convertedCellUnits;

    public ImprovedViewController(ObservableList<ImageData> imageDataContainer, ImprovedView view) {
        this.imageDataContainer = imageDataContainer;
        this.view = view;
        convertedCellUnits = view.getConvertedCells();
        registerHandlers();
    }

    private void registerHandlers() {
        view.getButtonImport().setOnAction(event -> chooseFile());
        for (ConvertedCellUnit unit : convertedCellUnits) {
            unit.getButtonApply().setOnAction(event -> applyStrategy(unit, getMatchDataInContainer(unit)));
            unit.getButtonExport().setOnAction(event -> exportCurrentImage(getMatchDataInContainer(unit), unit.isGrayscaleSelected()));
        }
    }

    private ImageData getMatchDataInContainer(ConvertedCellUnit unit) {
        return imageDataContainer.get(convertedCellUnits.indexOf(unit));
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
        setInteractionsEnable();
    }

    private void importImage(File file) {
        for (ImageData imageData : imageDataContainer) {
            imageData.setImageFile(file);
            imageData.readOriginalImage();
        }
    }

    private void showImagePreview() {
        String imageURI = imageDataContainer.get(0).getImageFile().toURI().toString();
        view.setOriginalPreview(new Image(imageURI));
    }


    private void createGrayscaleCopy() {
        for (ImageData imageData : imageDataContainer) {
            imageData.pickGrayscaleStrategy(new LumaGrayscaleStrategy());
            imageData.colorToGrayscale();
        }
    }

    private void setInteractionsEnable() {
        for (ConvertedCellUnit unit : convertedCellUnits) {
            unit.setInteractionsEnable();
        }
    }

    private void applyStrategy(ConvertedCellUnit unit, ImageData imageData) {
        String option = unit.getStrtegyChooserValue();
        if (option.equals("Grayscale (Luma)")) {
            unit.setGrayscaleSelected(true);
            showAppliedPreview(unit, ImageData.retrieveImageStream(imageData.getGrayscaleCopy()));
        }
        else {
            unit.setGrayscaleSelected(false);
            imageData.pickBinaryConverterStrategy(option);
            createOutputMatrix(imageData);
            showAppliedPreview(unit, ImageData.retrieveImageStream(imageData.getOutputMatrix()));
        }
    }

    private void createOutputMatrix(ImageData imageData) {
        imageData.grayscaleToBinary();
    }

    private void showAppliedPreview(ConvertedCellUnit unit, ByteArrayInputStream imageStream) {
        unit.setImageView(new Image(imageStream));
    }

    private void exportCurrentImage(ImageData imageData, boolean grayscaleSelected) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("jpg image(jpg, jpeg)", "*.jpg", "*.jpeg", "*.JPG", "*.JPEG"));
        File savedFile = fileChooser.showSaveDialog(view.getScene().getWindow());
        if (savedFile != null) {
            if (grayscaleSelected) {
                imwrite(savedFile.getPath(), imageData.getGrayscaleCopy());
            }
            else {
                imwrite(savedFile.getPath(), imageData.getOutputMatrix());
            }
        }
    }

}
