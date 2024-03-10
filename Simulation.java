import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * The Simulation class represents a simulation of a game board.
 * It contains information about the state of the board, such as the player's position,
 * the goal position, obstacles, enemies, and the dimensions of the board.
 * The class provides methods to read the state from a file, print the current state of the board,
 * check if the player has reached the goal, get the available moves for the player,
 * and move the player in a specified direction.
 */
public class Simulation {
	private Node actual_node;
	private int height;
	private int width;
	private List <Coordinate> enemies = new ArrayList <>();
	private List <Coordinate> obstacles = new ArrayList <>();
	private List <Coordinate> free_cells = new ArrayList <>();
	private Coordinate goal = null;
	private Coordinate player = null;
	private Coordinate ship = null;


	/**
	 * Represents a simulation of a game.
	 * The simulation initializes the game state by reading it from a file,
	 * creates a starting node for the game, and checks if the goal and player are present.
	 */
	public Simulation(String path) {
		read_state_from_file(path);
		actual_node = new Node(player, null, -1, 0, 0, false);

		if (goal == null)
			throw new RuntimeException("Error: goal not found");
		else if (player == null)
			throw new RuntimeException("Error: player not found");
	}

	/**
	 * Returns the height of the object.
	 *
	 * @return the height of the object
	 */
	public int get_height() {
		return height;
	}

	/**
	 * Returns the width of the simulation.
	 *
	 * @return the width of the simulation
	 */
	public int get_width() {
		return width;
	}

	/**
	 * Reads the state of the puzzle from a file.
	 *
	 * @param path the path of the file to read from
	 * @return a 2D array representing the puzzle state
	 * @throws RuntimeException if the file is empty
	 */
	private void read_state_from_file(String path) {
		List <String> rows = Collections.emptyList();

		try {
			rows = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		height = rows.size();

		if (height == 0)
			throw new RuntimeException("Error: empty file");

		width = rows.get(0).split("\s").length; // rows.get(0).length() * 2 - 1;

		int[][] puzzle = new int[height][width];

		for (int i = 0; i < height; i++) {
			String[] actual_row = rows.get(i).split("\s");

			for (int j = 0; j < width; j++) {
				int cell = Integer.parseInt(actual_row[j]);
				puzzle[i][j] = cell;

				if (cell == 5)
					goal = new Coordinate(j, i);
				else if (cell == 4)
					enemies.add(new Coordinate(j, i));
				else if (cell == 3)
					ship = new Coordinate(j, i);
				else if (cell == 2)
					player = new Coordinate(j, i);
				else if (cell == 1)
					obstacles.add(new Coordinate(j, i));
				else if (cell == 0)
					free_cells.add(new Coordinate(j, i));
			}
		}
	}

	/**
	 * Prints the current state of the simulation board.
	 * The board is represented by a 2D array, where each cell represents a specific element:
	 * - 0: Empty cell
	 * - 1: Obstacle
	 * - 2: Player
	 * - 3: Ship
	 * - 4: Enemy
	 * - 5: Goal
	 */
	public void print_state() {
		int[][] board = new int[height][width];

		for (Coordinate obstacle : obstacles)
			board[obstacle.get_y()][obstacle.get_x()] = 1;

		for (Coordinate enemy : enemies)
			board[enemy.get_y()][enemy.get_x()] = 4;

		board[player.get_y()][player.get_x()] = 2;
		board[goal.get_y()][goal.get_x()] = 5;
		board[ship.get_y()][ship.get_x()] = 3;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++)
				System.out.print(board[i][j] + " ");

			System.out.println();
		}
	}

	/**
	 * Checks if the current player position is the goal position.
	 *
	 * @return true if the player position is the goal position, false otherwise.
	 */
	public boolean is_goal() {
		return player == goal;
	}

	/**
	 * Checks if the given coordinate is a valid coordinate to move in.
	 * A coordinate is considered valid if it is within the boundaries of the board
	 * and does not contain any obstacles.
	 *
	 * @param coordinate the coordinate to check
	 * @return true if the coordinate is valid, false otherwise
	 */
	private boolean is_valid_cordinate_to_move_in(Coordinate coordinate) {
		int x = coordinate.get_x();
		int y = coordinate.get_y();

		boolean is_in_board = 0 <= y && y < height && 0 <= x && x < width;

		return is_in_board && obstacles.indexOf(coordinate) == -1;
	}

	/**
	 * Returns a list of available moves for the player.
	 *
	 * @return a list of integers representing the available moves
	 */
	public List <Integer> get_available_moves() {
		List <Integer> moves = new ArrayList <>();

		int x = player.get_x();
		int y = player.get_y();

		Coordinate up = new Coordinate(x, y - 1);
		Coordinate right = new Coordinate(x + 1, y);
		Coordinate bottom = new Coordinate(x, y + 1);
		Coordinate left = new Coordinate(x - 1, y);

		if (is_valid_cordinate_to_move_in(up))
			moves.add(Simulation.UP);
		if (is_valid_cordinate_to_move_in(right))
			moves.add(Simulation.RIGHT);
		if (is_valid_cordinate_to_move_in(bottom))
			moves.add(Simulation.BOTTOM);
		if (is_valid_cordinate_to_move_in(left))
			moves.add(Simulation.LEFT);

		return moves;
	}

	/**
	 * Moves the player in the specified direction.
	 *
	 * @param operator the direction in which to move the player
	 */
	public void move(int operator) {
		int x = player.get_x();
		int y = player.get_y();
		int new_x = -1, new_y = -1, cost = 1;

		switch (operator) {
			case Simulation.UP:
				new_x = x;
				new_y = y - 1;
				break;
			case Simulation.LEFT:
				new_x = x - 1;
				new_y = y;
				break;
			case Simulation.BOTTOM:
				new_x = x;
				new_y = y + 1;
				break;
			case Simulation.RIGHT:
				new_x = x + 1;
				new_y = y;
				break;
		}

		player.set_x(new_x);
		player.set_y(new_y);
		boolean was_ship_taken = false;

		if (enemies.indexOf(player) != -1)
			cost += 3;
		else if (player == ship)
			was_ship_taken = true;

		actual_node = new Node(player, actual_node, operator, actual_node.get_depth() + 1,
			actual_node.get_cost() + cost, was_ship_taken);
	}

	public void run() {

	}

	/* -------------------------------- OPERATORS ------------------------------- */

	public static final int UP = 1;
	public static final int RIGHT = 2;
	public static final int BOTTOM = 3;
	public static final int LEFT = 4;
}
