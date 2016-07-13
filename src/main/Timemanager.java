import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Martin on 13.07.2016.
 */
public class Timemanager {
    private IntegerProperty CurrentTime = new SimpleIntegerProperty();
    private int StartTime;
    private StateManager state;

    public Timemanager(StateManager existingstate){
        state = existingstate;
    }

    private void createListener(){
       ChangeListener listener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            // Hier muss eingefügt werden, in welchem Feld etwas verändert werden soll
                if(zeroValueCheck()) state.resetCodetoStart();


                System.out.println("Listener hört dir zu");
            }
        };
        CurrentTime.addListener(listener);
    }

    public boolean zeroValueCheck(){
        if(CurrentTime.getValue() == 0) return true;
        return false;
    }

    public void setStartTime(int TimeValue){
        StartTime = TimeValue;
        CurrentTime.setValue(StartTime);
        createListener();
    }

    public int getCurrentTime(){
        return CurrentTime.getValue();
    }

    public int getStartTime(){
        return StartTime;
    }

    public void runOneStep(){
        // Ausgabe unterhalb dient zu weiteren Testzwecken
        // System.out.println(CurrentTime.intValue());
        CurrentTime.setValue(CurrentTime.getValue() - 1);
    }

    public void resetCurrentTime(){
        CurrentTime.setValue(StartTime);
    }

    public void runtime() throws InterruptedException {
        while(CurrentTime.getValue() >= 0){
            Thread.sleep(1000);
            runOneStep();
        }
    }

}
