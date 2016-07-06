import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;

public class GUIMain extends Application
{
	Parent root;

	// Aus der fxml-Datei filtern
	@FXML
	TabPane CodeTabPane;

	public static void main(String[] args)
	{
		// Haupteinstiegspunkt
		launch(args);
	}

	@Override
    public void start(Stage stage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader();
		File f = new File("src/main/main.fxml");
        loader.setLocation(f.toURI().toURL());

        root = loader.load();

        //CodeTabPane.equals(loader);

		Scene scene = new Scene(root);

        stage.setTitle("TDDT");
        stage.setScene(scene);

        stage.setFullScreen(true);

        stage.show();
    }

	void addTab()
	{

	}

}
