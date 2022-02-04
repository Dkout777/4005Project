import java.lang.reflect.Array;
import java.util.ArrayList;

public class WorkStation {
    int num;
    boolean components [];
    public WorkStation(int num, boolean components[]){
        this.num = num;
        this.components = components;
    }
    public boolean[] getComponents() {
        return components;
    }
    public int getNum() {
        return num;
    }

}
