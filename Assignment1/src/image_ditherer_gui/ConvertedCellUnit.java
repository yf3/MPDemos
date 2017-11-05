package image_ditherer_gui;

import image_dithering.ImageData;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    ComboBox<String> strategyChooser;
    private Button buttonExport;

    ConvertedCellUnit(ReadOnlyDoubleProperty wrapperWidthProperty, ReadOnlyDoubleProperty wrapperHeightProperty) {
        basePane = new BorderPane();
        imageView = new ImageView();
        panel = new VBox();
        strategyChooser = new ComboBox<>();
        for (String strategyName : ImageData.strategyMap.keySet()) {
            strategyChooser.getItems().add(strategyName);
        }
        buttonExport = new Button("Export");

        panel.getChildren().addAll(strategyChooser, buttonExport);
        basePane.setCenter(imageView);
        basePane.setBottom(panel);

        panel.setAlignment(Pos.CENTER);
        basePane.prefWidthProperty().bind(wrapperWidthProperty);
        basePane.prefHeightProperty().bind(wrapperHeightProperty);
        panel.setPadding(new Insets(0, 0, 50, 0));
        panel.setSpacing(20.0);
    }

    void setImageView(Image image) {
        imageView.setImage(image);
    }

    BorderPane getBasePane() {
        return basePane;
    }

    Button getButtonExport() {
        return buttonExport;
    }
}
