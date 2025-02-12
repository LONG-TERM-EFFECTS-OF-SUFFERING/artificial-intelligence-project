package src.searchs;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import src.classes.Node;
import src.classes.Simulation;


public class Cost extends Search {
	private PriorityQueue <Node> queue;


	public Cost(String path) {
		super(path);
		queue = new PriorityQueue <> (new Comparator <Node>() {
			@Override
			/**
			 * This method compares two nodes based on their cost.
			 * It is used to order the nodes in the priority queue.
			 *
			 * @param node1 The first node to compare.
			 * @param node2 The second node to compare.
			 * @return 1 if the cost of node1 is greater than the cost of node2,
			 *         -1 if the cost of node1 is less than the cost of node2,
			 *         0 if the costs of both nodes are equal.
			 */
			public int compare(Node node1, Node node2) {
				return Double.compare(node1.get_cost(), node2.get_cost());
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
	 * to reach the solution.
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
		}
		else {
			expand_node(node);
			return run();
		}
	}
}
