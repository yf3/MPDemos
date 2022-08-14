import image_ditherer_gui.*;
import image_dithering.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import org.opencv.core.Core;

public class MainApplication extends Application {

    private static final int MODEL_COUNT = 2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        ObservableList<ImageData> imageDataContainer = FXCollections.observableArrayList();
        for (int i = 0; i < MODEL_COUNT; ++i) {
            imageDataContainer.add(new ImageData());
        }
        ImprovedView view = new ImprovedView();
        ImprovedViewController controller= new ImprovedViewController(imageDataContainer, view);
//        ImageData imageData = new ImageData();
//        ImageData imageDataRight = new ImageData();
//        SimpleView view = new SimpleView();
//        SimpleViewController controller = new SimpleViewController(imageData, imageDataRight, view);
//        primaryStage.setScene(view.getScene());
        primaryStage.setScene(view.getScene());
        primaryStage.setTitle("Image Dithering Demo");
        primaryStage.show();
    }

}
