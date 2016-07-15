import javafx.application.Application;
import  javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.*;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.util.ArrayList;

public class GUIMain extends Application
{
    // Logik //
    InterfaceManager interfaceManager;
    StateManager stateManager;
    String currentFile;
    String filePath;
    TimeManager timer;
    boolean attd;
    //------

    // GUI //
	Parent root;
    Stage mainStage;

	// Aus der fxml-Datei gefiltert
    Label CodeLabel;
    Label TestLabel;
	TextArea CodePane;
    TextArea TestPane;
    TextArea Console;
    Button DoneButton;
    Button BackButton;
    ImageView PhaseView;
    Label TimeLabel;

    // Menu
    MenuBar MainMenu;
    Menu FileMenu;
    MenuItem OpenMButton;
    MenuItem SaveMButton;
    MenuItem ExportMButton;
    MenuItem CloseMButton;
    //----------

    // Phasen-Bilder
    Image _CodePhase, _RefPhase, _TestPhase, _ATDDPhase;

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
         _CodePhase = new Image(getClass().getResource("/_CodePhase.png").toString());
         _TestPhase = new Image(getClass().getResource("/_TestPhase.png").toString());
         _RefPhase = new Image(getClass().getResource("/_RefPhase.png").toString());
         _ATDDPhase = new Image(getClass().getResource("/_ATDDPhase.png").toString());

         // GUI-Elemente aus der fxml filtern
         CodeLabel = (Label) scene.lookup("#CodeLabel");
         TestLabel = (Label) scene.lookup("#TestLabel");
         CodePane = (TextArea)scene.lookup("#CodePane");
         TestPane = (TextArea)scene.lookup("#TestPane");
         Console = (TextArea)scene.lookup("#Console");
         DoneButton = (Button)scene.lookup("#DoneButton");
         BackButton = (Button)scene.lookup("#BackButton");
         PhaseView = (ImageView)scene.lookup("#PhaseView");
         TimeLabel = (Label) scene.lookup("#TimeLabel");

         MainMenu = (MenuBar)scene.lookup("#MenuBar");
         FileMenu = MainMenu.getMenus().get(0);
         OpenMButton = FileMenu.getItems().get(0);
         SaveMButton = FileMenu.getItems().get(1);
         ExportMButton = FileMenu.getItems().get(2);
         CloseMButton = FileMenu.getItems().get(3);
         //--------------------------------

         // GUI-Elemente finalisieren
         // Eventhandler
         initializeEventHandler();
         //-------------------------

         ShowWelcomeFileSelection();

         // Logik initialisieren
         initializeLogic();
         //--------------------------

         // Show Scene
         stage.setTitle("TDDT");

         stage.setScene(scene);
         stage.setMaximized(true);

