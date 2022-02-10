import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Sim {

    private int componentsCompleted = 0;
    //Time the simulation will end
    private int end;
    // counter for each product type;
    int productsCompleted[] = {0,0,0};
    private int clock;
    //Lists for  each type of entity
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
        WorkStation workStation3 = new WorkStation(3, new boolean[]{true, false, true});
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

    /**
     * this method initializes the simulation, first by giving the inspectors a component and then running the update
     * function until the time runs out.
     */
    public void initialize() {
        for (Inspector inspector : inspectors) {
            inspector.acceptComponent(this.generateComponent(inspector.getNum()));
            System.out.println("Time: " +clock+ " : Inspector "+ inspector.getNum()+" Started inspecting Component " + inspector.getComponent().getNum());
            futureEventList.add(new Event(generateInspectorServiceTime() + clock, Entity.Inspector, inspector.getNum()));
        }
        futureEventList.sort(Comparator.comparingInt(Event::getTime));
        // THe main simulation loop, it lasts until the clock reaches end
        int x = 0;
        while(this.getClock() < this.getEnd()){
           // System.out.println("Cycle: " + x);
           // if(futureEventList.size()>0)
               // System.out.println("current event: "+futureEventList.get(0).eventToString());
            this.update();
            futureEventList.sort(Comparator.comparingInt(Event::getTime));
           // if(futureEventList.size()>0)
              //  System.out.println("Next event: "+futureEventList.get(0).eventToString());
           // x++;
        }
        //This  section prints out all the statistics about the current run
        for (Inspector insp:inspectors) {
            if(insp.getBlocked()){
                insp.addIdle(end);
            }
        }
        for(Buffer buffer: buffers){
            buffer.topOffTime(clock);
        }
        for(WorkStation workStation: workStations){
            if(workStation.isWorking()){
                workStation.setWorking(false, clock);
            }
        }
        System.out.println("----------------statistics-------------" );
        System.out.println("Inspected "+this.getComponentsCompleted() + " Components" );
        System.out.println("Workstation 1 made  "+productsCompleted[0] + " products" );
        System.out.println("Workstation 2 made  "+productsCompleted[1] + " products" );
        System.out.println("Workstation 3 made "+productsCompleted[2] + " products" );
        System.out.println("Product throughput was "+(float)(productsCompleted[0] + productsCompleted[1] + productsCompleted[2])/end);
        for (Buffer buffer: buffers) {
            System.out.println("Buffer " +buffer.getComponentNum()+ " for workstation "+ buffer.getWorkStation());
            System.out.println("was empty for " + buffer.getTimes()[0]+", half full for " + buffer.getTimes()[1] + ", full for "+ buffer.getTimes()[2]);
        }
        for (Inspector insp: inspectors) {
            System.out.println("Inspector "+ insp.getNum() + " Idle time was " + insp.getIdleTime());

        }
        for(WorkStation work: workStations){
            System.out.println("Workstation "+ work.getNum() + " was working for " + work.timeBusy);
        }


    }

    /**
     * This generates a random number between 1 and 10 to use as a service time. This is temporary until the data
     * collection phase is done.
     * @return Random int
     */
    public int generateInspectorServiceTime() {
        return new Random().nextInt(10) +1;
    }
    public int generateWorkstationServiceTime() {
        return new Random().nextInt(10) +1;
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
        updateWorkStations();
        updateInspectors();
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
                    System.out.println("Time: " +clock+ " : Inspector "+ insp.getNum()+" Finished Inspecting Component " + insp.getComponent().getNum());
                    componentsCompleted++;
                    if(allocateToBuffer(insp.getComponent())){
                      insp.giveComponent();
                    }
                    else{
                        insp.setBlocked(true);
                        insp.setIdleStart(clock);
                        System.out.println("Time: " +clock+ " : Inspector "+ insp.getNum()+" is blocked ");
                    }

                }
            }

        }
        if(currentEvent.getEntity() == Entity.Workstation){
            for(WorkStation workStation: workStations){
                if(currentEvent.getNum() == workStation.num){
                    workStation.clearComponents();
                    System.out.println("Time: " +clock+ " : Workstation "+ workStation.getNum()+" Finished making a product ");
                    productsCompleted[workStation.num-1]++;
                    workStation.setWorking(false, clock);
                }
            }
        }
        futureEventList.remove(0);
        futureEventList.sort(Comparator.comparingInt(Event::getTime));
    }

    /**
     * THis method is responsible for updating the inspectors. It loops through all inspectors, if blocked it will
     * attempt to put away the component again, if not inspecting then it will assign a new component
     */
    public void updateInspectors(){
        // tries tp unblock
        for(Inspector insp:inspectors) {
            if(insp.getBlocked()){
                if(allocateToBuffer(insp.getComponent())){
                    insp.giveComponent();
                    insp.setBlocked(false);
                    insp.addIdle(clock);
                    System.out.println("Time: " +clock+ " : Inspector "+ insp.getNum()+" is unblocked ");
                    insp.acceptComponent(this.generateComponent(insp.getNum()));
                    futureEventList.add(new Event(this.generateInspectorServiceTime() + clock, Entity.Inspector, insp.getNum()));
                    System.out.println("Time: " +clock+ " : Inspector "+ insp.getNum()+" Started inspecting Component " + insp.getComponent().getNum());
                }
            }
            else if (!insp.isInspecting()) {
                insp.acceptComponent(this.generateComponent(insp.getNum()));
                futureEventList.add(new Event(this.generateInspectorServiceTime() + clock, Entity.Inspector, insp.getNum()));
                System.out.println("Time: " +clock+ " : Inspector "+ insp.getNum()+" Started inspecting Component " + insp.getComponent().getNum());
            }
        }

    }

    /**
     * This method is responsible for updating the workstations, it loops through an checks if they aren't working, if
     * not it will loop through it's buffers to check if it can start. If each buffer has at least 1 item, it will
     * remove 1 item from each buffer and add them to the workstation array, and set itself to working.
     */
    public void updateWorkStations(){
        for (WorkStation work: workStations) {
            if (!work.isWorking()) {
                ArrayList<Buffer> tempArray = new ArrayList<Buffer>();
                for (Buffer buffer : buffers) {
                    if (buffer.getWorkStation() == work.getNum()) {
                        tempArray.add(buffer);
                    }
                }
                Boolean check = true;
                for (Buffer buffer : tempArray) {
                    if (buffer.getBufferSize() == 0) {
                        check = false;
                    }
                }
                if (check) {
                    for (Buffer buffer : tempArray) {
                        work.addComponent(buffer.removeComponent(clock));
                    }
                    work.setWorking(true, clock);
                    System.out.println("Time: " +clock+ " : WorkStation "+ work.getNum()+" started working ");
                    futureEventList.add(new Event(generateWorkstationServiceTime() + clock, Entity.Workstation, work.getNum()));

                }
            }
        }
    }

    /**
     * This method is used to allocate a component to one of the buffers. It first creates  a temp list cotnaining all
     * buffers that hold that component. It then sorts it based on how big each buffer is. It then attempts to add the
     * component to each buffer until it succeeds.
     * @param component the component that is being added
     * @return boolean to let know if it suceeded in adding to buffer
     */
    public boolean allocateToBuffer(Component component){
        ArrayList<Buffer> checkArray = new ArrayList<Buffer>();
        for (Buffer buffer:buffers) {
            if(buffer.getComponentNum() == component.getNum()){
                checkArray.add(buffer);
            }
        }
        checkArray.sort(Comparator.comparingInt(Buffer::getBufferSize));
        for (Buffer sortedBuffer:checkArray) {
            // System.out.println(sortedBuffer.getBufferSize());
        }
        for (Buffer sortedBuffer:checkArray) {
            if(sortedBuffer.getBufferSize() < 2){
                System.out.println("added to buffer " + sortedBuffer.getComponentNum() +" Belonging to Workstation " +  sortedBuffer.getWorkStation());
                sortedBuffer.addComponent(component, clock);
                return true;
            }
        }
        return false;
    }

    /**
     * If inspectorNum is 1 it returns 1 else it randomly chooses between 2 or 3
     * @param inspectorNum
     * @return
     */
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
        Sim mainSimulation = new Sim(1000);
        mainSimulation.initialize();



    }
}

