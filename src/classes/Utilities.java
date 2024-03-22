package src.classes;

import java.util.List;
import java.util.Stack;

import src.classes.Simulation.Operator;

public class Utilities {
	/**
	 * Converts a Operator enum value to its corresponding string
	 * representation.
	 *
	 * @param operator The Operator value to convert
	 * @return the string representation of the operator
	 * @throws RuntimeException if the operator is invalid
	 */
	static public String operator_to_string(Operator operator) {
		switch (operator) {
			case Operator.UP:
				return "UPPER";
			case Operator.LEFT:
				return "LEFT";
			case Operator.BOTTOM:
				return "BOTTOM";
			case Operator.RIGHT:
				return "RIGHT";
			case null:
				return "";
			default:
				throw new RuntimeException("Error: invalid operator");
		}
	}

	/**
	 * Checks if a player (of a node) is on the ship base on the ship's fuel.
	 *
	 * @param ship_fuel The amount of fuel of the ship.
	 * @return true if the ship has fuel, false otherwise.
	 */
	static public boolean is_on_ship(int ship_fuel) {
		return ship_fuel > 0;
	}

	/**
	 * Returns a list of operators representing the sequence of operations performed
	 * from the
	 * given node to its root.
	 *
	 * @param node The node from which to trace the performed operations.
	 * @return a list of operators representing the performed operations.
	 */
	static public List<Operator> get_performed_operations(Node node) {
		List<Operator> operations = new Stack<>();
		operations.add(node.get_operator());
		Node parent = node.get_parent();

		while (parent != null) {
			operations.add(parent.get_operator());
			parent = parent.get_parent();
		}

		return operations;
	}

	/**
	 * Inverts the given solution.
	 *
	 * @param solution The solution to invert.
	 * @return the inverted solution.
	 */
	static public List<Operator> invert_solution(List<Operator> solution) {
		List<Operator> inverted_solution = new Stack<>();

		for (int i = solution.size() - 1; i >= 0; i--)
			inverted_solution.add(solution.get(i));

		return inverted_solution;
	}

	// nombre de funcion que verifica si el siguiente movimiento de un nodo no sea
	// el mismo a algun nodo ascendetes o ancestros del nodo actual:
	static public boolean is_repeated(Node node) {
		Node parent = node.get_parent();
		while (parent != null) {
			if (node.equals(parent))
				return true;
			parent = parent.get_parent();
		}
		return false;
	}
}
