/**
 * Represents a node in a search tree.
 */
public class Node {
	private Coordinate player = null;
	private Node parent;
	private int operator;
	private int depth;
	private int cost;
	private boolean was_ship_taken;

	/**
	 * Constructs a new Node object.
	 *
	 * @param player           the coordinate of the player
	 * @param parent           the parent node
	 * @param operator         the operator used to reach this node
	 * @param depth            the depth of this node in the search tree
	 * @param cost             the cost associated with reaching this node
	 * @param was_ship_taken   indicates if the ship was taken in this node
	 */
	public Node(Coordinate player, Node parent, int operator,
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
	 * @return the coordinate of the player
	 */
	public Coordinate get_player() {
		return player;
	}

	/**
	 * Returns the parent node of this node.
	 *
	 * @return the parent node
	 */
	public Node get_parent() {
		return parent;
	}

	/**
	 * Returns the depth of this node in the search tree.
	 *
	 * @return the depth of this node
	 */
	public int get_depth() {
		return depth;
	}

	/**
	 * Returns the operator used to reach this node.
	 *
	 * @return the operator used to reach this node
	 */
	public int get_operator() {
		return operator;
	}

	/**
	 * Returns the cost associated with reaching this node.
	 *
	 * @return the cost of this node
	 */
	public int get_cost() {
		return cost;
	}
}
