package src.windows;

import javax.swing.*;

import src.classes.Simulation;
import src.searchs.*;

import java.awt.*;
import java.awt.event.*;


public class MenuWindow extends JFrame {
	private JButton start_button;
	private JButton select_file_button;
	private JComboBox <String> algorithm_combo_box;
	private String path = "./src/professor_test.txt"; // TODO: ask for the test file in a dialog


	public MenuWindow() {
		Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
		int screen_width = (int) screen_size.getWidth();
		int screen_height = (int) screen_size.getHeight();
		int width = (int) (screen_width * 0.2);
		int height = (int) (screen_height * 0.3);

		setTitle("Smart Mandalorian");
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel main_panel = new JPanel();
		main_panel.setLayout(new GridLayout(2, 2));

		JLabel title_label = new JLabel("Select a search algorithm:");
		main_panel.add(title_label);

		String[] algorithms = { "Amplitude", "Cost", "Avara", "A*" };
		algorithm_combo_box = new JComboBox <>(algorithms);
		main_panel.add(algorithm_combo_box);

		start_button = new JButton("Start simulation");
		start_button.addActionListener(new start_button_listener());
		main_panel.add(start_button);

		select_file_button = new JButton("Select test file");
		// select_file_button.addActionListener(new action_listener());
		main_panel.add(select_file_button);

		add(main_panel);
		setLocationRelativeTo(null);
	}

	private class start_button_listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String selected_algorithm = (String) algorithm_combo_box.getSelectedItem();
			Simulation simulation = new Simulation(path);
			Search search;

			switch (selected_algorithm) {
				case "Amplitude" -> search = new Amplitude(path);
				case "Cost" -> search = new Cost(path);
				case "Depth" -> search = new Depth(path);
				case "Avara" -> search = new Avara(path);
				default -> search = new AStar(path);
			}
			dispose();
			SimulationWindow simulation_window = new SimulationWindow(simulation, search);
			simulation_window.setVisible(true);
		}
	}
}
