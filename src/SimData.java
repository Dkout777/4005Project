import java.io.*;
import java.util.ArrayList;

public class SimData {
   private int componentsInspected;
   private int[] workStationProducts;

   private double[] componentWorkTimes;
   private double throughPut;
   private ArrayList<Double> avgCapacity;
   private ArrayList<Double> idleTimes;
   private ArrayList<Double> workingTimes;
   private double[] componentWorkingStartTimes;
   private ArrayList allData;
   private int[] inspectedComponents;
   public int[] getInspectedComponents() {
      return inspectedComponents;
   }


   public SimData(){
      allData = new ArrayList<>();
      inspectedComponents = new int[3];
      componentWorkingStartTimes = new double[3];
      componentWorkTimes = new double[3];
      componentWorkTimes[0] = 0;
      componentWorkTimes[1] = 0;
      componentWorkTimes[2] = 0;
      inspectedComponents[0] = 0;
      inspectedComponents[1] = 0;
      inspectedComponents[2] = 0;

   }
   public void addInspectedComponents(int component) {
      inspectedComponents[component-1] = inspectedComponents[component-1] + 1;
   }

   public double[] getComponentWorkTimes() {
      return componentWorkTimes;
   }
   public void setWorkingStartTimes(int component, double clock) {
      componentWorkingStartTimes[component-1] = clock;
   }
   public void addWorkingTimes(int component, double clock) {
      componentWorkTimes[component-1] = componentWorkTimes[component-1] +(clock - componentWorkingStartTimes[component-1]);
      componentWorkingStartTimes[component-1] = -1;
   }

   public int[] getWorkStationProducts() {
      return workStationProducts;
   }

   public void setWorkStationProducts(int[] workStationProducts) {
      this.workStationProducts = workStationProducts;
   }

   public ArrayList<Double> getAvgCapacity() {
      return avgCapacity;
   }

   public void setAvgCapacity(ArrayList<Double> avgCapacity) {
      this.avgCapacity = avgCapacity;
   }

   public ArrayList<Double> getIdleTimes() {
      return idleTimes;
   }

   public void setIdleTimes(ArrayList<Double> idleTimes) {
      this.idleTimes = idleTimes;
   }

   public ArrayList<Double> getWorkingTimes() {
      return workingTimes;
   }

   public void setWorkingTimes(ArrayList<Double> workingTimes) {
      this.workingTimes = workingTimes;
   }
   public int getComponentsInspected() {
      return componentsInspected;
   }

   public void setComponentsInspected(int componentsInspected) {
      this.componentsInspected = componentsInspected;
   }
   public double getThroughPut() {
      return throughPut;
   }

   public void setThroughPut(double throughPut) {
      this.throughPut = throughPut;
   }
   public void compileData() {
      allData.add(componentsInspected);
      for (int num: inspectedComponents) {
         allData.add(num);
      }
      for (double num: componentWorkTimes) {
         allData.add(num);
      }
      for (double num: workStationProducts) {
         allData.add(num);
      }
      allData.add(throughPut);
      for (double num: avgCapacity) {
         allData.add(num);
      }
      for (double num: idleTimes) {
         allData.add(num);
      }
      for (double num: workingTimes) {
         allData.add(num);
      }
   }
   public void save() throws IOException {
      BufferedWriter writer = new BufferedWriter(new FileWriter(new File("test.txt"),true));
      writer.write("New Replication-----------");
      writer.newLine();
      for (Object obj:allData) {
         writer.write(obj.toString());
         writer.newLine();
      }
      writer.write("----------end------------");
      writer.newLine();
      writer.close();
   }

}
