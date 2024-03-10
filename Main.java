import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Scanner;


/**
 * The Main class is the entry point of the program. It provides the main method
 * to run the simulation and interact with the user.
 */
public class Main {
	/**
	 * Converts the given operator code to a string representation.
	 *
	 * @param operator The operator code.
	 * @return The string representation of the operator.
	 * @throws RuntimeException if the operator code is invalid.
	 */
	static public String operator_to_string(int operator) {
		switch (operator) {
			case Simulation.UP:
				return "UPPER";
			case Simulation.LEFT:
				return "LEFT";
			case Simulation.BOTTOM:
				return "BOTTOM";
			case Simulation.RIGHT:
				return "RIGHT";
			default:
				throw new RuntimeException("Error: invalid operator");
		}
	}

	/**
	 * Prints the available operators to the console.
	 *
	 * @param simulation The simulation object.
	 */
	static private void print_available_operators(Simulation simulation) {
		List <Integer> moves = simulation.get_available_moves();

		System.out.println("Available options");
		for (Integer move : moves)
			System.out.print(Main.operator_to_string(move) + " ");

		System.out.println();
	}

	/**
	 * Prints the instructions for the simulation.
	 */
	static private void print_instructions() {
		System.out.println("Instructions");
		System.out.println("1. Enter the operator (1 UP, 2 RIGHT, 3 BOTTOM, 4 LEFT).");
		System.out.println("2. The simulation will move the object accordingly.");
		System.out.println("3. Repeat steps 1 and 2 until the goal is reached.");
		System.out.println("4. The simulation will end.");
		System.out.println();
	}

	/**
	 * The main method to run the simulation and interact with the user.
	 *
	 * @param args The command-line arguments.
	 */
	public static void main(String[] args) {
		Simulation simulation = new Simulation("./test.txt"); // 10 x 10
		Scanner scanner = new Scanner(System.in);

		do {
			simulation.print_state();
			print_available_operators(simulation);
			print_instructions();

			System.out.println("Write the operator: ");
			int operator = scanner.nextInt();

			simulation.move(operator);

			System.out.println();

		} while (!simulation.is_goal());

		scanner.close();
	}
}
