import java.util.Arrays;

public class RandomInputGenerator {
    int m = 299; //modulus
    int a = 30;//multiplier
    int c = 45; // increment

    public double[] randNumGen(int amount, int seed){
       double[]  randNums = new double[amount];
       randNums[0] = seed;
       for (int x = 1; x < amount; x ++){
           randNums[x] = ((randNums[x-1]*a) + c) % m;
       }
       int x=0;
        for (double num:randNums) {
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
            dMinus[x] = sortedNums[x] - (((float)(x+1)-1)/sortedNums.length);
        }
         Arrays.sort(dPlus);
         Arrays.sort(dMinus);
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
        // System.out.println("cummaltive value: " + cummulativeValue);
        float sigmaNumerator = (float)Math.sqrt((13*M) + 7);
        float sigmaDenominator = 12 * (M+1);
        float sigma = sigmaNumerator/sigmaDenominator;
        float Z = p/sigma;
        System.out.println("P is: " + p);
        System.out.println("sigma is: " + sigma);
        return Z;

    }
    public double generateVariate(int type, double rand){
        switch (type){
            //Inspector 1 component 1
            case 0:
                return (-1/0.0965)*(Math.log(1- rand));
            //Inspector 2 component 2
            case 1:
                return (-1/0.06452)*(Math.log(1- rand));
            // Inspector 2 component 3
            case 2:
                return (-1/0.049)*(Math.log(1- rand));
            //Workstation 1
            case 3:
                return (-1/0.2172)*(Math.log(1- rand));
            //Workstation 2
            case 4:
                return (-1/0.0902)*(Math.log(1- rand));
            //Workstation 3
            case 5:
                return (-1/0.1137)*(Math.log(1- rand));
            default:
                return 0;

        }

    }
    public static void main(String[] args) {
        RandomInputGenerator x = new RandomInputGenerator();
        double[] nums = x.randNumGen(20,300);
        for (double num: nums) {
            System.out.println(num);
        }/*
        System.out.println("Max D Value: " + x.KSTest(nums));
        System.out.println("Z value is: " + x.autoCoTest(nums));*/
        for (double num:nums) {
            System.out.println(x.generateVariate(0, num));

        }

    }

}
