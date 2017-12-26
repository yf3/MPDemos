import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import yf3.audio.NotationInterpreter;
import yf3.audio.SoundDataGenerator;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
//        String test = "C.4 D.4 E.4 F.4 G.4 A.4 B.4";
//        SoundDataGenerator soundDataGenerator = new SoundDataGenerator();
//        NotationInterpreter notationInterpreter = new NotationInterpreter();
//        try {
//            soundDataGenerator.buildData(notationInterpreter.retrieveNoteList(test));
//            soundDataGenerator.produceWAV("./out/test.wav");
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/AppView.fxml"));
        try {
            BorderPane rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.show();
    }
}