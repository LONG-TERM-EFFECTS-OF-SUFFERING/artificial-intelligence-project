package src.windows;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

import src.classes.Simulation;
import src.searchs.*;


public class MenuWindow extends JFrame implements ActionListener {
	private JButton select_file_button;
	private JButton start_button;
	private JComboBox<String> algorithm_combo_box;
	private String path = "./src/test.txt";


	public MenuWindow() {
		Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
		int screen_width = (int) screen_size.getWidth();
		int screen_height = (int) screen_size.getHeight();
		int width = (int) (screen_width * 0.2);
		int height = (int) (screen_height * 0.3);

		setTitle("Smart Mandalorian");
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setIconImage(new ImageIcon("./src/assets/icon.png").getImage());

		JPanel main_panel = new JPanel();
		main_panel.setLayout(new GridLayout(2, 2));

		JLabel title_label = new JLabel("Select a search algorithm:");
		main_panel.add(title_label);

		String[] algorithms = { "Amplitude", "Cost", "Avara", "A*" };
		algorithm_combo_box = new JComboBox <>(algorithms);
		main_panel.add(algorithm_combo_box);

		start_button = new JButton("Start simulation");
		start_button.addActionListener(this);
		main_panel.add(start_button);

		select_file_button = new JButton("Select test file");
		select_file_button.addActionListener(this);
		main_panel.add(select_file_button);

		add(main_panel);
		setLocationRelativeTo(null);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String button_name = e.getActionCommand();

		switch (button_name) {
			case "Start simulation" -> {
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
			case "Select test file" -> {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("./src"));
				int result = fileChooser.showOpenDialog(this);

				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					path = selectedFile.getAbsolutePath();
				}
			}
		}
	}
}
