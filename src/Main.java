package src;

import java.util.List;

import src.classes.Simulation;
import src.classes.Utilities;
import src.searchs.*;


public class Main {
	/**
	 * The main method to run the simulation and interact with the user.
	 *
	 * @param args The command-line arguments.
	 */
	public static void main(String[] args) {
		Menu menu = new Menu();
		while (true) {
			menu.displayMenu();
			menu.selectOption();
		}
	}
}
