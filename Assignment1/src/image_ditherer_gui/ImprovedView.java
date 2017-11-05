package image_ditherer_gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class ImprovedView {

    private Scene scene;
    private BorderPane rootLayout;
    private TilePane cellWrapper;
    private BorderPane originalCell;
    private ImageView originalImageView;
    private VBox originalPanel;
    private Button buttonImport;
    private ObservableList<ConvertedCellUnit> convertedCells;

    private static final int CELL_AMOUNT = 2;
    private static final int INIT_ROOT_LAYOUT_WIDTH = 1280;
    private static final int INIT_ROOT_LAYOUT_HEIGHT = 768;
    private static final int CELL_WIDTH = 360;

    public ImprovedView() {
        create();
        adjust();
        setContent();
        arrange();
    }

    private void create() {
        originalCell = new BorderPane();
        originalImageView = new ImageView();
        originalPanel = new VBox();
        buttonImport = new Button();
        cellWrapper = new TilePane();
        convertedCells = FXCollections.observableArrayList();
        rootLayout = new BorderPane();
        scene = new Scene(rootLayout);
    }

    private void adjust() {
        rootLayout.setPrefWidth(INIT_ROOT_LAYOUT_WIDTH);
        rootLayout.setPrefHeight(INIT_ROOT_LAYOUT_HEIGHT);
        cellWrapper.setAlignment(Pos.CENTER);
        cellWrapper.setTileAlignment(Pos.CENTER);
        cellWrapper.setPrefTileWidth(CELL_WIDTH);
        cellWrapper.prefHeightProperty().bind(rootLayout.heightProperty());
        cellWrapper.setHgap(10.0);

        originalCell.prefWidthProperty().bind(cellWrapper.tileWidthProperty());
        originalCell.prefHeightProperty().bind(cellWrapper.heightProperty());
        originalImageView.setPreserveRatio(true);
        originalImageView.fitWidthProperty().bind(originalCell.widthProperty());
        originalPanel.setAlignment(Pos.CENTER);
        originalPanel.setPadding(new Insets(0, 0, 50, 0));
        originalPanel.setSpacing(20.0);
    }

    private void setContent() {
        for (int i = 0; i < CELL_AMOUNT; ++i) {
            convertedCells.add(new ConvertedCellUnit(cellWrapper.tileWidthProperty(), cellWrapper.heightProperty()));
        }
        buttonImport.setText("Import JPEG");
    }

    private void arrange() {
        rootLayout.setCenter(cellWrapper);

        cellWrapper.setPrefColumns(3);
        cellWrapper.getChildren().add(originalCell);
        for (ConvertedCellUnit unit : convertedCells) {
            cellWrapper.getChildren().add(unit.getBasePane());
        }
        originalCell.setCenter(originalImageView);
        originalCell.setBottom(originalPanel);

        originalPanel.getChildren().add(buttonImport);
    }

    void setOriginalPreview(Image image) {
        originalImageView.setImage(image);
    }

    Button getButtonImport () {
        return buttonImport;
    }

    ObservableList<ConvertedCellUnit> getConvertedCells() {
        return convertedCells;
    }

    public Scene getScene() {
        return scene;
    }

}
