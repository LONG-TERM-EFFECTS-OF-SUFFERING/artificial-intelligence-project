package src.searchs;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import src.classes.Node;
import src.classes.Simulation;
import src.classes.Simulation.Operator;
import src.classes.Utilities;

public class Depth extends Search {
	private Stack <Node> stack = new Stack<>();

	public Depth(String path) {
		super(path);
		stack.add(get_simulation().get_root());
	}

	@Override
	public void expand_node(Node node) {
		Simulation simulation = get_simulation();

		List <Simulation.Operator> availabe_operators = simulation.get_available_operators(node.get_player());

		for (Simulation.Operator operator : availabe_operators) {
			Node move_node = simulation.move(operator, node);

			if (Utilities.is_repeated(move_node)) // Do not return and avoid loops
				continue;

			stack.add(move_node);
			super.increase_expanded_nodes();
		}
	}

	@Override
	public Node run() {
		Simulation simulation = get_simulation();

		if (stack.isEmpty())
			throw new RuntimeException("Error: solution not found");

		Node node = stack.pop();

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
