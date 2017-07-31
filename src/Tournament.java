import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Tournament {
	private ArrayList<String> candidates = new ArrayList();
	private ArrayList<Person> players = new ArrayList();
	private int playeramt;
	private Scanner scan;
	private String tourneyname;

	public Tournament(Scanner s) {
		this.scan = s;
		System.out.println("Enter tournament name:");
		this.tourneyname = Main.readString(scan);
	}

	public String getTName() {
		return this.tourneyname;
	}

	public void setPlayerAmt() {
		System.out.println("Enter the number of players this tournament will contain:");
		this.playeramt = Main.readInt(this.scan);
	}

	public void obtainCandidates() {
		System.out.println("Enter candidate names:");
		while (true) {
			String s = Main.readString(scan);
			if (s.equals(Main.EXIT_CODE)) {
				break;
			}
			if (s.trim().equals("")) {
				System.out.println("You cannot enter an empty name - try another");
			}
			if (this.candidates.contains(s)) {
				System.out.println("Candidate name already taken - try another");
			} else {
				this.candidates.add(s);
				System.out.println("Candidate " + s + " added");
			}
		}
	}

	public void removeCandidates() {
		System.out.println("Enter candidate name to remove:");
		while (true) {
			String s = Main.readString(scan);
			if (s.equals(Main.EXIT_CODE)) {
				break;
			}
			if (this.candidates.contains(s)) {
				this.candidates.remove(s);
				System.out.println("Candidate " + s + " removed.");
			} else {
				System.out.println("Candidate not found.");
			}
		}
	}

	public void startTourney() {
		System.out.println("Starting tournament: " + getTName());
		if (!pickPlayers()) {
			return;
		}
		listPlayers();
		while (this.players.size() > 1) {
			playElimination();
		}
	}

	private void startRandomMatch() {
		Person p1 = (Person) this.players.remove((int) (Math.random() * this.players.size()));
		Person p2 = (Person) this.players.remove((int) (Math.random() * this.players.size()));
		System.out.println("Match: " + p1.name + " VS " + p2.name);

		System.out.println("Enter the winners name:");
		String winnersname = Main.readString(scan);
		if (winnersname.equals(p1.name)) {
			this.players.add(p1);
		} else if (winnersname.equals(p2.name)) {
			this.players.add(p2);
		}
	}

	public void playElimination() {
		while (this.players.size() > 1) {
			playEliminationRound();
			for (Person p : this.players) {
				p.fought = false;
				p.currentbye = false;
			}
		}
		System.out.println("The winner of this tournament is: " + ((Person) this.players.get(0)).name);
	}

	public void playEliminationRound() {
		if (this.players.size() % 2 != 0) {
			for (Person randomp : this.players) {
				if (!randomp.hadbye) {
					randomp.hadbye = true;
					randomp.currentbye = true;
					break;
				}
			}
		}

		System.out.println("The players this round are:");
		for (Person cp : this.players) {
			if (cp.currentbye) {
				System.out.println(cp.name + " CB");
			} else if (cp.hadbye) {
				System.out.println(cp.name + " HB");
			} else {
				System.out.println(cp.name);
			}
		}

		while (true) {
			int fighters = 0;
			Person p1 = null;
			Person p2 = null;
			for (int i = 0; i < this.players.size(); i++) {
				p1 = (Person) this.players.get(i);
				if ((!p1.fought) && (!p1.currentbye)) {
					p1.fought = true;
					fighters++;
					break;
				}
			}
			for (int j = 0; j < this.players.size(); j++) {
				p2 = (Person) this.players.get(j);
				if ((!p2.fought) && (!p2.currentbye)) {
					p2.fought = true;
					fighters++;
					break;
				}
			}
			switch (fighters) {
			case 0:
				System.out.println("--- ROUND OVER ---");
				System.out.println();
				return;
			case 1:
				System.out.println("Match: " + p1.name + " - NO OPPONENT");
				return;
			case 2:
				while (true) {
				System.out.println("Match: " + p1.name + " VS " + p2.name);
					System.out.println("Enter the name of the winner:");

					String winnersname = Main.readString(scan);
					if (winnersname.equals(p1.name)) {
						this.players.remove(p2);
						break;
					}
					if (winnersname.equals(p2.name)) {
						this.players.remove(p1);
						break;
					}
					System.out.println("Invalid winner - try again");
				}
			}
		}
	}

	public void listPlayers() {
		System.out.println("The contestants are:");
		for (Person p : this.players) {
			System.out.println(p.name);
		}
		System.out.println("---------------------");
	}

	public boolean pickPlayers() {
		if (this.candidates.size() < this.playeramt) {
			System.out.println("Not enough candidates");
			return false;
		}
		if (this.candidates.size() == this.playeramt) {
			System.out.print("Picking contestants");
			for (String c : this.candidates) {
				this.players.add(new Person(c));
				System.out.print(".");
			}
			System.out.println();
			return true;
		}
		System.out.print("Picking contestants");
		while (this.players.size() < this.playeramt) {
			int randomplayerindex = (int) (Math.random() * this.candidates.size());
			this.players.add(new Person((String) this.candidates.remove(randomplayerindex)));
			System.out.print(".");
		}
		System.out.println();
		return true;
	}
}