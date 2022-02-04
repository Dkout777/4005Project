import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
  int workStation;
  int componentNum;

  Queue<Component> compBuffer = new LinkedList<Component>();
  public Buffer(int workStation, int componentNum){
    this.workStation = workStation;
    this.componentNum = componentNum;
  }
  public int getComponentNum() {
    return componentNum;
  }
  public int getBufferSize() {
    return compBuffer.size();
  }
  public  void addComponent(Component added){
    compBuffer.add(added);
  }
  public int getWorkStation() {
    return workStation;
  }
  public Queue<Component> getCompBuffer() {
    return compBuffer;
  }

}
