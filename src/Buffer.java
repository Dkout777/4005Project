import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
  int workStation;
  int componentNum;
  int times [] = {0,0,0};
  int timeStarted = 0;

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
  public  void addComponent(Component added, int timeAdded){
    times[compBuffer.size()] = times[compBuffer.size()] + (timeAdded-timeStarted);
    timeStarted = timeAdded;
    compBuffer.add(added);
  }
  public int getWorkStation() {
    return workStation;
  }
  public Queue<Component> getCompBuffer() {
    return compBuffer;
  }
  public Component removeComponent(int timeRemoved){
    times[compBuffer.size()] = times[compBuffer.size()] + (timeRemoved-timeStarted);
    timeStarted = timeRemoved;
    Component removed = compBuffer.remove();
    return  removed;
  }
  public int[] getTimes() {
    return times;
  }
  public void topOffTime(int timeEnded){
    times[compBuffer.size()] = times[compBuffer.size()] + (timeEnded-timeStarted);
  }

}
