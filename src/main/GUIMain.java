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

import javax.xml.parsers.ParserConfigurationException;
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
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
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
         CodeTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
         TestTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);

         DoneButton.setOnAction(new EventHandler<ActionEvent>()
         {
             @Override
             public void handle(ActionEvent e) { nextStep(); }
         });

         BackButton.setOnAction(new EventHandler<ActionEvent>()
         {
             @Override
             public void handle(ActionEvent e) { backStep(); }
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
         /*stage.setMinWidth(800);
         stage.setWidth(800);
         stage.setMinHeight(600);
         stage.setHeight(600);*/

         stage.setMaximized(true);

         stage.show();
     }

     void initializeLogic()
     {
         // INIT XML //
         try
         {
             XMLManager.XMLManager("XMLAufgabe");
         }
         catch (ParserConfigurationException e)
         {
             e.printStackTrace();
         }

         // InterfaceManager //
         HashMap<String, TextArea> Code = new HashMap<String, TextArea>();
         // Ersten Tab neu initialisieren
         Tab ctab = CodeTabPane.getTabs().get(0);
         ctab.setText(XMLManager.getAufgabename());
         Code.put(XMLManager.getAufgabename(), (TextArea)ctab.getContent().lookup("#cta0"));

         HashMap<String, TextArea> TestCode = new HashMap<String, TextArea>();
         // Ersten Tab neu initialisieren
         Tab ttab = TestTabPane.getTabs().get(0);
         ttab.setText(XMLManager.getAufgabename() + " Test");
         TestCode.put("firstTest", (TextArea)ttab.getContent().lookup("#tta0"));

         interfaceManager = new InterfaceManager(Code, TestCode, Console);

         // StateManager //
         java.util.Collection<Code> codes = new ArrayList<Code>();

         Code firstC = new Code(XMLManager.getKlasse(), XMLManager.getAufgabenstellung(), XMLManager.getAufgabename());
         codes.add(firstC);

         stateManager = new StateManager(codes, interfaceManager);
     }

    // Fügt neuen Code/TestCode Tab hinzu
    int TabCount = 1;
    void addTab(String fileName, String content, boolean code)
    {
        TextArea codePanel = new TextArea();
        String tabId = "";
        if(code)
            tabId = "cta" + TabCount;
        else
            tabId = "tta" + TabCount;
        codePanel.setId(tabId);
        codePanel.setText(content);

        Tab item = new Tab();
        item.setText(fileName);
        item.setContent(codePanel);
        item.setOnClosed(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                removeTab(((Tab)event.getSource()).getText());
            }
        });

        if(code)
           CodeTabPane.getTabs().add(item);
        else
          TestTabPane.getTabs().add(item);

        interfaceManager.addTextArea(fileName, codePanel, code);

        TabCount++;
    }

    // Eventhandler, entfernt Code/TestCode Tab
    void removeTab(String fileName)
    {
        interfaceManager.removeCode(fileName);
        TabCount--;
    }

    void nextStep() { stateManager.toNextStep(); }

    void backStep() { stateManager.fromGreenToRed(); }

}
