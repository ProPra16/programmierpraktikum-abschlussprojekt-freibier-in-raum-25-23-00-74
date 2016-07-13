/**
 * Created by Martin on 13.07.2016.
 */
public class BabystepsManager {
    StateManager statem;
    Timemanager timem;

    public BabystepsManager(StateManager state, Timemanager time){
        statem = state;
        timem = time;
    }
    //Nur für Testzwecke, ich weis, ist nicht gut =(
    public BabystepsManager(Timemanager time){
        timem = time;
    }

    public void run(){
        if(timeisover() && statem.getCurrentState() != "Refractor")
            startPhaseagain();
    }

    boolean timeisover(){
        if(timem.getCurrentTime() == 0) return true;
        return false;
    }

    public void startPhaseagain(){
            statem.resetCodetoStart();
            timem.resetCurrentTime();
    }
}
