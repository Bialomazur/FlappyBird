package flappyBird;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Renderer extends JPanel {

	private static final long serialVersionUID = 8095379131479840912L;
	
	private BufferedImage image;
	
	public Renderer() {
		
		try {
			image = ImageIO.read(new File("C:\\Users\\POLAK\\eclipse-workspace\\Flappy Bird\\src\\flappy.png"));
		
		} catch(IOException ex) {
			
			System.out.println("Error while loading the image!");
			
		}
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	
		super.paintComponent(g);
		FlappyBird.flappyBird.repaint(g);
	}
	
	

}
