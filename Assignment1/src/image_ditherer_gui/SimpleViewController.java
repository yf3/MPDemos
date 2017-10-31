package image_ditherer_gui;
import image_dithering.ImageData;
import javafx.stage.FileChooser;

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
    }

    private void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("jpg image(jpg, jpeg)", "*.jpg", "*.jpeg", "*.JPG", "*.JPEG"));
        File file = fileChooser.showOpenDialog(view.getScene().getWindow());
        if (file != null) {
            importImage(file);
            showImagePreview();
        }
    }

    private void importImage(File file) {
        imageData.setImageFile(file);
    }

    private void showImagePreview() {
        view.setOriginalPreview();
    }

}