         stage.show();


     }

     void initializeLogic(){
         // InterfaceManager //
         interfaceManager = new InterfaceManager(CodePane, TestPane, Console);

         // StateManager //
         Code firstC = new Code(XMLManager.getKlasse(), XMLManager.getAufgabenstellung(), XMLManager.getDateiname());
         currentFile = firstC.dateiname;
         String s =  XMLManager.getTestName();
         Code firstT = new Code(XMLManager.getTest(), "", XMLManager.getTestName());


         stateManager = new StateManager(firstC, firstT, interfaceManager);

         if(XMLManager.getBabysteps()) {
             timer = new TimeManager(stateManager, TimeLabel, this);
             timer.setStartTime(XMLManager.getBabystepsTime());
             timer.runtime();
         }
         else if(XMLManager.getAttd())
         {
             attd = true;
             stateManager = new StateManager(firstC, firstT, new Code(XMLManager.getAttdInhalt(), "", XMLManager.getAttdName()), interfaceManager);

         }

         updatePhase();
     }

    void initializeEventHandler()
    {
        DoneButton.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent e) { nextStep(); } });

        BackButton.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent e) { backStep(); } });

        // Menu //
        OpenMButton.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent e) { OpenMenuHandler(); } });

        SaveMButton.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent e) { SaveMenuHandler(); } });

        ExportMButton.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent e) { ExportMenuHandler(); } });

        CloseMButton.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent e) { CloseMenuHandler(); } });
    }

    void updatePhase()
    {
        switch (stateManager.currentState)
        {
            case "Red":
                PhaseView.setImage(_TestPhase);
                CodePane.setEditable(false);
                TestPane.setEditable(true);
                BackButton.setDisable(true);
                if(timer != null)
                {
                    timer.resetCurrentTime();
                    timer.Restart();
                }
                break;
            case "Green":
                PhaseView.setImage(_CodePhase);
                CodePane.setEditable(true);
                TestPane.setEditable(false);
                BackButton.setDisable(false);
                if(timer != null)
                {
                    timer.resetCurrentTime();
                    timer.Restart();
                }
                break;
            case "Refactor":
                PhaseView.setImage(_RefPhase);
                CodePane.setEditable(true);
                TestPane.setEditable(true);
                BackButton.setDisable(true);
                if(timer != null)
                    timer.Pause();
                break;
            case "ATDD":
                PhaseView.setImage(_ATDDPhase );
                CodePane.setEditable(false);
                TestPane.setEditable(true);
                BackButton.setDisable(true);
                break;
        }
    }

    void nextStep() {
        // Alert der dem Nutzer Statusänderungen (oder auch nicht) anzeigt
        Alert alert =  new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Willkommen");
        alert.setHeaderText("Willkommen zu TDDT. Möchten sie eine neue Aufgabe auswählen oder letztes Projekt laden?");

        String currentState = stateManager.getCurrentState();
        stateManager.toNextStep();

        // Änderung switchen
        if(currentState.equals(stateManager.getCurrentState()) && currentState.equals("Red"))
        {
            alert =  new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Test failed");
            alert.setContentText("Tests sind nicht compiliert oder es schlägt mindestens ein Test fehl.\nBitte Tests überprüfen");
        }
        else if(currentState.equals(stateManager.getCurrentState()) && currentState.equals("Green"))
        {
            alert =  new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Code failed");
            alert.setContentText("Code ist nicht compiliert oder es schlägt mindestens ein Test fehl.\nBitte Code überprüfen");
        }
        else if(stateManager.getCurrentState().equals("Green") && currentState.equals("Red"))
        {
            alert =  new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fortschritt");
            alert.setContentText("Tests sind compiliert und genau einer schlägt fehl.\nBitte diesen Tests erfüllen");
        }
        else if(stateManager.getCurrentState().equals("Red") && currentState.equals("Green"))
        {
            alert =  new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Rückschritt");
            alert.setContentText("Tests sind compiliert und genau einer schlägt fehl.\nBitte diesen Tests erfüllen");
        }
        else if(stateManager.getCurrentState().equals("Refactor") && currentState.equals("Green"))
        {
            alert =  new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fortschritt");
            alert.setContentText("Tests wurden erfüllt.\nDu hast nun die Möglichkeit deinen Code zu verbessern.");
        }
        else if(stateManager.getCurrentState().equals("Refactor") && currentState.equals("Refactor"))
        {
            alert =  new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setContentText("Code oder Test compilieren nicht oder die Test laufen nicht mehr durch!");
        }
        else if(stateManager.getCurrentState().equals("Red") && currentState.equals("Refactor"))
        {
            if(!attd) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fortschritt");
                alert.setContentText("Tests wurden erfüllt.\nBitte neuen fehlschlagenden Test schreiben!");
            }
            else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ATDD");
                alert.setContentText("Akzeptanztest funktioniert noch nicht. Aber das ist ok.");
            }
        }
        else if(currentState.equals(stateManager.getCurrentState()) && currentState.equals("ATDD"))
        {
            alert =  new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ATDD-Test");
            alert.setContentText("Der Akzeptanz-Test schlägt nicht fehl.\nBitte Test überprüfen");
        }
        else if(stateManager.getCurrentState().equals("ATDD") && currentState.equals("Refactor"))
        {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fortschritt");
                alert.setContentText("Akzeptanz-Test wurde erfüllt.\nBitte neuen fehlschlagenden Akzeptanz-Test schreiben!");
        }
        else if(stateManager.getCurrentState().equals("Red") && currentState.equals("ATDD"))
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fortschritt");
            alert.setContentText("Guter Akzeptanz-Test geschrieben.");
        }

        alert.showAndWait();
        updatePhase();
    }

    void backStep() {
        stateManager.fromGreenToRed();
        updatePhase();
    }

    // Menu-Eventhandler
    void OpenMenuHandler()
    {
        // Dialog öffnen
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open XML-File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File defaultDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setInitialDirectory(defaultDirectory);

        File file = fileChooser.showOpenDialog(mainStage);

        try
        {
            // Datei öffnen
            FileManager.openFile(file.getAbsolutePath());
            filePath = file.getAbsolutePath();

            // TA init
            currentFile = XMLManager.getAufgabename();
            CodeLabel.setText(XMLManager.getAufgabename());
            CodePane.setText(XMLManager.getKlasse());

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    void SaveMenuHandler()
    {
        try {
            stateManager.update(stateManager.currentState.equalsIgnoreCase("ATDD"));
            if(attd)
                FileManager.safeFile(filePath, interfaceManager.getCode(), stateManager.cm.getTest().getKlasse(), stateManager.getAkTest().getKlasse());
            else
                FileManager.safeFile(filePath, interfaceManager.getCode(), interfaceManager.getTestCode());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    void ExportMenuHandler()
    {
        // Dialog öffnen
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as Java-File");

        // Extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Java files (*.java)", "*.java");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(mainStage);
        
            FileManager.speichereJava(interfaceManager.getCode(), file);
            //FileManager.safeFile(file.getName().toString().substring(0, file.getName().toString().indexOf(".")), interfaceManager.getCode(), interfaceManager.getTestCode(), "");
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

        ButtonType Load = new ButtonType("Laden");
        ButtonType Cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(Load, Cancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == Load) {
            OpenMenuHandler();
        } else {
            CloseMenuHandler();
        }
    }

    public void ShowTimeoutAlert()
    {
        Platform.runLater(()->
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Out of Time");
                    alert.setHeaderText("Deine Bearbeitungszeit ist abgelaufen!\nPhase zurück gesetzt!");
                    alert.showAndWait();

                    updatePhase();
                });

        timer.Pause();
    }

}
