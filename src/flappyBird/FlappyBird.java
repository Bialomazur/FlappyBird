package flappyBird;

import java.awt.Color;


import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird implements ActionListener, MouseListener, KeyListener{
	
	public static FlappyBird flappyBird;
	
	public final int WIDTH = 1200, HEIGHT = 800;
	
	public Renderer renderer;
	
	public Rectangle bird;
	
	public ArrayList<Rectangle> columns;
	
	public Random rand;
	
	public boolean started;

	public static boolean gameOver, played, hit, faster;	
	
	public int ticks, yMotion, score, highscore, speed = 5;
	
	public static int progress = 0,time = 20;
	
	public static Timer timer;
	
	
	
	
	
	File Clap = new File("C:\\Users\\POLAK\\eclipse-workspace\\Flappy Bird\\src\\flappyBird\\Jump.wav");
    File Lost = new File("C:\\Users\\POLAK\\eclipse-workspace\\Flappy Bird\\src\\flappyBird\\Lost.wav");
    File Lvl = new File("C:\\Users\\POLAK\\eclipse-workspace\\Flappy Bird\\src\\flappyBird\\lvlup.mp3");
	
	
	
	
	public FlappyBird() {
		
		JFrame jframe = new JFrame();
		timer = new Timer(time, this);
		renderer = new Renderer();
		rand = new Random();
		
		jframe.add(renderer);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		jframe.setTitle("Flappy Bird");
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		
		
		bird = new Rectangle(WIDTH / 2 -10, HEIGHT / 2 -10, 20, 20);
		columns = new ArrayList<Rectangle>();
		
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		
		
		
		timer.start();
		
		
	}
	
	
	public void addColumn(boolean start) {
		
		int space = 300;
		int width = 100;
		int height = 50 + rand.nextInt(300);
		
		if (start) {
		columns.add(new Rectangle(WIDTH + width +columns.size() * 300, HEIGHT - height - 120, width, height));
		columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));
	
		
		
		} else {
			
		columns.add(new Rectangle(columns.get(columns.size() -1).x + 600, HEIGHT - height - 120, width, height)); 
		columns.add(new Rectangle(columns.get(columns.size() -1).x, 0, width, HEIGHT - height - space)); 
		
		
		}
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		ticks++;
		
		if (started)
		{
		
		
		
		for (int i = 0; i < columns.size(); i++) {
			
			Rectangle column = columns.get(i);
			
			column.x -= speed;
			
		}
		
		if (ticks % 2 == 0 && yMotion < 15) {
			
			yMotion += 2;
		}
		
		
		for (int i = 0; i < columns.size(); i++) {
			
			Rectangle column = columns.get(i);
			
			if (column.x + column.width < 0) {
				
				columns.remove(column);
				if (column.y == 0) {
				addColumn(false);
			
				}	
			}
			
			column.x -= speed;
			
		}
		
	
		bird.y += yMotion;
		
		for (Rectangle column : columns)
		{
			
			if (column.y == 0 && bird.x + bird.width / 2 > column.x + column.width / 2 -10 && bird.x + bird.width / 2 < column.x + column.width / 2 + 10) {
				 
				score++;
				progress++;
				
				
		
				
			}
			
			
			if (column.intersects(bird))
			{
				gameOver = true;
				PlaySound(Lost, 0);
				bird.x = column.x - bird.width;
				hit = true;
			}
		}
		
		if (bird.y > HEIGHT - 120 || bird.y > HEIGHT - bird.height - 120 || bird.y < 0)
		{
			    
			bird.y = HEIGHT - 120 - bird.height;
			gameOver = true;
			if (!played && !hit) {
				PlaySound(Lost, 0);
				played = true;
			}
			}
			
			
		}
		
		if (bird.y + yMotion >= HEIGHT - 120) {
			bird.y = HEIGHT - 120 - bird.height;
			
		}
		
		
		renderer.repaint();
		
	}
	
	

	
	
	
	public void paintColumn(Graphics g, Rectangle column) {
		
		g.setColor(Color.green.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
	}
	
	public void jump()
	{
		if (gameOver)
		{
			bird = new Rectangle(WIDTH / 2 -10, HEIGHT / 2 -10, 20, 20);
			columns.clear();
			yMotion = 0;
			score = 0;
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			
			gameOver = false;
			
			hit = false;
			played = false;
			progress = 0;


			
			
			
		}
		
		if (!started)
		{
			started = true;
		}
		
		else if (!gameOver) {
			
			if (yMotion > 0) {
				
				yMotion = 0;
				PlaySound(Clap, 0);
			
			}
			
			yMotion -= 10;
			
		}
	}
	
	
	public static void main(String[] args) {
		
		flappyBird = new FlappyBird();
		played = false;
		hit = false;
		faster = false;
		
	}
		
	static void PlaySound(File Sound, int t) {
		
		try {
		
		Clip clip = AudioSystem.getClip();
		clip.open(AudioSystem.getAudioInputStream(Sound));
		clip.start();
		Thread.sleep(t);
		//clip.stop();
	
		} catch(Exception e) {
			
			System.out.println(e);
		}
		
		
	}


	public void repaint(Graphics g) {
		
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.orange);
		g.fillRect(0, HEIGHT - 120, WIDTH, 150);
		
		g.setColor(Color.green);
		g.fillRect(0, HEIGHT - 120, WIDTH, 20);
		
		g.setColor(Color.red);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
		
		for (Rectangle column : columns) {
			
			paintColumn(g, column);
		}
		
		g.setColor(Color.BLUE);
		g.setFont(new Font("Arial", 1, 100));
		

		
		
		if (gameOver)
		{   
			

			if (score > highscore) {
				
				highscore = score;
			}
			

			
			g.drawString("Game Over!", 320, HEIGHT / 2 - 50);
			
			g.setColor(Color.YELLOW);
			g.setFont(new Font("Arial", 1, 35));
			
			g.drawString("Highscore : " + String.valueOf(highscore), 500, HEIGHT / 2 + 50);
			speed = 0;
			
			
			
			
		}
		if (!started)
		{
			g.drawString("Press Space to start!", 90, HEIGHT / 2 - 50);
		}
		
		else if (!gameOver) {
			
			speed = 5;
			
			
		}
		
		if (!gameOver && started)
		{
			g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
			
			
		}
		
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		
		jump();
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		

		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			
			jump();
		}
		// TODO Auto-generated method stub
		
	}

}
