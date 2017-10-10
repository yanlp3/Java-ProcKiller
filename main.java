import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class main {

	static ArrayList<String> procsToClose = new ArrayList<String>();
	static ArrayList<String> openedProcs = new ArrayList<String>();
	static boolean installed = false;
	static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {

		installed = checkInstalled();

		if (!installed) {
			install();
		} else {
			System.out.println("Welcome back to ProcKiller v1.0 by Yan Diagilev.");
			scanProcsToClose();
			scanOpenedProcs();
			closeProcs();
			
			System.out.println("Thanks for using ProcKiller v1.0 by Yan Diagilev! :)");

			s.next();
		}

	}

	public static void install() {
		System.out.println("Welcome To ProcKiller v1.0 Installation Proccess!");
		System.out.println("Step 1: Create Process list to Kill (write 0 when finished):");
		String proc = "";
		System.out.println("Enter Processes to Kill:");

		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("list.txt"), "utf-8"));
		} catch (IOException ex) {
			System.out.println("Error!");
		}

		do {
			proc = s.next();
			try {
				writer.write(proc);
				writer.newLine();
			} catch (IOException e) {
				System.out.println("Error!");
				e.printStackTrace();
			}
		} while (proc.equals("0") == false);

		try {
			writer.close();
		} catch (IOException e) {
			System.out.println("Error!");
			e.printStackTrace();
		}

		System.out.println("List Created successfully at list.txt , you can edit it whenever you want.");

		System.out.println("Step 2: Do yo want to start ProcKiller Automaticlly when windows starts? Y/N");

		String ans = s.next();

		if (ans.equals("Y") || ans.equals("y")) {
			System.out.println("soon");
		} else {
			System.out.println("soon");
		}

		System.out.println("Installation completed successfully , Thanks for using ProcKiller v1.0 by Yan Diagilev :)");

		s.next();
		System.exit(0);

	}

	public static boolean checkInstalled() {

		return new File("list.txt").isFile();
	}

	public static void closeProcs() {

		System.out.println("Killing Processes:");
		
		for (int i = 0; i < procsToClose.size() - 1; i++) {
			for (int j = 0; j < openedProcs.size(); j++) {
				if (openedProcs.get(j).contains(procsToClose.get(i))) {

					try {
						Runtime.getRuntime().exec("taskkill /F /IM" + " " + procsToClose.get(i) + ".exe");
					} catch (IOException e) {
						System.out.println("Error!");
					}

					System.out.println(procsToClose.get(i)+".exe " + "- Killed!");

				}
				{
				}
			}
		}
	}

	public static void scanOpenedProcs() {
		System.out.println("Scanning Opened Processes");

		Process process = null;
		try {
			process = Runtime.getRuntime().exec("tasklist.exe");
		} catch (IOException e) {
			System.out.println("Error!");
		}
		Scanner scanner = new Scanner(new InputStreamReader(process.getInputStream()));
		while (scanner.hasNext()) {
			openedProcs.add(scanner.nextLine());
		}
		scanner.close();
		System.out.println("Opened Processes Sucssesfuly Scaned.");
	}

	public static void scanProcsToClose() {
		System.out.println("Loading Proccess List...");
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream("list.txt");
		} catch (FileNotFoundException e) {
			System.out.println("Error!");
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String proc;

		try {
			while ((proc = br.readLine()) != null) {
				procsToClose.add(proc);
			}
		} catch (IOException e) {
			System.out.println("Error!");
		}

		try {
			br.close();
		} catch (IOException e) {
			System.out.println("Error!");
		}
		
		procsToClose.remove(procsToClose.size()-1);
		
		System.out.println("List Successfully loaded.");


		
	}

}
