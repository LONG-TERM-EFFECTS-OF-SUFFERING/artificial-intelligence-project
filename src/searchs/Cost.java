package src.searchs;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import src.classes.Node;
import src.classes.Simulation;
import src.classes.Utilities;

public class Cost extends Search {
  private PriorityQueue<Node> queue = new PriorityQueue<>();

  public Cost(String path) {
    super(path);
    queue = new PriorityQueue<>(new Comparator<Node>() {
      @Override
      public int compare(Node nodo1, Node nodo2) {
        return Double.compare(nodo1.get_cost(), nodo2.get_cost());
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

    List<Simulation.Operator> availabe_operators = simulation.get_available_operators(node.get_player());
    Simulation.Operator node_operator = node.get_operator();
    Simulation.Operator node_opposite_operator = Utilities.get_opposite_operator(node_operator);

    for (Simulation.Operator operator : availabe_operators) {
      if (operator == node_opposite_operator) // Do not return
        continue;

      queue.add(simulation.move(operator, node));
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
	public List <Simulation.Operator> run() {
		Simulation simulation = get_simulation();

		if (queue.isEmpty())
			throw new RuntimeException("Error: solution not found");

		Node node = queue.poll();

		if (simulation.is_goal(node)) {
			super.set_cost(node.get_cost());
			super.set_depth(node.get_depth());
			return Utilities.get_performed_operations(node);
		}
		else {
			expand_node(node);
			return run();
		}
	}

}
