import javafx.application.Application;
import  javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.*;
import java.util.Optional;

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
    String currentFile;
    //------

    // GUI //
	Parent root;
    Stage mainStage;

	// Aus der fxml-Datei gefiltert
	TabPane CodeTabPane;
    SingleSelectionModel<Tab> CTabSM;
    TabPane TestTabPane;
    SingleSelectionModel<Tab> TTabSM;
    TextArea Console;
    Button DoneButton;
    Button BackButton;
    ImageView PhaseView;

    // Menu
    MenuBar MainMenu;
    Menu FileMenu;
    MenuItem NewMButton;
    MenuItem OpenMButton;
    MenuItem SaveMButton;
    MenuItem SaveAsMButton;
    MenuItem CloseMButton;
    //----------

    // Phasen-Bilder
    Image _CodePhase, _RefPhase, _TestPhase;

	 public static void main(String[] args)
	 {
		// Haupteinstiegspunkt
		launch(args);
     }

	 @Override
     public void start(Stage stage) throws Exception
     {
         // Configurations
         Platform.setImplicitExit(true);
         mainStage = stage;

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

         // Resourcen laden

         File f = new File("src/main/_CodePhase.png");
         _CodePhase = new Image(f.toURI().toString());
         f = new File("src/main/_TestPhase.png");
         _TestPhase = new Image(f.toURI().toString());
         f = new File("src/main/_RefPhase.png");
         _RefPhase = new Image(f.toURI().toString());

         // GUI-Elemente aus der fxml filtern
         CodeTabPane = (TabPane)scene.lookup("#CodeTabPane");
         TestTabPane = (TabPane)scene.lookup("#TestTabPane");
         Console = (TextArea)scene.lookup("#Console");
         DoneButton = (Button)scene.lookup("#DoneButton");
         BackButton = (Button)scene.lookup("#BackButton");
         PhaseView = (ImageView)scene.lookup("#PhaseView");

         MainMenu = (MenuBar)scene.lookup("#MenuBar");
         FileMenu = MainMenu.getMenus().get(0);
         NewMButton = FileMenu.getItems().get(0);
         OpenMButton = FileMenu.getItems().get(1);
         SaveMButton = FileMenu.getItems().get(2);
         SaveAsMButton = FileMenu.getItems().get(3);
         CloseMButton = FileMenu.getItems().get(4);
         //--------------------------------

         // GUI-Elemente finalisieren
         CodeTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
         TestTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
         CTabSM = CodeTabPane.getSelectionModel();
         TTabSM = TestTabPane.getSelectionModel();

         // Eventhandler
         initializeEventHandler();
         //-------------------------

         // Logik initialisieren
         initializeLogic();
         //--------------------------

         // Show Scene
         if(CodeTabPane != null)
            stage.setTitle("TDDT");
         else
            stage.setTitle("ERROR");

         stage.setScene(scene);
         stage.setMaximized(true);

         stage.show();

         ShowWelcomeFileSelection();
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
         currentFile = firstC.dateiname;

         stateManager = new StateManager(codes, interfaceManager,false);
         updatePhase();
     }

    void initializeEventHandler()
    {
        DoneButton.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent e) { nextStep(); } });

        BackButton.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent e) { backStep(); } });

        // Menu //
        NewMButton.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent e) { NewMenuHandler(); } });

        OpenMButton.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent e) { OpenMenuHandler(); } });

        SaveMButton.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent e) { SaveMenuHandler(); } });

        SaveAsMButton.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent e) { SaveAsMenuHandler(); } });

        CloseMButton.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent e) { CloseMenuHandler(); } });
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
        item.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                currentFile = ((Tab)event.getSource()).getText();
            }
        });

        if(code) {
            CodeTabPane.getTabs().add(item);
            CTabSM.select(item);
        }
        else {
            TestTabPane.getTabs().add(item);
            TTabSM.select(item);
        }

        interfaceManager.addTextArea(fileName, codePanel, code);

        TabCount++;

        currentFile = fileName;
    }

    void updatePhase()
    {
        switch (stateManager.currentState)
        {
            case "Red": PhaseView.setImage(_CodePhase); break;
            case "Green": PhaseView.setImage(_TestPhase); break;
            case "Refactor": PhaseView.setImage(_RefPhase); break;
        }
    }

    // Eventhandler, entfernt Code/TestCode Tab
    void removeTab(String fileName)
    {
        interfaceManager.removeCode(fileName);
        TabCount--;
    }

    void nextStep() { stateManager.toNextStep(); }

    void backStep() { stateManager.fromGreenToRed(); }

    // Menu-Eventhandler //
    void NewMenuHandler()
    {
        interfaceManager.writeToConsole("AAAAAAAAAAHHH");
    }

    void OpenMenuHandler()
    {
        // Dialog öffnen
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open XML-File");
        File file = fileChooser.showOpenDialog(mainStage);

        try
        {
            // Datei öffnen
            FileManager.openFile(file.getName().toString().substring(0, file.getName().toString().indexOf(".")));

            // Neuen Tab generieren
            addTab(XMLManager.getAufgabename(), XMLManager.getKlasse(), true);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    void SaveMenuHandler()
    {
        try {
            FileManager.safeFile(currentFile, interfaceManager.getCode(currentFile));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    void SaveAsMenuHandler()
    {
        // Dialog öffnen
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save XML-File");

        // Extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(mainStage);

        try {
            FileManager.safeFile(file.getName().toString().substring(0, file.getName().toString().indexOf(".")), interfaceManager.getCode(currentFile));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    void CloseMenuHandler()
    {
        // Versuche Anwendung zu beenden
        try {
            Platform.exit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void ShowWelcomeFileSelection()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Willkommen");
        alert.setHeaderText("Willkommen zu TDDT. Möchten sie eine neue Aufgabe auswählen oder letztes Projekt laden?");

        ButtonType New = new ButtonType("Neue Aufgabe");
        ButtonType Load = new ButtonType("Laden");
        ButtonType Cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(New, Load, Cancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == New){
            // ... user chose "One"
        } else if (result.get() == Load) {
            // ... user chose "Two"
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

}
