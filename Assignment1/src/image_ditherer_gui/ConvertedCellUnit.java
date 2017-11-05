package image_ditherer_gui;

import image_dithering.ImageData;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class ConvertedCellUnit {

    private BorderPane basePane;
    private ImageView imageView;
    private VBox panel;
    private ComboBox<String> strategyChooser;
    private HBox bar;
    private Button buttonApply;
    private Button buttonExport;
    private boolean grayscaleSelected;

    ConvertedCellUnit(ReadOnlyDoubleProperty tileWidthProperty, ReadOnlyDoubleProperty wrapperHeightProperty) {
        basePane = new BorderPane();
        imageView = new ImageView();
        panel = new VBox();
        strategyChooser = new ComboBox<>();
        for (String strategyName : ImageData.strategyMap.keySet()) {
            strategyChooser.getItems().add(strategyName);
        }
        strategyChooser.getSelectionModel().selectFirst();
        bar = new HBox();
        buttonApply = new Button("Apply");
        buttonExport = new Button("Export");

        bar.getChildren().addAll(buttonApply, buttonExport);
        panel.getChildren().addAll(strategyChooser, bar);
        basePane.setTop(imageView);
        basePane.setBottom(panel);
        basePane.setPadding(new Insets(100, 0, 50, 0));

        basePane.prefWidthProperty().bind(tileWidthProperty);
        basePane.prefHeightProperty().bind(wrapperHeightProperty);
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(tileWidthProperty);
        panel.setAlignment(Pos.CENTER);
        panel.setSpacing(20.0);
        bar.setAlignment(Pos.CENTER);
        bar.setSpacing(10.0);

        setInteractionsDisable();
    }

    void setInteractionsDisable() {
        strategyChooser.setDisable(true);
        buttonApply.setDisable(true);
        buttonExport.setDisable(true);
    }

    void setInteractionsEnable() {
        strategyChooser.setDisable(false);
        buttonApply.setDisable(false);
        buttonExport.setDisable(false);
    }

    void setImageView(Image image) {
        imageView.setImage(image);
    }

    BorderPane getBasePane() {
        return basePane;
    }

    Button getButtonApply() {
        return buttonApply;
    }

    Button getButtonExport() {
        return buttonExport;
    }

    String getStrtegyChooserValue() {
        return strategyChooser.getValue();
    }

    boolean isGrayscaleSelected() {
        return grayscaleSelected;
    }

    void setGrayscaleSelected(boolean value) {
        grayscaleSelected = value;
    }
}
