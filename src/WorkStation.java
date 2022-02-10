import java.lang.reflect.Array;
import java.util.ArrayList;

public class WorkStation {
    int num;
    int timeBusy = 0;
    int timeStarted;
    boolean components [];
    ArrayList<Component> componentList = new ArrayList<Component>();
    boolean working;
    public WorkStation(int num, boolean components[]){
        this.num = num;
        this.components = components;
        working = false;
    }
    public boolean[] getComponents() {
        return components;
    }
    public int getNum() {
        return num;
    }
    public void addComponent(Component component){
        componentList.add(component);
    }
    public void clearComponents(){
        componentList.clear();
    }
    public void setWorking(boolean working, int clock){

        this.working = working;
        if(working == false){
            timeBusy = timeBusy + (clock - timeStarted);
        }
        else{
            timeStarted = clock;
        }
    }
    public int getTimeBusy(){
        return timeBusy;
    }
    public boolean isWorking(){
        return working;
    }


}
