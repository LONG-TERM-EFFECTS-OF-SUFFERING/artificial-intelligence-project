package src.windows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

import src.classes.ImageCollection;
import src.classes.Node;
import src.classes.Simulation;
import src.classes.Utilities;
import src.searchs.Search;


public class SimulationWindow extends JFrame implements ActionListener {
	private boolean is_running = false;
	private ImageCollection image_collection;
	private int cols;
	private int current_node_index = 0;
	private int rows;
	private int window_height;
	private int window_width;
	private JButton start_button;
	private JButton status_button;
	private JDialog result_dialog;
	private JLabel status;
	private JPanel button_panel;
	private JPanel grid_panel;
	private JPanel main_panel;
	private List<Node> nodes;
	private Search search;
	private Timer timer;


	public SimulationWindow(Simulation simulation, Search search) {
		this.search = search;
		this.rows = simulation.get_rows();
		this.cols = simulation.get_columns();

		nodes = Utilities.get_ancestor_nodes(search.run());

		timer = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (current_node_index < nodes.size()) {
					Node current_node = nodes.get(current_node_index++);
					update_grid(simulation.get_board(current_node));
					String operator = Utilities.operator_to_string(current_node.get_operator());
					status.setText(status.getText() + operator + " ");
				} else {
					((Timer) e.getSource()).stop();
					is_running = false;
					display_simulation_information();
				}
			}
		});

		Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
		int screen_width = (int) screen_size.getWidth();
		int screen_height = (int) screen_size.getHeight();

		window_width = (int) (screen_width * 0.3);
		window_height = (int) (screen_height * 0.5);

		int grid_label_width = (int) (window_width * 0.1);
		int grid_label_height = (int) (window_height * 0.075);

		setTitle("Simulation");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(window_width, window_height);
		setResizable(false);
		setIconImage((new ImageIcon("./src/assets/icon.png").getImage()));

		image_collection = new ImageCollection(grid_label_width, grid_label_height);

		main_panel = new JPanel();
		main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
		add(main_panel);

		grid_panel = new JPanel(new GridLayout(rows, cols));
		main_panel.add(grid_panel);

		button_panel = new JPanel();
		main_panel.add(button_panel);

		start_button = new JButton("Start");
		start_button.addActionListener(this);

		status_button = new JButton("Pause");
		status_button.addActionListener(this);

		JButton menu_button = new JButton("Menu");
		menu_button.addActionListener(this);

		button_panel.add(start_button);
		button_panel.add(status_button);
		button_panel.add(menu_button);

		status = new JLabel("Status: ");
		button_panel.add(status);

		main_panel.add(Box.createVerticalStrut(10));

		for (int i = 0; i < rows * cols; i++) {
			JLabel label = new JLabel();
			label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			grid_panel.add(label);
			label.setSize(grid_label_width, grid_label_height);
		}

		setLocationRelativeTo(null);
	}


	/**
	 * Displays the simulation information in a dialog window.
	 */
	private void display_simulation_information() {
		result_dialog = new JDialog(this, "Simulation results", true);
		result_dialog.setSize((int) (window_width * 0.3), (int) (window_height * 0.3));
		result_dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		result_dialog.setResizable(false);

		JPanel result_panel = new JPanel();
		result_panel.setLayout(new BoxLayout(result_panel, BoxLayout.Y_AXIS));

		JLabel expanded_nodes_label = new JLabel("Expanded Nodes: " + search.get_expanded_nodes());
		JLabel cost_label = new JLabel("Cost: " + search.get_cost());
		JLabel depth_label = new JLabel("Depth: " + search.get_depth());

		result_panel.add(Box.createVerticalGlue());
		result_panel.add(expanded_nodes_label);
		result_panel.add(cost_label);
		result_panel.add(depth_label);
		result_panel.add(Box.createVerticalGlue());

		result_panel.setAlignmentX(Component.CENTER_ALIGNMENT);

		result_dialog.add(result_panel, BorderLayout.CENTER);

		result_dialog.setVisible(true);
	}

	/**
	 * Updates the grid with the given board state.
	 *
	 * @param grid The board state to update the grid with.
	 */
	private void update_grid(int[][] grid) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				JLabel label = (JLabel) grid_panel.getComponent(i * cols + j);
				int value = grid[i][j];
				ImageIcon icon;

				switch (value) {
					case 0 -> icon = image_collection.get_image_icon("empty");
					case 1 -> icon = image_collection.get_image_icon("obstacle");
					case 2 -> icon = image_collection.get_image_icon("Mandalorian");
					case 3 -> icon = image_collection.get_image_icon("ship");
					case 4 -> icon = image_collection.get_image_icon("Stormtropper");
					default -> icon = image_collection.get_image_icon("Grogu");
				}

				label.setIcon(icon);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String button_name = e.getActionCommand();

		switch (button_name) {
			case "Start" -> {
				if (!is_running) {
					is_running = true;
					timer.start();
				}

				start_button.setEnabled(false);
			}
			case "Pause" ->  {
				is_running = false;
				timer.stop();
				status_button.setText("Continue");
			}
			case "Continue" -> {
				is_running = true;
				timer.start();
				status_button.setText("Pause");
			}
			case "Menu" -> {
				dispose();
				MenuWindow menu = new MenuWindow();
				menu.setVisible(true);
			}
		}
	}
}
