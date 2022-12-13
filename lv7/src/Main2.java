public class Main2 {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static StreamTokenizer in = new StreamTokenizer(reader);
    static PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String[] args) throws IOException {

        boolean[] nums=new boolean[9999999];

        int max=-9;
        while (true){
            int n=Integer.parseInt(reader.readLine());
            if (n==-1) break;

            max=Math.max(max,n);
            nums[n]=true;
        }
        for (int i = 0; i <=max ; i++) {
            if (nums[i]){
                out.println(i);
//                out.flush(); //刷新缓存区
            }
        }
        out.flush();
    }

    static int nextInt() throws IOException {
        in.nextToken();
        return (int)in.nval;
    }



}
