import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class sim {
	public static void main(String[] args) throws FileNotFoundException {
		int h_bits;
		String model = args[0];
		h_bits = Integer.parseInt(args[1]);
		int m_bits = Integer.parseInt(args[2]);
		int n_bits = Integer.parseInt(args[3]);
		int mb_bits = Integer.parseInt(args[4]);
		String trace = args[5];
		System.out.println("\nCOMMAND");
		String[] tr = trace.split("/");
		System.out.println("./sim " + model + " " + h_bits + " " + m_bits + " " + n_bits + " " + mb_bits + " " + tr[tr.length - 1]);

		// read trace file
		Scanner trace_file;
		File file = new File(trace);
		trace_file = new Scanner(file);

		// hybrid simulator
		bimodal sim_1 = new bimodal(mb_bits);
		gshare sim_2 = new gshare(m_bits, n_bits);
		hybrid simulator = new hybrid(h_bits, trace_file, sim_1, sim_2);
		simulator.run();
		simulator.print_contents();
	}
}
