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


	public Amplitude(String path) {
		simulation = new Simulation(path);
		queue.add(simulation.get_root());
	}

	public void expand_node(Node node) {
		List <Simulation.Operator> availabe_operators = simulation.get_available_operators(node.get_player());
		Simulation.Operator node_operator = node.get_operator();
		Simulation.Operator node_opposite_operator = Utilities.get_opposite_operator(node_operator);

		for (Simulation.Operator operator : availabe_operators) {
			if (operator == node_opposite_operator) // Do not return
				continue;

			// System.out.println();

			Node new_node = simulation.move(operator, node);
			// simulation.print_state(new_node);
			queue.add(new_node);
		}
	}

	public List <Simulation.Operator> run() {
		if (queue.isEmpty())
			throw new RuntimeException("Error: solution not found");

		Node node = queue.remove();

		if (simulation.is_goal(node)) {
			return Utilities.get_performed_operations(node);
		} else {
			expand_node(node);
			return run();
		}
	}

	public void testing() {
		if (queue.isEmpty())
			throw new RuntimeException("Error: solution not found");

		Node node = queue.remove();

		expand_node(node);
	}
}
