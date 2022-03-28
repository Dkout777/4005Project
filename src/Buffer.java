import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
  int workStation;
  int componentNum;
  //Sum representing how long it has 0, 1, 2 components
  double times [] = {0,0,0};
  //time at which the most recent component has been added
  double timeStarted = 0;

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

  /**
   *  Add component to queue and adds to sum at that queue size and setting a new time started
   * @param added added component
   * @param timeAdded clock at which it was added
   */
  public  void addComponent(Component added, double timeAdded){
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

  /**
   * removes component for queue and adds to the time counter, also sets new start time
   * @param timeRemoved clock time at which the component is removed
   * @return
   */
  public Component removeComponent(double timeRemoved){
    times[compBuffer.size()] = times[compBuffer.size()] + (timeRemoved-timeStarted);
    timeStarted = timeRemoved;
    Component removed = compBuffer.remove();
    return  removed;
  }
  public double[] getTimes() {
    return times;
  }

  /**
   * If there is an event still in progess when simulation ends, the remaining times need to be added, so this
   * function gets called.
   * @param timeEnded clock at which simulation ended
   */
  public void topOffTime(double timeEnded){
    times[compBuffer.size()] = times[compBuffer.size()] + (timeEnded-timeStarted);
  }

}
