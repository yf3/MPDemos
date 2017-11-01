package image_ditherer_gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class SimpleView {


    private Scene scene;
    private BorderPane rootLayout;
    private Button buttonImport;
    private Button buttonConvert;
    private Button buttonExport;
    private HBox panel;
    private HBox previewArea;

    private ImageView originalPreview;
    private ImageView outputPreview;

    public SimpleView() {
        create();
        arrange();
        adjust();
    }

    private void create() {
        rootLayout = new BorderPane();
        scene = new Scene(rootLayout);
        buttonImport = new Button("Import JPEG");
        buttonConvert = new Button("Convert to Black/White");
        buttonExport = new Button("Export Image");
        panel = new HBox();
        previewArea = new HBox();
        originalPreview = new ImageView();
        outputPreview = new ImageView();
    }

    private void arrange() {
        panel.getChildren().addAll(buttonImport, buttonConvert, buttonExport);
        rootLayout.setBottom(panel);
        previewArea.getChildren().addAll(originalPreview, outputPreview);
        rootLayout.setCenter(previewArea);
    }

    private void adjust() {
        rootLayout.setMinWidth(800);
        rootLayout.setMinHeight(600);
        panel.setAlignment(Pos.CENTER);
        previewArea.setAlignment(Pos.CENTER);
        previewArea.setMinWidth(600);
        previewArea.setMinHeight(300);
        originalPreview.setFitWidth(300);
        originalPreview.setFitHeight(300);
        outputPreview.setFitWidth(300);
        outputPreview.setFitHeight(300);
    }

    Button getButtonImport() {
        return buttonImport;
    }

    void setOriginalPreview(Image image) {
        originalPreview.setImage(image);
    }

    public Scene getScene() {
        return scene;
    }
}
