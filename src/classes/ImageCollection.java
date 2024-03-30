package src.classes;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.HashMap;
import java.util.Map;


public class ImageCollection {
	private Map <String, ImageIcon> images = new HashMap <String, ImageIcon>() {
		{
			put("empty", new ImageIcon("./src/assets/empty.png"));
			put("obstacle", new ImageIcon("./src/assets/obstacle.png"));
			put("Mandalorian", new ImageIcon("./src/assets/Mandalorian.png"));
			put("Stormtropper", new ImageIcon("./src/assets/Stromtropper.png"));
			put("ship", new ImageIcon("./src/assets/ship.png"));
			put("Grogu", new ImageIcon("./src/assets/Grogu.png"));
		}
	};


	public ImageCollection(int with, int height) {
		for (Map.Entry <String, ImageIcon> entry : images.entrySet()) {
			String key = entry.getKey();
			ImageIcon value = entry.getValue();

			Image scaled_icon = value.getImage();
			scaled_icon = scaled_icon.getScaledInstance(with, height, Image.SCALE_SMOOTH);

			images.put(key, new ImageIcon(scaled_icon));
		}
	}


	/**
	 * Returns the image icon associated with the given name.
	 *
	 * @param name The name of the image icon to retrieve.
	 * @return The image icon associated with the given name, or null if not found.
	 */
	public ImageIcon get_image_icon(String name) {
		return images.get(name);
	}
}
