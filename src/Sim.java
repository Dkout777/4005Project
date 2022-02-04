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


    }

    public int generateServiceTime() {
        return new Random().nextInt(10);
    }

    ;

    public void update() {
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
                    System.out.println("Inspector " + insp.getNum()+" Created Component :" + clock + " :" + currentEvent.getTime());
                    insp.giveComponent();
                    componentsCompleted++;
                    insp.acceptComponent(this.generateComponent(insp.getNum()));
                    futureEventList.add(new Event(this.generateServiceTime() + clock, Entity.Inspector, insp.getNum()));
                }
            }
            futureEventList.remove(0);
            futureEventList.sort(Comparator.comparingInt(Event::getTime));

        }
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
        Sim mainSimulation = new Sim(20);
        mainSimulation.initialize();



    }
}

