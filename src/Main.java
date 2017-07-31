import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author EiNR
 *
 */
public class Main {
	public final static String EXIT_CODE = "Sh1rana1";
	public static ArrayList<Tournament> tlist = new ArrayList();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean programRunning = true;

		Scanner scan = new Scanner(System.in);
		while (programRunning) {
			printMainMenu();
			String input = scan.nextLine();
			if (input.equals("1")) {
				Tournament t = new Tournament(scan);

				t.setPlayerAmt();
				t.obtainCandidates();

				tlist.add(t);
			} else if (input.equals("2")) {
				System.out.println("Select tournament to start, or 0 to return to main menu:");
				listTournaments();

				int selected = readInt(scan);
				if (selected != 0) {
					Tournament tourney = (Tournament) tlist.get(selected - 1);

					tourney.startTourney();

					tlist.remove(tourney);
				}
			} else if (input.equals("3")) {
				System.out.println("Select tournament to edit, or 0 to return to main menu:");
				listTournaments();

				int selected = readInt(scan);
				if (selected != 0) {
					Tournament tourney = (Tournament) tlist.remove(selected - 1);

					System.out.println("Select operation:");
					System.out.println("1 - Remove candidates");
					System.out.println("2 - Remove tournament");
					System.out.println("3 - Add candidates");
					System.out.println("0 - Return to main menu");

					int operation = readInt(scan);
					if (operation == 1) {
						tourney.removeCandidates();
					} else {
						if (operation == 2) {
							System.out.println("Tournament removed");
							continue;
						}
						if (operation == 3) {
							tourney.obtainCandidates();
						} else if (operation == 0) {
							System.out.println("Returning to main menu.");
						} else {
							System.out.println("Invalid operation.");
						}
					}
					tlist.add(tourney);
				}
			} else if (input.equals("0")) {
				programRunning = false;
			} else {
				System.out.println("Invalid input - please select a menu option");
			}
		}
		scan.close();
		System.out.println("Thank you for using Tournament Picker - Terminating program.");
	}

	public static void listTournaments() {
		System.out.println("Your valid tournaments:");
		int i = 1;
		for (Tournament t : tlist) {
			System.out.println(i + " - " + t.getTName());
			i++;
		}
	}

	public static void printMainMenu() {
		System.out.println("------------------Tournament Picker------------------");
		System.out.println("1 - Set up new tournament");
		System.out.println("2 - Select and start tournament");
		System.out.println("3 - Edit tournament");
		System.out.println("0 - Exit program");
		System.out.println("-----------------------------------------------------");
	}

	// returns -1 if bad input
	public static int readInt(Scanner scan) {
		try {
			String s = scan.nextLine();
			Scanner scanstr = new Scanner(s);
			int n = scanstr.nextInt();
			scanstr.close();
			return n;
		} catch (Exception e) {
			return -1;
		}
	}

	public static String readString(Scanner scan) {
		while (true) {
			try {
				String x = scan.nextLine();
				if (x.equals("")) {
					System.out.println("You cannot enter a blank line");
				} else {
					return x;
				}
			} catch (Exception e) {
				System.out.println("Unexpected Exception: " + e);
				
				// maybe save the state?
			}
		}

	}
}
