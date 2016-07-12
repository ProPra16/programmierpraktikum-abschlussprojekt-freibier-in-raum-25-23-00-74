/**
 * Created by Martin on 13.07.2016.
 */
public class Timemanager {
    private int CurrentTime;
    private int StartTime;

    public Timemanager(){

    }

    public void setStartTime(int TimeValue){
        StartTime = TimeValue;
        CurrentTime = StartTime;
    }

    public int getCurrentTime(){
        return CurrentTime;
    }

    public int getStartTime(){
        return StartTime;
    }

    public void runtime() throws InterruptedException {
        while(CurrentTime >= 0){
            System.out.println(CurrentTime);
            Thread.sleep(1000);
            CurrentTime--;
        }
    }

}
