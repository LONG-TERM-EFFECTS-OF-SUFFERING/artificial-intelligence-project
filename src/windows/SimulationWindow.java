package src.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import src.classes.Node;
import src.classes.Simulation;
import src.classes.Utilities;
import src.searchs.Search;


public class SimulationWindow extends JFrame implements ActionListener {
	private Simulation simulation;
	private Search search;
	private int rows;
	private int cols;
	private boolean is_running = false;
	private JPanel main_panel;
	private JPanel button_panel;
	private JPanel grid_panel;
	private JLabel status;
	private List <Node> nodes;
	private int current_node = 0;


	public SimulationWindow(Simulation simulation, Search search) {
		this.simulation = simulation;
		this.search = search;
		this.rows = simulation.get_rows();
		this.cols = simulation.get_columns();

		nodes = Utilities.get_ancestor_nodes(search.run());

		Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
		int screen_width = (int) screen_size.getWidth();
		int screen_height = (int) screen_size.getHeight();
		int width = (int) (screen_width * 0.3);
		int height = (int) (screen_height * 0.4);

		setTitle("Simulation");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(width, height);
		// setResizable(false);

		main_panel = new JPanel();
		main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
		add(main_panel);

		grid_panel = new JPanel(new GridLayout(rows, cols));
		main_panel.add(grid_panel);

		button_panel = new JPanel();
		main_panel.add(button_panel);

		JButton start_button = new JButton("Start");
		start_button.addActionListener(this);
		JButton pause_button = new JButton("Pause");
		pause_button.addActionListener(this);
		JButton menu_button = new JButton("Menu");
		menu_button.addActionListener(this);
		button_panel.add(start_button);
		button_panel.add(pause_button);
		button_panel.add(menu_button);

		status = new JLabel("Status: ");
		button_panel.add(status);

		main_panel.add(Box.createVerticalStrut(10));

		for (int i = 0; i < rows * cols; i++) {
			JLabel label = new JLabel();
			label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			grid_panel.add(label);
		}

		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Start")) {
			if (!is_running) {
				is_running = true;
				run_simulation();
			}
		} else if (e.getActionCommand().equals("Pause"))
			is_running = false;
		else if (e.getActionCommand().equals("Menu")) {
			dispose();
			MenuWindow menu = new MenuWindow();
			menu.setVisible(true);
		}
	}

	private void update_grid(int[][] grid) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				JLabel label = (JLabel) grid_panel.getComponent(i * cols + j);
				int value = grid[i][j];
				Color background_color;
/* 				switch (value) {
					case 0 -> label.setIcon(null);
					case 1 -> label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
					case 2 -> label.setIcon(new ImageIcon("goal.png"));
					case 3 -> label.setIcon(new ImageIcon("enemy.png"));
				} */
				switch (value) {
					case 0 -> background_color = Color.WHITE;
					case 1 -> background_color = Color.BLACK;
					case 2 -> background_color = Color.BLUE;
					case 3 -> background_color = Color.MAGENTA;
					case 4 -> background_color = Color.RED;
					default -> background_color = Color.YELLOW; // 5
				}

				label.setBackground(background_color);
				label.setOpaque(true);
			}
		}
	}

	public void run_simulation() {
		System.out.println("Expanded nodes: " + search.get_expanded_nodes());
		System.out.println("Cost: " + search.get_cost());
		System.out.println("Depth: " + search.get_depth());

		Timer timer = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Actualizar la cuadrícula con el nodo actual
				if (current_node < nodes.size()) {
					Node currentNode = nodes.get(current_node++);
					update_grid(simulation.get_board(currentNode));
					String operator = Utilities.operator_to_string(currentNode.get_operator());
					status.setText(status.getText() + operator + " ");
				} else {
					((Timer) e.getSource()).stop();
					is_running = false;
				}
			}
		});

		timer.start();
	}
}
