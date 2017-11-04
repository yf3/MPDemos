import image_ditherer_gui.*;
import image_dithering.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ImageData imageData = new ImageData();
        ImageData imageDataRight = new ImageData();
        SimpleView view = new SimpleView();
        SimpleViewController controller = new SimpleViewController(imageData, imageDataRight, view);
        primaryStage.setTitle("Image Dithering Demo");
        primaryStage.setScene(view.getScene());
        primaryStage.show();
    }

}
