package src.classes;

public class Node {
	private Coordinate player = null;
	private Node parent;
	private Simulation.Operator operator;
	private int depth;
	private double cost;
	private boolean was_ship_taken = false;
	private int ship_fuel;

	/**
	 * Constructs a new Node object.
	 *
	 * @param player    The coordinate of the player.
	 * @param parent    The parent node.
	 * @param operator  The operator applied to reach this node.
	 * @param depth     The depth of this node in the search tree.
	 * @param cost      The cost associated with reaching this node.
	 * @param ship_fuel The remaining fuel of the ship.
	 */
	public Node(Coordinate player, Node parent, Simulation.Operator operator,
			int depth, double cost, int ship_fuel) {
		this.player = player;
		this.parent = parent;
		this.operator = operator;
		this.depth = depth;
		this.cost = cost;
		this.ship_fuel = ship_fuel;
	}

	/**
	 * Returns the coordinate of the player in the node.
	 *
	 * @return the coordinate of the player.
	 */
	public Coordinate get_player() {
		return player;
	}

	/**
	 * Returns the parent node of the node.
	 *
	 * @return the parent node.
	 */
	public Node get_parent() {
		return parent;
	}

	/**
	 * Returns the depth of the node in the search tree.
	 *
	 * @return the depth of the node.
	 */
	public int get_depth() {
		return depth;
	}

	/**
	 * Returns the operator used to reach the node.
	 *
	 * @return the operator used to reach the node.
	 */
	public Simulation.Operator get_operator() {
		return operator;
	}

	/**
	 * Returns the cost associated with reaching the node.
	 *
	 * @return the cost of the node.
	 */
	public double get_cost() {
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

	/**
	 * Returns the amount of fuel in the ship.
	 *
	 * @return the amount of fuel in the ship
	 */
	public int get_ship_fuel() {
		return ship_fuel;
	}

	/**
	 * Takes the ship and sets the ship fuel to 10.
	 */
	public void take_ship() {
		was_ship_taken = true;
		ship_fuel = 10;
	}

	/**
	 * Decreases the ship's fuel by one unit.
	 */
	public void decrease_ship_fuel() {
		ship_fuel--;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || getClass() != obj.getClass())
			return false;

		Node other = (Node) obj;
		boolean other_is_on_ship = Utilities.is_on_ship(other.get_ship_fuel());
		Coordinate other_player = other.get_player();

		return player.equals(other_player) && Utilities.is_on_ship(ship_fuel) == other_is_on_ship;
	}
}
