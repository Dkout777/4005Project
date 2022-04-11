import java.io.IOException;
import java.util.ArrayList;

public class MasterSim {
    private int pos = 0;
    private int replications;
    private int seed;
    private ArrayList<SimData> datas;
    private double[] randNums;
    private int end;
    private RandomInputGenerator randomInputGenerator = new RandomInputGenerator();
    public MasterSim(int end, int seed, int replications){
        this.seed = seed;
        this.replications = replications;
        datas = new ArrayList<>();
        this.end =end;
        randNums = randomInputGenerator.randNumGen(end*replications, seed);
    }
    public void initialize() throws IOException {
        for (int x =0; x < replications; x ++ ){
            Sim sim = new Sim(end,pos, randNums, false);
            datas.add(sim.initialize());
            pos = sim.getPos();
        }
        for (SimData data: datas) {
            data.compileData();
            data.save();
        }
    }

    public static void main(String[] args) throws IOException {
        MasterSim mainSim = new MasterSim(1000, 100, 6);
        mainSim.initialize();
    }
}
