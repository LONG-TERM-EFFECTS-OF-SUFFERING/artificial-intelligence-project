package src.classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Simulation {
	private Coordinate goal = null;
	private Coordinate player = null;
	private Coordinate ship = null;
	private int columns;
	private int rows;
	private List <Coordinate> enemies = new ArrayList<>();
	private List <Coordinate> free_cells = new ArrayList<>();
	private List <Coordinate> obstacles = new ArrayList<>();
	private Node root;


	/**
	 * Returns the root node of the simulation.
	 *
	 * @return the root node of the simulation.
	 */
	public Node get_root() {
		return root;
	}

	/**
	 * Returns the number of rows in the simulation.
	 *
	 * @return the number of rows
	 */
	public int get_rows() {
		return rows;
	}

	/**
	 * Returns the number of columns in the simulation.
	 *
	 * @return the number of columns
	 */
	public int get_columns() {
		return columns;
	}

	/**
	 * Returns the goal coordinate in the simulation.
	 *
	 * @return the goal coordinate
	 */
	public Coordinate get_goal() {
		return goal;
	}

	/**
	 * Represents a simulation of a game.
	 * The simulation initializes the game state by reading it from a file,
	 * creates a starting node for the game, and checks if the goal and player are present.
	 */
	public Simulation(String path) {
		read_state_from_file(path);

		if (goal == null)
			throw new RuntimeException("Error: goal not found");
		else if (player == null)
			throw new RuntimeException("Error: player not found");

		root = new Node(player, null, null, 0, 0, 0);
	}

	/**
	 * Reads the state of the simulation from a file.
	 *
	 * @param path the path of the file to read
	 */
	private void read_state_from_file(String path) {
		List <String> lines = Collections.emptyList();

		try {
			lines = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		rows = lines.size();

		if (rows == 0)
			throw new RuntimeException("Error: empty file");

		columns = lines.get(0).split("\s").length; // rows.get(0).length() * 2 - 1;

		for (int i = 0; i < rows; i++) {
			String[] actual_row = lines.get(i).split("\s");

			for (int j = 0; j < columns; j++) {
				int cell = Integer.parseInt(actual_row[j]);

				if (cell == 5) {
					if (goal != null)
						throw new RuntimeException("Error: more than one goal");
					goal = new Coordinate(j, i);
				} else if (cell == 4)
					enemies.add(new Coordinate(j, i));
				else if (cell == 3) {
					if (ship != null)
						throw new RuntimeException("Error: more than one ship");
					ship = new Coordinate(j, i);
				} else if (cell == 2) {
					if (player != null)
						throw new RuntimeException("Error: more than one player");
					player = new Coordinate(j, i);
				} else if (cell == 1)
					obstacles.add(new Coordinate(j, i));
				else if (cell == 0)
					free_cells.add(new Coordinate(j, i));
			}
		}
	}

	/**
	 * Retrieves the game board representation as a 2D array.
	 * The board contains information about obstacles, free cells,
	 * enemies, player, goal, and ship.
	 *
	 * @param node The current game state represented by a Node object.
	 * @return the game board represented as a 2D array of integers.
	 */
	public int[][] get_board(Node node) {
		int[][] board = new int[rows][columns];

		for (Coordinate obstacle : obstacles)
			board[obstacle.get_y()][obstacle.get_x()] = 1;

		for (Coordinate free_cell : free_cells)
			board[free_cell.get_y()][free_cell.get_x()] = 0;

		for (Coordinate enemy : enemies)
			board[enemy.get_y()][enemy.get_x()] = 4;

		Coordinate node_player = node.get_player();

		board[goal.get_y()][goal.get_x()] = 5;
		board[node_player.get_y()][node_player.get_x()] = 2;

		if (!node.get_was_ship_taken())
			board[ship.get_y()][ship.get_x()] = 3;

		return board;
	}

	/**
	 * Checks if the current player position is the goal position.
	 *
	 * @return true if the player position is the goal position, false otherwise.
	 */
	public boolean is_goal(Node node) {
		return node.get_player().equals(goal);
	}

	/**
	 * Checks if the given coordinate is a valid coordinate to move in.
	 * A coordinate is considered valid if it is within the boundaries of the board
	 * and does not contain any obstacles.
	 *
	 * @param coordinate The coordinate to check.
	 * @return true if the coordinate is valid, false otherwise.
	 */
	private boolean is_valid_cordinate_to_move_in(Coordinate coordinate) {
		int x = coordinate.get_x();
		int y = coordinate.get_y();

		boolean is_in_board = 0 <= y && y < rows && 0 <= x && x < columns;

		return is_in_board && obstacles.indexOf(coordinate) == -1;
	}

	/**
	 * Returns a list of available operators for the player.
	 *
	 * @return a list of available operators.
	 */
	public List <Operator> get_available_operators(Coordinate player) {
		List <Operator> operators = new ArrayList <>();

		int x = player.get_x();
		int y = player.get_y();

		Coordinate up = new Coordinate(x, y - 1);
		Coordinate right = new Coordinate(x + 1, y);
		Coordinate bottom = new Coordinate(x, y + 1);
		Coordinate left = new Coordinate(x - 1, y);

		if (is_valid_cordinate_to_move_in(up))
			operators.add(Operator.UP);
		if (is_valid_cordinate_to_move_in(right))
			operators.add(Operator.RIGHT);
		if (is_valid_cordinate_to_move_in(bottom))
			operators.add(Operator.BOTTOM);
		if (is_valid_cordinate_to_move_in(left))
			operators.add(Operator.LEFT);

		return operators;
	}

	/**
	 * Moves the player of the given node in the specified direction.
	 *
	 * @param operator The direction in which to move the player.
	 * @param Node     The node to which you will move player.
	 */
	public Node move(Operator operator, Node node) {
		Coordinate node_player = node.get_player();
		int x = node_player.get_x();
		int y = node_player.get_y();
		int new_x = -1, new_y = -1;
		double cost = 0;

		switch (operator) {
			case Operator.UP:
				new_x = x;
				new_y = y - 1;
				break;
			case Operator.LEFT:
				new_x = x - 1;
				new_y = y;
				break;
			case Operator.BOTTOM:
				new_x = x;
				new_y = y + 1;
				break;
			case Operator.RIGHT:
				new_x = x + 1;
				new_y = y;
				break;
			default:
				throw new RuntimeException("Error: invalid operator");
		}

		Coordinate new_player = new Coordinate(new_x, new_y);

		int ship_fuel = node.get_ship_fuel();
		boolean is_on_ship = Utilities.is_on_ship(ship_fuel);

		if (is_on_ship) {
			cost += 0.5;
			ship_fuel--;
		} else
			cost += 1;

		if (enemies.indexOf(new_player) != -1)
			if (!is_on_ship)
				cost += 4;

		Node new_node = new Node(new_player, node, operator, node.get_depth() + 1,
				node.get_cost() + cost, ship_fuel);

		if (!node.get_was_ship_taken() && new_player.equals(ship))
			new_node.take_ship();

		return new_node;
	}

	/**
	 * Represents the possible operators for movement in a simulation.
	 */
	static public enum Operator {
		UP,
		RIGHT,
		BOTTOM,
		LEFT,
	}
}
