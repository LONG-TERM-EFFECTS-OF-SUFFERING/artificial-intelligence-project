import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;


public class Simulation {
	private int height;
	private int width;
	private int[][] initial_state;

	public Simulation(String path) {
		set_initial_state(path);
	}

	/**
	 * Sets the initial state of the puzzle by reading the puzzle data from a file.
	 * The file should contain a grid of numbers representing the puzzle.
	 * Each number represents a cell in the puzzle.
	 *
	 * @param path the path to the file containing the puzzle data
	 * @throws RuntimeException if the puzzle is invalid (empty or inconsistent dimensions)
	 */
	private void set_initial_state(String path) {
		List <String> rows = Collections.emptyList();

		try {
			rows = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		height = rows.size();

		if (height == 0)
			throw new RuntimeException("Invalid puzzle!");

		width = rows.get(0).split("\s").length; // rows.get(0).length() * 2 - 1;

		int[][] puzzle = new int[height][width];

		for (int i = 0; i < height; i++) {
			String[] actual_row = rows.get(i).split("\s");

			for (int j = 0; j < width; j++)
				puzzle[i][j] = Integer.parseInt(actual_row[j]);
		}

		initial_state = puzzle;
	}

	/**
	 * Prints the initial state of the simulation.
	 */
	public void print_initial_state() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(initial_state[i][j] + " ");
			}
			System.out.println();
		}
	}
}
