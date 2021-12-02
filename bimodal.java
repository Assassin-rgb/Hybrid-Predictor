import java.util.Arrays;

public class bimodal {
    int index_bits;
    int[] bimodal;
    int high = 7;
    int low = 0;
    int mid = 4;

    // constructor
    public bimodal(int m_bits) {
        index_bits = m_bits;
        bimodal = new int[(int) Math.pow(2, m_bits)];
        Arrays.fill(bimodal, mid);
    }

    // extract index
    public int getIndex(String address) {
        int dec = Integer.parseInt(address, 16);
        String bin = Integer.toBinaryString(dec);
        String ind = bin.substring(bin.length()-2-index_bits, bin.length()-2);
        int index = Integer.parseInt(ind,2);
        return index;
    }

    // get prediction
    public String predict(String address) {
        String predicted;
        int index = getIndex(address);
        // getting prediction
        if (bimodal[index] < mid) {
            predicted = "n";
        } else {
            predicted = "t";
        }
        return predicted;
    }

    // updating counter
    public void update_counter(String address, String actual){
        int index = getIndex(address);
        if (actual.equals("t")) {
            if (bimodal[index] != high) {
                bimodal[index]++;
            }
        } else {
            if (bimodal[index] != low) {
                bimodal[index]--;
            }
        }
    }

    // print final contents
    public void print_content() {
        System.out.println("FINAL BIMODAL CONTENTS");
        for (int i=0; i<bimodal.length; i++) {
            System.out.println(i + "\t" + bimodal[i]);
        }
    }
}
