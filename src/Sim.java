import java.util.ArrayList;

public class Sim {
    int end;
    int clock;
    ArrayList<Inspector> inspectors;
    ArrayList<WorkStation> workStations;
    public Sim(int end){
        clock = 0;
        this.end=end;
       Inspector insp1 = new Inspector(1);
       inspectors.add(insp1);
       Inspector insp2 = new Inspector(2);
       inspectors.add(insp2);
       WorkStation workStation1 = new WorkStation(1);
       workStations.add(workStation1);
       WorkStation workStation2 = new WorkStation(2);
       workStations.add(workStation2);
       WorkStation workStation3 = new WorkStation(2);
       workStations.add(workStation3);

    }





    public static void main(String[] args) {
        System.out.println("test");
    }
}

