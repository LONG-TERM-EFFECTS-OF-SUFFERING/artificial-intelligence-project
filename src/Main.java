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
		Amplitude amplitude = new Amplitude("./src/professor_test.txt");
		Cost cost = new Cost("./src/professor_test.txt");

		List <Simulation.Operator> solution = cost.run();
		//Invertimos la solucion
		List <Simulation.Operator> inverted_solution = Utilities.invert_solution(solution);

		System.out.print("START");
		for (Simulation.Operator operator : inverted_solution)
			System.out.print(Utilities.operator_to_string(operator) + "->");
		System.out.print("GOAL\n");

		System.out.println("Expanded nodes: " + cost.get_expanded_nodes());
		System.out.println("Cost: " + cost.get_cost());
		System.out.println("Depht: " + cost.get_depth());

		System.out.println();
	}
}
