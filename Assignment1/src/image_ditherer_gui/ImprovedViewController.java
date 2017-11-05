package image_ditherer_gui;

import image_dithering.ImageData;
import image_dithering.LumaGrayscaleStrategy;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;

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
            unit.getButtonApply().setOnAction(event -> applyStrategy(unit, convertedCellUnits.indexOf(unit)));
        }
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

    private void applyStrategy(ConvertedCellUnit unit, int index) {
        String option = unit.getStrtegyChooserValue();
        ImageData imageData = imageDataContainer.get(index);
        if (option.equals("Grayscale")) {

        }
        else {
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

}
