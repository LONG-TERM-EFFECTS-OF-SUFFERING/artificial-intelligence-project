package src.searchs;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import src.classes.Node;
import src.classes.Simulation;
import src.classes.Utilities;

public class Avara extends Search {
  private PriorityQueue<Node> queue;

  public Avara(String path) {
    super(path);
    queue = new PriorityQueue<>(new Comparator<Node>() {
      @Override
      public int compare(Node node1, Node node2) {
        Simulation simulation = get_simulation();

        double heuristic1 = Utilities.calculateHeuristic(node1, simulation.get_goal());
        double heuristic2 = Utilities.calculateHeuristic(node2, simulation.get_goal());
        return heuristic1 > heuristic2 ? 1 : (heuristic1 < heuristic2 ? -1 : 0);
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
