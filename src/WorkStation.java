import java.lang.reflect.Array;
import java.util.ArrayList;

public class WorkStation {
    int num;
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
    public void setWorking(boolean bool){
        working = bool;
    }
    public boolean isWorking(){
        return working;
    }


}
