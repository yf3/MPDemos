package image_ditherer_gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class SimpleView {

    private Scene scene;
    private BorderPane rootLayout;

    private TilePane previewArea;
    private ImageView originalPreview;
    private ImageView outputPreviewLeft;
    private ImageView outputPreviewRight;

    private TilePane panel;
    private VBox outputLeftPanel;
    private VBox outputRightPanel;
    private ComboBox<String> leftStrategyChooser;
    private ComboBox<String> rightStrategyChooser;
    private HBox leftButtonSet;
    private HBox rightButtonSet;

    private Button buttonImport;
    private Button leftApplyButton;
    private Button rightApplyButton;
    private Button leftExportButton;
    private Button rightExportButton;

    public static final ObservableList<String> strategyOptions;

    static {
        strategyOptions = FXCollections.observableArrayList(
                "Grayscale",
                "Ordered Dithering 2x2",
                "Ordered Dithering 4x4",
                "Ordered Dithering 8x8",
                "Uniform Ordered Dithering 2x2",
                "Uniform Ordered Dithering 4x4",
                "Uniform Ordered Dithering 8x8"
        );
    }

    public SimpleView() {
        create();
        setContent();
        arrange();
        adjust();
    }

    private void create() {
        rootLayout = new BorderPane();
        scene = new Scene(rootLayout);
        panel = new TilePane();
        previewArea = new TilePane();
        originalPreview = new ImageView();
        outputPreviewLeft = new ImageView();
        outputPreviewRight = new ImageView();
        outputLeftPanel = new VBox();
        outputRightPanel = new VBox();
        leftStrategyChooser = new ComboBox<>();
        rightStrategyChooser = new ComboBox<>();
        leftButtonSet = new HBox();
        rightButtonSet = new HBox();
        buttonImport = new Button();
        leftApplyButton = new Button();
        rightApplyButton = new Button();
        leftExportButton = new Button();
        rightExportButton = new Button();
    }

    private void setContent() {
        leftStrategyChooser.setItems(strategyOptions);
        leftStrategyChooser.getSelectionModel().selectFirst();
        rightStrategyChooser.setItems(strategyOptions);
        rightStrategyChooser.getSelectionModel().selectFirst();
        buttonImport.setText("Import JPEG");
        leftApplyButton.setText("Apply");
        rightApplyButton.setText("Apply");
        leftExportButton.setText("Export");
        rightExportButton.setText("Export");
    }

    private void arrange() {
        previewArea.setPrefColumns(3);
        previewArea.getChildren().addAll(originalPreview, outputPreviewLeft, outputPreviewRight);
        outputLeftPanel.getChildren().addAll(leftStrategyChooser, leftButtonSet);
        outputRightPanel.getChildren().addAll(rightStrategyChooser, rightButtonSet);
        leftButtonSet.getChildren().addAll(leftApplyButton, leftExportButton);
        rightButtonSet.getChildren().addAll(rightApplyButton, rightExportButton);
        panel.setPrefColumns(3);
        panel.getChildren().addAll(buttonImport, outputLeftPanel, outputRightPanel);
        rootLayout.setCenter(previewArea);
        rootLayout.setBottom(panel);
    }

    private void adjust() {
        rootLayout.setMinWidth(1280);
        rootLayout.setMinHeight(768);
        previewArea.setAlignment(Pos.CENTER);
        previewArea.setTileAlignment(Pos.CENTER);
        previewArea.setPrefTileWidth(360);
        originalPreview.setPreserveRatio(true);
        outputPreviewLeft.setPreserveRatio(true);
        outputPreviewRight.setPreserveRatio(true);
        originalPreview.setFitWidth(previewArea.getTileWidth());
        outputPreviewLeft.setFitWidth(previewArea.getTileWidth());
        outputPreviewRight.setFitWidth(previewArea.getTileWidth());

        panel.setAlignment(Pos.CENTER);
        panel.setTileAlignment(Pos.TOP_CENTER);
        panel.setPrefTileWidth(360);
        panel.setPadding(new Insets(0, 0, 50, 0));

        outputLeftPanel.setAlignment(Pos.CENTER);
        outputRightPanel.setAlignment(Pos.CENTER);
        outputLeftPanel.setSpacing(20.0);
        outputRightPanel.setSpacing(20.0);

        leftButtonSet.setAlignment(Pos.CENTER);
        rightButtonSet.setAlignment(Pos.CENTER);
        leftButtonSet.setSpacing(10.0);
        rightButtonSet.setSpacing(10.0);
    }

    Button getButtonImport() {
        return buttonImport;
    }

    Button getLeftApplyButton() {
        return leftApplyButton;
    }

    Button getLeftExportButton() {
        return leftExportButton;
    }

    Button getRightApplyButton() {
        return rightApplyButton;
    }

    Button getRightExportButton() {
        return rightExportButton;
    }

    String getLeftStrategyChooserValue() {
        return leftStrategyChooser.getValue();
    }

    String getRightStrategyChooserValue() {
        return rightStrategyChooser.getValue();
    }

    void setOriginalPreview(Image image) {
        originalPreview.setImage(image);
    }

    void setOutputPreviewLeft(Image image) {
        outputPreviewLeft.setImage(image);
    }

    void setOutputPreviewRight(Image image) {
        outputPreviewRight.setImage(image);
    }

    public Scene getScene() {
        return scene;
    }
}
