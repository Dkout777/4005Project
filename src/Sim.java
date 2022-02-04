import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Sim {

    private int componentsCompleted = 0;
    private int end;


    private int clock;
    private ArrayList<Inspector> inspectors = new ArrayList<Inspector>();
    private ArrayList<WorkStation> workStations = new ArrayList<WorkStation>();
    private ArrayList<Buffer> buffers = new ArrayList<Buffer>();
    public ArrayList<Event> futureEventList = new ArrayList<Event>();
    private Random rand = new Random();

    public Sim(int end) {
        clock = 0;
        this.end = end;
        Inspector insp1 = new Inspector(1);
        inspectors.add(insp1);
        Inspector insp2 = new Inspector(2);
        inspectors.add(insp2);

        WorkStation workStation1 = new WorkStation(1, new boolean[]{true, false, false});
        workStations.add(workStation1);
        WorkStation workStation2 = new WorkStation(2, new boolean[]{true, true, false});
        workStations.add(workStation2);
        WorkStation workStation3 = new WorkStation(3, new boolean[]{false, true, true});
        workStations.add(workStation3);
        for (WorkStation work: workStations) {
            if(work.getComponents()[0]){
                buffers.add(new Buffer(work.getNum(), 1));
            }
            if(work.getComponents()[1]){
                buffers.add(new Buffer(work.getNum(), 2));
            }
            if(work.getComponents()[2]){
                buffers.add(new Buffer(work.getNum(), 3));
            }
        }
    }

    public int getComponentsCompleted() {
        return componentsCompleted;
    }

    public int getClock() {
        return clock;
    }

    public int getEnd() {
        return end;
    }

    public void initialize() {
        for (Inspector inspector : inspectors) {
            inspector.acceptComponent(this.generateComponent(inspector.getNum()));
            futureEventList.add(new Event(generateServiceTime() + clock, Entity.Inspector, inspector.getNum()));
        }
        futureEventList.sort(Comparator.comparingInt(Event::getTime));
        while(this.getClock() < this.getEnd()){
            this.update();
        }
        System.out.println("made "+this.getComponentsCompleted() + " Components" );
        for (Buffer buffer: buffers) {
            System.out.println("Buffer " +buffer.getComponentNum()+ " for workstation"+ buffer.getWorkStation()+" contains");
            for (Component comp: buffer.getCompBuffer()) {
                System.out.println("comp " + comp.getNum() );
            }

        }


    }

    public int generateServiceTime() {
        return new Random().nextInt(10);
    }
    public void update() {
        if(futureEventList.size() == 0){
            clock = end;
            return;
        }
        Event currentEvent = futureEventList.get(0);
        if(currentEvent.getTime() > end){
            clock = end;
            return;
        }
        updateCurrentEvent(currentEvent);

        for(Inspector insp:inspectors) {
            if (!insp.isInspecting()) {
                insp.acceptComponent(this.generateComponent(insp.getNum()));
                futureEventList.add(new Event(this.generateServiceTime() + clock, Entity.Inspector, insp.getNum()));
            }
        }
    }

    /**
     * THis function takes the most recent event in future event list then updates the model based on it
      * @param currentEvent
     */
    public void updateCurrentEvent(Event currentEvent) {
        clock = currentEvent.getTime();
        // Handling current Event
        if (currentEvent.getEntity() == Entity.Inspector) {
            for (Inspector insp : inspectors) {
                if (insp.getNum() == currentEvent.getNum()) {
                    System.out.println("Inspector " + insp.getNum()+" inspected Component "+ insp.getComponent().getNum() +" :" + clock + " :" + currentEvent.getTime());
                    componentsCompleted++;
                    if(allocateToBuffer(insp.getComponent())){
                      insp.giveComponent();
                      insp.acceptComponent(this.generateComponent(insp.getNum()));
                      futureEventList.add(new Event(this.generateServiceTime() + clock, Entity.Inspector, insp.getNum()));
                    }
                    else{
                        insp.setBlocked(true);
                        System.out.println("Inspector " +insp.getNum()+ " is blocked");
                    }

                }
            }
            futureEventList.remove(0);
            futureEventList.sort(Comparator.comparingInt(Event::getTime));

        }
    }
    public boolean allocateToBuffer(Component component){
        ArrayList<Buffer> checkArray = new ArrayList<Buffer>();
        for (Buffer buffer:buffers) {
            if(buffer.getComponentNum() == component.getNum()){
                checkArray.add(buffer);
            }
        }
        checkArray.sort(Comparator.comparingInt(Buffer::getBufferSize));
        for (Buffer sortedBuffer:checkArray) {
            if(sortedBuffer.getBufferSize() < 2){
                sortedBuffer.addComponent(component);
                return true;
            }
        }
        return false;
    }
    public Component generateComponent(int inspectorNum){
        if(inspectorNum ==1){
            return (new Component(1));
        }
        else{
            Random randomnum = new Random();
            double temp = randomnum.nextDouble();
            if(temp <= 0.5){
                return new Component(2);
            }else{
                return new Component(3);
            }
        }
    }





    public static void main(String[] args) {
        Sim mainSimulation = new Sim(50);
        mainSimulation.initialize();



    }
}

