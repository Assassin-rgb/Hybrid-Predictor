import java.util.Arrays;

public class gshare {
    int index_bits;
    int xor_bits;
    int[] gshare;
    String ghr = "";
    int high = 7;
    int low = 0;
    int mid = 4;

    // constructor
    public gshare(int m_bits, int n_bits) {
        index_bits = m_bits;
        xor_bits = n_bits;
        gshare = new int[(int) Math.pow(2, m_bits)];
        Arrays.fill(gshare, mid);
        for (int i=0; i<n_bits; i++){
            ghr += "0";
        }
    }

    // perform xor
    public String xor(String a, String b) {
        String[] a1 = a.split("");
        String[] b1 = b.split("");
        int len = a.length();
        String out = "";
        int i = 0;
        while (i < len) {
            if (a1[i].equals(b1[i])) {
                out += "0";
            } else {
                out += "1";
            }
            i++;
        }
        return out;
    }

    // extract index
    public int getIndex(String address) {
        int dec = Integer.parseInt(address, 16);
        String bin = Integer.toBinaryString(dec);
        String ind1 = bin.substring(bin.length()-2-index_bits, bin.length()-2-xor_bits);
        String temp = bin.substring(bin.length()-2-xor_bits, bin.length()-2);
        String ind2 = xor(temp, ghr);
        String ind = ind1 + ind2;
        int index = Integer.parseInt(ind,2);
        return index;
    }

    // get prediction
    public String predict(String address) {
        String predicted;
        int index = getIndex(address);
        // getting prediction
        if (gshare[index] < mid){
            predicted = "n";
        } else {
            predicted = "t";
        }
        return predicted;
    }

    // updating global history register
    public void update_ghr(String address, String actual) {
        int index = getIndex(address);
        if (actual.equals("t")) {
            ghr = "1" + ghr;
        } else {
            ghr = "0" + ghr;
        }
        ghr = ghr.substring(0, ghr.length()-1);
    }

    // updating counter
    public void update_counter(String address, String actual) {
        int index = getIndex(address);
        if (actual.equals("t")) {
            if (gshare[index] != high) {
                gshare[index]++;
            }
        } else {
            if (gshare[index] != low) {
                gshare[index]--;
            }
        }
    }

    // print final contents
    public void print_content() {
        System.out.println("FINAL GSHARE CONTENTS");
        for (int i=0; i<gshare.length; i++) {
            System.out.println(i + "\t" + gshare[i]);
        }
    }
}
