package src.classes;


public class Node {
	private Coordinate player = null;
	private Node parent;
	private Simulation.Operator operator;
	private int depth;
	private int cost;
	private boolean was_ship_taken;


	/**
	 * Constructs a new Node object.
	 *
	 * @param player         The coordinate of the player.
	 * @param parent         The parent node.
	 * @param operator       The operator used to reach this node.
	 * @param depth          The depth of this node in the search tree.
	 * @param cost           The cost associated with reaching this node.
	 * @param was_ship_taken The boolean that indicates if the ship was taken in
	 *                       this node.
	 */
	public Node(Coordinate player, Node parent, Simulation.Operator operator,
				int depth, int cost, boolean was_ship_taken) {
		this.player = player;
		this.parent = parent;
		this.operator = operator;
		this.depth = depth;
		this.cost = cost;
		this.was_ship_taken = was_ship_taken;
	}

	/**
	 * Returns the coordinate of the player in this node.
	 *
	 * @return the coordinate of the player.
	 */
	public Coordinate get_player() {
		return player;
	}

	/**
	 * Returns the parent node of this node.
	 *
	 * @return the parent node.
	 */
	public Node get_parent() {
		return parent;
	}

	/**
	 * Returns the depth of this node in the search tree.
	 *
	 * @return the depth of this node.
	 */
	public int get_depth() {
		return depth;
	}

	/**
	 * Returns the operator used to reach this node.
	 *
	 * @return the operator used to reach this node.
	 */
	public Simulation.Operator get_operator() {
		return operator;
	}

	/**
	 * Returns the cost associated with reaching this node.
	 *
	 * @return the cost of this node.
	 */
	public int get_cost() {
		return cost;
	}

	/**
	 * Returns the value indicating whether the ship was taken or not.
	 *
	 * @return true if the ship was taken, false otherwise.
	 */
	public boolean get_was_ship_taken() {
		return was_ship_taken;
	}
}
