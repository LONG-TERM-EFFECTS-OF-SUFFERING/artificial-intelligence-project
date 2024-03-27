package src.classes;

import java.util.ArrayList;
import java.util.List;

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
				return "UP";
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
	 * Returns a list of ancestor nodes for the given node.
	 *
	 * @param node the node for which to find ancestor nodes
	 * @return a list of ancestor nodes in reverse order
	 */
	static public List <Node> get_ancestor_nodes(Node node) {
		List <Node> nodes = new ArrayList <Node>();

		while (node != null) {
			nodes.add(node);
			node = node.get_parent();
		}

		return nodes.reversed();
	}

	/**
	 * Checks if a given node is repeated in its parent hierarchy.
	 *
	 * @param node the node to check for repetition.
	 * @return true if the node is repeated, false otherwise.
	 */
	static public boolean is_repeated(Node node) {
		Node parent = node.get_parent();
		while (parent != null) {
			if (node.equals(parent))
				return true;
			parent = parent.get_parent();
		}
		return false;
	}

	/**
	 * Calculates the heuristic for a given node using the Manhattan distance
	 * divided by two.
	 *
	 * @param node The node for which to calculate the heuristic.
	 * @param goal The goal coordinate.
	 * @return The calculated heuristic.
	 */
	static public double calculateHeuristic(Node node, Coordinate goal) {
		Coordinate player = node.get_player();
		int xDifference = Math.abs(player.get_x() - goal.get_x());
		int yDifference = Math.abs(player.get_y() - goal.get_y());
		double manhattanDistance = xDifference + yDifference;
		return manhattanDistance / 2;
	}
}
