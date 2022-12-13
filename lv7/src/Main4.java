import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

public class Main4 {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int[] num1 = new int[m];
        for (int i = 0; i < m; i++) {
            num1[i] = scanner.nextInt();
        }

        int n = scanner.nextInt();
        int[] num2 = new int[n];
        for (int i = 0; i < n; i++) {
            num2[i] = scanner.nextInt();
        }

        int[] num3=new int[m+n];
        for (int i = 0; i <m ; i++) {
            num3[i]=num1[i];
        }
        num1=num3;
        String merge = merge(num1, m, num2, n);

        //md5过程
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(merge.getBytes());
        byte[] digest = md5.digest();
        StringBuffer buf = new StringBuffer("");
        int i;
        for (int offset = 0; offset < digest.length; offset++) {
            i = digest[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        System.out.println(buf.toString());


    }

    public static String merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;
        int p2 = n - 1;
        int tail = m + n - 1;
        while(p1 >= 0 || p2 >= 0) {
            if(p1 < 0) {
                nums1[tail--] = nums2[p2--];
            } else if(p2 < 0) {
                nums1[tail--] = nums1[p1--];
            } else if(nums1[p1] < nums2[p2]) {
                nums1[tail--] = nums2[p2--];
            } else {
                nums1[tail--] = nums1[p1--];
            }
        }
        return Arrays.toString(nums1);
    }


}
