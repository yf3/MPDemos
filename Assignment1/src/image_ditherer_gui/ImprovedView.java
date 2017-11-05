package image_ditherer_gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

public class ImprovedView {

    private Scene scene;
    private BorderPane rootLayout;
    private TilePane cellWrapper;
    private BorderPane originalCell;
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
        cellWrapper = new TilePane();
        convertedCells = FXCollections.observableArrayList();
        rootLayout = new BorderPane();
        scene = new Scene(rootLayout);
    }

    private void setContent() {
        for (int i = 0; i < CELL_AMOUNT; ++i) {
            convertedCells.add(new ConvertedCellUnit(cellWrapper.widthProperty(), cellWrapper.heightProperty()));
        }
    }

    private void adjust() {
        rootLayout.setPrefWidth(INIT_ROOT_LAYOUT_WIDTH);
        rootLayout.setPrefHeight(INIT_ROOT_LAYOUT_HEIGHT);
        cellWrapper.setAlignment(Pos.CENTER);
        cellWrapper.setTileAlignment(Pos.CENTER);
        cellWrapper.setPrefTileWidth(CELL_WIDTH);
        cellWrapper.prefHeightProperty().bind(rootLayout.heightProperty());
    }

    private void arrange() {
        rootLayout.setCenter(cellWrapper);

        cellWrapper.getChildren().add(originalCell);
        for (ConvertedCellUnit unit : convertedCells) {
            cellWrapper.getChildren().add(unit.getBasePane());
        }

    }

    public Scene getScene() {
        return scene;
    }

}
