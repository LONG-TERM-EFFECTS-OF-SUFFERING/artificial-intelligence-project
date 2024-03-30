package src.searchs;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import src.classes.Node;
import src.classes.Simulation;
import src.classes.Utilities;


public class AStar extends Search {
	private PriorityQueue <Node> queue;


	public AStar(String path) {
		super(path);
		queue = new PriorityQueue <>(new Comparator <Node>() {
			@Override
			/**
			 * This method compares two nodes based on their f(n) values.
			 * f(n) is the sum of the cost to reach the node (g(n)) and the heuristic or estimated cost.
			 * It is used to order the nodes in the priority queue for the A* algorithm.
			 *
			 * @param node1 The first node to compare.
			 * @param node2 The second node to compare.
			 * @return 1 if the f(n) of node1 is greater than the f(n) of node2,
			 *         -1 if the f(n) of node1 is less than the f(n) of node2,
			 *         0 if the f(n) of both nodes are equal.
			 */
			public int compare(Node node1, Node node2) {
				Simulation simulation = get_simulation();

				double node1_heuristic = Utilities.calculate_heuristic(node1, simulation.get_goal());
				double node2_heuristic = Utilities.calculate_heuristic(node2, simulation.get_goal());

				double fn1 = node1.get_cost() + node1_heuristic;
				double fn2 = node2.get_cost() + node2_heuristic;

				return Double.compare(fn1, fn2);
			}
		});

		queue.add(get_simulation().get_root());
	}


	/**
	 * Expands a given node by generating child nodes using available operators.
	 *
	 * @param node The node to be expanded.
	 */
	public void expand_node(Node node) {
		Simulation simulation = get_simulation();

		List <Simulation.Operator> availabe_operators = simulation.get_available_operators(node.get_player());

		for (Simulation.Operator operator : availabe_operators) {
			Node parent_node = node.get_parent();
			Node move_node = simulation.move(operator, node);

			if (move_node.equals(parent_node)) // Do not return
				continue;

			queue.add(move_node);
			super.increase_expanded_nodes();
		}
	}

	/**
	 * Runs the cost search algorithm to find a solution.
	 *
	 * @return a list of Simulation.Operators representing the performed operations
	 *         to reach the solution.
	 * @throws RuntimeException if a solution is not found.
	 */
	public Node run() {
		Simulation simulation = get_simulation();

		if (queue.isEmpty())
			throw new RuntimeException("Error: solution not found");

		Node node = queue.poll();

		if (simulation.is_goal(node)) {
			super.set_cost(node.get_cost());
			super.set_depth(node.get_depth());
			return node;
		} else {
			expand_node(node);
			return run();
		}
	}
}
