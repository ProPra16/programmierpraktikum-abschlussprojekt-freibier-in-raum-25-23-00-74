import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Martin on 13.07.2016.
 */
public class TimeManager {
    private int currentTime;
    private int StartTime;
    private StateManager state;
    private Label label;

    private GUIMain Main;

    private Timer timer;
    private Thread thread;
    private boolean running = false;

    public TimeManager(StateManager existingstate, Label l, GUIMain Main){
        state = existingstate;
        label = l;
        this.Main = Main;
    }

    public void setStartTime(int TimeValue){
        StartTime = TimeValue;
        currentTime = StartTime;
    }

    public int getCurrentTime(){
        return currentTime;
    }

    public int getStartTime(){
        return StartTime;
    }

    public void resetCurrentTime(){
        currentTime = StartTime;
    }

    public void Pause()
    {
        running = false;

        label.setText("Pause");
    }

    public void Restart()
    {
        running = true;
    }

    public void runtime() {
        running = true;
        thread = new Thread(()-> {

            while (true) {
                if(running) {

                    Platform.runLater(() -> {
                        label.setText("Verbleibende Zeit: " + currentTime);
                    });
                    currentTime--;

                    if (currentTime <= 0) {
                        Platform.runLater(() -> {
                            state.resetCodetoStart();
                        });
                        Main.ShowTimeoutAlert();

                        resetCurrentTime();
                    }
                }
                try {
                    thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}


