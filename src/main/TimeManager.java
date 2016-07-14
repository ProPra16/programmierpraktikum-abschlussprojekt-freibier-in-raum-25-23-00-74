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
        try {
            Platform.runLater(()->{label.setText("Pause");
                try {
                    timer.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Restart()
    {
        runtime();
    }

    public void runtime() {
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                currentTime--;

                Platform.runLater(()->{label.setText("Verbleibende Zeit: " + currentTime);});


                if(currentTime <= 0) {
                    state.resetCodetoStart();
                    Main.ShowTimeoutAlert();

                    resetCurrentTime();
                }
            }
        }, 0, 1000);
    }
}


