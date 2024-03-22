package src.searchs;

import java.util.List;

import src.classes.Node;
import src.classes.Simulation;


abstract public class Search {
	private Simulation simulation;
	private int expanded_nodes = 0;
	private double cost;
	private int depth;


	public Search(String path) {
		simulation = new Simulation(path);
	}


	/**
	 * Returns the simulation object.
	 *
	 * @return The simulation object.
	 */
	public Simulation get_simulation() {
		return simulation;
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
	 * Increases the count of expanded nodes by one.
	 */
	public void increase_expanded_nodes() {
		expanded_nodes++;
	}

	/**
	 * Returns the cost associated with the preferential search by amplitude.
	 *
	 * @return the cost.
	 */
	public double get_cost() {
		return cost;
	}

	/**
	 * Sets the cost of the search.
	 *
	 * @param cost the cost value to set.
	 */
	public void set_cost(double cost) {
		this.cost = cost;
	}

	/**
	 * Returns the depth of the search.
	 *
	 * @return the depth of the search
	 */
	public int get_depth() {
		return depth;
	}

	/**
	 * Sets the depth of the search.
	 *
	 * @param depth the depth of the search
	 */
	public void set_depth(int depth) {
		this.depth = depth;
	}

	/**
	 * Expands a given node by generating child nodes using available operators.
	 *
	 * @param node The node to be expanded.
	 */
	abstract public void expand_node(Node node);


	/**
	 * Runs the search algorithm and returns a list of nodes.
	 *
	 * @return a list of nodes representing the search results
	 */
	abstract public Node run();
}
