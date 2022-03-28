import java.lang.reflect.Array;
import java.util.ArrayList;

public class WorkStation {
    int type;
    int num;
    double timeBusy = 0;
    //Start time of work
    double timeStarted;
    boolean components [];
    ArrayList<Component> componentList = new ArrayList<Component>();
    boolean working;
    public WorkStation(int num, boolean components[], int type){
        this.type = type;
        this.num = num;
        this.components = components;
        working = false;
    }
    public  int getType(){
        return type;
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

    /**
     * Sets the working boolean as well as increasing the time busy sum
     * @param working boolean setting if it's working
     * @param clock the clock at which it starts or stops working
     */
    public void setWorking(boolean working, double clock){

        this.working = working;
        if(working == false){
            timeBusy = timeBusy + (clock - timeStarted);
        }
        else{
            timeStarted = clock;
        }
    }
    public double getTimeBusy(){
        return timeBusy;
    }
    public boolean isWorking(){
        return working;
    }


}
