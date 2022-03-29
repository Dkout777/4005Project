import java.io.*;
import java.util.ArrayList;

public class SimData {
   private int componentsInspected;
   private int[] workStationProducts;
   private double throughPut;
   private ArrayList<Double> avgCapacity;
   private ArrayList<Double> idleTimes;
   private ArrayList<Double> workingTimes;
   private ArrayList allData;
   public SimData(){
      allData = new ArrayList<>();
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
