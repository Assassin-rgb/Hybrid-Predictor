import java.util.Arrays;
import java.util.Scanner;

public class hybrid {
    Scanner trace_file;
    int index_bits;
    int[] hybrid;
    bimodal bimodal_sim;
    gshare gshare_sim;
    int high = 3;
    int low = 0;
    int mid = 2;
    int total;
    int miss;

    // constructor
    public hybrid(int h_bits, Scanner trace, bimodal sim1, gshare sim2) {
        index_bits = h_bits;
        trace_file = trace;
        hybrid = new int[(int) Math.pow(2, h_bits)];
        Arrays.fill(hybrid, 1);
        bimodal_sim = sim1;
        gshare_sim = sim2;
    }

    // get index
    public int getIndex(String address) {
        int dec = Integer.parseInt(address, 16);
        String bin = Integer.toBinaryString(dec);
        String ind = bin.substring(bin.length()-2-index_bits, bin.length()-2);
        int index = Integer.parseInt(ind,2);
        return index;
    }

    // run simulator
    public void run() {
        while (trace_file.hasNext()) {
            total++;
            String[] line = trace_file.nextLine().split(" ");
            String actual = line[1];
            int index = getIndex(line[0]);

            // get predictions
            String pred1 = bimodal_sim.predict(line[0]);
            String pred2 = gshare_sim.predict(line[0]);

            // choose model and update model counters
            if (hybrid[index] >= mid) {
                if (!actual.equals(pred2)) {
                    miss++;
                }
                gshare_sim.update_counter(line[0], actual);
            } else {
                if (!actual.equals(pred1)) {
                    miss++;
                }
                bimodal_sim.update_counter(line[0], actual);
            }
            gshare_sim.update_ghr(line[0], actual);

            // update chooser table
            if (pred1.equals(actual) && !pred2.equals(actual)) {
                if (hybrid[index] != low) {
                    hybrid[index]--;
                }
            } else if (!pred1.equals(actual) && pred2.equals(actual)) {
                if (hybrid[index] != high) {
                    hybrid[index]++;
                }
            }
        }
    }

    // print final contents
    public void print_contents() {
        System.out.println("OUTPUT");
        System.out.println("number of predictions:\t\t" + total);
        System.out.println("number of mispredictions:\t" + miss);
        double missrate =  Math.round(((double)miss/(double)total) * 10000.0) / 100.0;
        System.out.println("misprediction rate:\t\t" + missrate + "%");
        System.out.println("FINAL CHOOSER CONTENTS");
        for (int i=0; i<hybrid.length; i++) {
            System.out.println(i + "\t" + hybrid[i]);
        }
        gshare_sim.print_content();
        bimodal_sim.print_content();
    }
}
