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
		Amplitude amplitude = new Amplitude("./src/test.txt");
		List <Simulation.Operator> solution = amplitude.run();

		for (Simulation.Operator operator : solution)
			System.err.print(Utilities.operator_to_string(operator));

		System.out.println();

		// amplitude.testing();
	}
}
