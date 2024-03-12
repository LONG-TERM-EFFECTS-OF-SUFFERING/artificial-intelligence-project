package src.searchs;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import src.classes.Node;
import src.classes.Simulation;
import src.classes.Utilities;


public class Amplitude {
	private Queue <Node> queue = new LinkedList <>();
	private Simulation simulation;
	private int expanded_nodes = 0;
	private int cost;


	public Amplitude(String path) {
		simulation = new Simulation(path);
		queue.add(simulation.get_root());
	}

	/**
	 * Returns the number of expanded nodes.
	 *
	 * @return the number of expanded nodes.
	 */
	public int get_expanded_nodes() {
		return expanded_nodes;
	}

	/**
	 * Returns the cost associated with the preferential search by amplitude.
	 *
	 * @return the cost.
	 */
	public int get_cost() {
		return cost;
	}

	/**
	 * Expands a given node by generating child nodes using available operators.
	 *
	 * @param node The node to be expanded.
	 */
	public void expand_node(Node node) {
		List <Simulation.Operator> availabe_operators = simulation.get_available_operators(node.get_player());
		Simulation.Operator node_operator = node.get_operator();
		Simulation.Operator node_opposite_operator = Utilities.get_opposite_operator(node_operator);

		for (Simulation.Operator operator : availabe_operators) {
			if (operator == node_opposite_operator) // Do not return
				continue;

			queue.add(simulation.move(operator, node));
			expanded_nodes++;
		}
	}

	/**
	 * Runs the amplitude search algorithm to find a solution.
	 *
	 * @return a list of Simulation.Operators representing the performed operations
	 * to reach the solution.
	 * @throws RuntimeException if a solution is not found.
	 */
	public List <Simulation.Operator> run() {
		if (queue.isEmpty())
			throw new RuntimeException("Error: solution not found");

		Node node = queue.remove();

		if (simulation.is_goal(node)) {
			cost = node.get_cost();
			return Utilities.get_performed_operations(node);
		} else {
			expand_node(node);
			return run();
		}
	}
}
