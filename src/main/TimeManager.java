import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


/**
 * Created by Martin on 13.07.2016.
 */
public class TimeManager {
    private IntegerProperty CurrentTime = new SimpleIntegerProperty();
    private int StartTime;
    private StateManager state;
    private int status = 0;

    public TimeManager(StateManager existingstate){
        state = existingstate;
    }
    public void test(){

    }

    private void createListener(){
        ChangeListener listener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(CurrentTime.getValue() > 0 && status == 0) {
                    System.out.println("Listener hört dir zu1 " + CurrentTime.getValue());
                    // Hier muss eingefügt werden, in welchem Feld etwas verändert werden soll
                }
                // Babystepsbefehl
                if(zeroValueCheck() && !state.currentState.equals("Refractor")){
                    state.resetCodetoStart();
                    status = 1;
                    System.out.println("Listener hört dir zu2 " + CurrentTime.getValue() );
                }
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
        createListener();
        CurrentTime.setValue(StartTime);

    }

    public int getCurrentTime(){
        return CurrentTime.getValue();
    }

    public int getStartTime(){
        return StartTime;
    }

    public void runOneStep(){
        CurrentTime.setValue(CurrentTime.getValue() - 1);
    }

    public void resetCurrentTime(){
        CurrentTime.setValue(StartTime);
    }

    public void runtime() throws InterruptedException {
        while(CurrentTime.getValue() >= 0 && status == 0){
            Thread.sleep(1000);
            runOneStep();
        }
    }
    // Kann durch einen Klick auf einen Button aufgerufen werden um die Phase
    // zurückgesetze Phase zustarten
    public void resetStatus() throws InterruptedException {
        status = 0;
        runtime();
    }

}


