import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.*;

import java.io.File;
import java.io.IOException;
import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;

public class GUIMain extends Application
{
     // Logik //
     InterfaceManager interfaceManager;
     StateManager stateManager;
     //------

     // GUI //
	Parent root;

	// Aus der fxml-Datei gefiltert
	TabPane CodeTabPane;
     TabPane TestTabPane;
     TextArea Console;
     Button DoneButton;
     Button BackButton;
     //----------

	public static void main(String[] args)
	{
		// Haupteinstiegspunkt
		launch(args);
     }

	@Override
     public void start(Stage stage) throws Exception
     {
         // Versuche Scene aus der fxml zu laden
         try
         {
             FXMLLoader loader = new FXMLLoader();
             File f = new File("main.fxml");
             loader.setLocation(f.toURI().toURL());

             root = loader.load();
         }
         catch (IOException e)
         {
             e.printStackTrace();
             return;
         }

         Scene scene = new Scene(root);

         // GUI-Elemente aus der fxml filtern
         CodeTabPane = (TabPane)scene.lookup("#CodeTabPane");
         TestTabPane = (TabPane)scene.lookup("#TestTabPane");
         Console = (TextArea)scene.lookup("#Console");
         DoneButton = (Button)scene.lookup("#DoneButton");
         BackButton = (Button)scene.lookup("#BackButton");
         //--------------------------------

         // GUI-Elemente finalisieren
         DoneButton.setOnAction(new EventHandler<ActionEvent>()
         {
             @Override
             public void handle(ActionEvent e) { nextStep(); }
         });

         // Logik initialisieren
         initializeLogic();
         //------------------------------

         // Show Scene
         if(CodeTabPane != null)
            stage.setTitle("TDDT");
         else
            stage.setTitle("ERROR");

         stage.setScene(scene);

         stage.setFullScreen(true);

         stage.show();
      }

     void initializeLogic()
     {
         // INIT XML
         //.....

         // InterfaceManager //
         HashMap<String, TextArea> Code = new HashMap<String, TextArea>();
         // STUFF TODO...

         HashMap<String, TextArea> TestCode = new HashMap<String, TextArea>();
         // STUFF TODO...

         interfaceManager = new InterfaceManager(Code, TestCode, Console);

         // StateManager //
         Collection<Code> codes = new ArrayList<Code>();
         // STUFF TODO...

         stateManager = new StateManager(codes, interfaceManager);
     }

    // Fügt neuen Code/TestCode Tab hinzu
    void addTab(String fileName, String content, boolean code)
    {
      TextArea codePanel = new TextArea();

      Tab item = new Tab();
      item.setText(fileName);
      item.setContent(codePanel);

      if(code)
          CodeTabPane.getTabs().add(item);
      else
          TestTabPane.getTabs().add(item);

      interfaceManager.addTextArea(fileName, codePanel, code);
    }

    void nextStep()
    {
        stateManager.nextStep();
    }

}
