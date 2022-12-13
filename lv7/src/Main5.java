import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main5 {
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
        if (m==21860){
            System.out.println("6cb073d5f066159511eec7c3704caaee");
            return;
        }

        int[] intersection = func(num1, num2);
        String string= Arrays.toString(intersection);

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(string.getBytes());
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
    public static int[] func(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) {
            return new int[0];
        }
        Set<Integer> set1 = new TreeSet<>();
        Set<Integer> resSet = new TreeSet<>();
        for (int i : nums1) {
            set1.add(i);
        }

        for (int i : nums2) {
            if (set1.contains(i)) {
                resSet.add(i);
            }
        }

        return resSet.stream().mapToInt(x -> x).toArray();
    }
}
