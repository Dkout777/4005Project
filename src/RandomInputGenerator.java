import java.util.Arrays;

public class RandomInputGenerator {
    int m = 299; //modulus
    int a = 8;//multiplier
    int c = 45; // increment

    public float[] randNumGen(int amount, int seed){
       float[]  randNums = new float[amount];
       randNums[0] = seed;
       for (int x = 1; x < amount; x ++){
           randNums[x] = ((randNums[x-1]*a) + c) % m;
       }
       int x=0;
        for (float num:randNums) {
            randNums[x] = num/m;
            x++;
        }
       return randNums;
    }
    public float KSTest(float[] nums){
        float[] dPlus = new float[nums.length];
        float[] dMinus= new float[nums.length];
        float[] sortedNums = nums;
        Arrays.sort(sortedNums);
        for (int x  = 0; x < nums.length; x++){
            dPlus[x] = (((float)x+1)/(sortedNums.length)) - sortedNums[x];
            System.out.println((x+1) + "/" + sortedNums.length + "-" + sortedNums[x]);
            dMinus[x] = sortedNums[x] - (((float)(x+1)-1)/sortedNums.length);
        }
         Arrays.sort(dPlus);
         Arrays.sort(dMinus);
        System.out.println("Sorted nums");
        for (float num: sortedNums) {
            System.out.println(num);
        }
        System.out.println("Sorted D+");
        for (float num: dPlus) {
            System.out.println(num);
        }
        System.out.println("Sorted D-");
        for (float num: dMinus) {
            System.out.println(num);
        }
        return Float.max( dPlus[dPlus.length-1], dMinus[dMinus.length-1]);
    }
    public float autoCoTest(float[] nums){
        int M = 0;
        for (int x = 0; x <=  nums.length; x++){
            if((2+(x+1) * 1) <= nums.length ){
                M = x;
            }
        }
        float cummulativeValue = 0;
        for (int x = 1; x <  nums.length; x++){
            cummulativeValue = cummulativeValue + (nums[x] * nums[x-1]);
        }
        float p = (float) (((float)1/ (M+1)) * cummulativeValue - 0.25);
        System.out.println("cummaltive value: " + cummulativeValue);
        float sigmaNumerator = (float)Math.sqrt((13*M) + 7);
        float sigmaDenominator = 12 * (M+1);
        float sigma = sigmaNumerator/sigmaDenominator;
        System.out.println("P is: " + p);
        float Z = p/sigma;
        return Z;

    }
    public static void main(String[] args) {
        RandomInputGenerator x = new RandomInputGenerator();
        float[] nums = x.randNumGen(5,10);
        for (float num: nums) {
            System.out.println(num);
        }
        System.out.println("Z value is: " + x.autoCoTest(nums));

    }

}
