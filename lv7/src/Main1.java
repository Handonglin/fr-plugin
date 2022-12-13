import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums=new int[n];
        for (int i = 0; i <n ; i++) {
            nums[i]=sc.nextInt();
        }
        System.out.println(func(nums));

    }
    public static int func(int[] nums) {
        int cur=-9527;
        int count=0;
        for(int i=0;i<nums.length;i++){
            if(count==0){
                cur=nums[i];
            }
            if(cur==nums[i]){
                count++;
            }else{
                count--;
            }
        }
        return cur;

    }
}
