package GameOfSnake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener{


	static final int SCREEN_WIDTH = 1000;
	static final int SCREEN_HEIGHT = 1000;
	
	static final int CELL_SIZE = 50;
	static final int CELLS = (SCREEN_WIDTH/CELL_SIZE)*(SCREEN_HEIGHT/CELL_SIZE);
	int[] [] XY ;
	char direction = 'R';
	final int DELAY = 250;
	
	Timer timer;
	int snakeLenght;
	int score;
	int appleEaten;
	int appleX;
	int appleY;
	boolean GameOver;
	
	
	public GamePanel(){

		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));

		this.setBackground(Color.black);

		this.setFocusable(true);

		this.addKeyListener(new MyKeyAdapter());

		startGame();
	}
	
	private void startGame() {
		snakeLenght = 3;
		score = 0;
		appleEaten = 0;
		GameOver=false;
		XY = new int [CELLS] [2];
		direction = 'R';
		timer = new Timer(DELAY,this);
		timer.start();

	
		createNewApple();
	}
	
	public void createNewApple() {
		appleX = ((int)(Math.random()*(SCREEN_WIDTH/CELL_SIZE)))*CELL_SIZE;
		appleY = ((int)(Math.random()*(SCREEN_HEIGHT/CELL_SIZE)))*CELL_SIZE;
		appleEaten++;
	}
	
	
	
	public void move(){

		for(int i = snakeLenght; i>0; i--) {
			XY[i][0] = XY[i-1][0];
			XY[i][1] = XY[i-1][1];
		}
			


		switch(direction) {

		case 'U':

			XY[0][1] = XY[0][1] - CELL_SIZE;

			break;

		case 'D':

			XY[0][1] = XY[0][1] + CELL_SIZE;

			break;

		case 'L':

			XY[0][0] = XY[0][0] - CELL_SIZE;

			break;

		case 'R':

			XY[0][0] = XY[0][0] + CELL_SIZE;
			
			break;

		}

	}
	
	public void collide(){
		//body check
		for(int i = snakeLenght; i>0;i--) 
			if((XY[0][0] == XY[i][0])&& (XY[0][1] == XY[i][1])) 
				GameOver = true;

		//walls check
		if(XY[0][0] < 0 || XY[0][0] > SCREEN_WIDTH || XY[0][1] < 0 || XY[0][1] > SCREEN_HEIGHT) 
			GameOver = true;

	}
	
	
	
	public void eatApple() {
		if((XY[0][0] == appleX) && (XY[0][1] == appleY)) {

			snakeLenght++;

			if(appleEaten%5==0)
				score+= 1000;	
			else
				score+=500;
			
			
			appleEaten++;
			createNewApple();

		}

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(!GameOver) {
			
			move();
			collide();
			eatApple();
		}else {
			try {
				checkHighScore();
			} catch (FileÅxception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			timer.stop();
		}
		repaint();
	}
	
	
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		draw(g);

	}

	public void draw(Graphics g) {
		
		if(!GameOver) {
			drawApple(g);
	
			drawSnake(g);
			
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+ score, (SCREEN_WIDTH - metrics.stringWidth("Score: "+ score))/2, g.getFont().getSize());
		}else {
			
			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 75));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2 - metrics.stringWidth("O")*2);
			
			g.drawString("Press S for High Score", (SCREEN_WIDTH - metrics.stringWidth("Press S for High Score"))/2, SCREEN_HEIGHT/2);
			g.drawString("Press Enter to restart...", (SCREEN_WIDTH - metrics.stringWidth("Press Enter to restart..."))/2, SCREEN_HEIGHT/2+ metrics.stringWidth("O")*2);
			
			
		}

	}
	
	public void drawApple(Graphics g) {
		
		g.setColor(Color.red);

		g.fillOval(appleX, appleY, CELL_SIZE, CELL_SIZE);
		
		if(appleEaten%5==0) {
			g.setColor(Color.WHITE);
			g.fillOval(appleX + CELL_SIZE/3/2, appleY + CELL_SIZE/3/2, CELL_SIZE*2/3, CELL_SIZE*2/3);
			
			g.setColor(Color.red);
			g.fillOval(appleX + CELL_SIZE/3, appleY + CELL_SIZE/3, CELL_SIZE/4+2, CELL_SIZE/4+2);
		}
		
	}
	
	public void drawSnake(Graphics g) {
		

		for(int i = 0; i< snakeLenght;i++) {

			if(i == 0)
				g.setColor(Color.YELLOW);
			else 
				g.setColor(Color.green);
			
		
			g.fillRect(XY[i][0], XY[i][1], CELL_SIZE, CELL_SIZE);
			g.setColor(Color.white);
			g.drawRect(XY[i][0], XY[i][1], CELL_SIZE, CELL_SIZE);
		}

	}


	public class MyKeyAdapter extends KeyAdapter{

		@Override

		public void keyPressed(KeyEvent e) {

			switch(e.getKeyCode()) {

			case KeyEvent.VK_LEFT:

				if(direction != 'R') 
					direction = 'L';
				
				break;

			case KeyEvent.VK_RIGHT:

				if(direction != 'L') 
					direction = 'R';
				
				break;

			case KeyEvent.VK_UP:

				if(direction != 'D') 
					direction = 'U';
				
				break;

			case KeyEvent.VK_DOWN:

				if(direction != 'U') 
					direction = 'D';

				break;

			case KeyEvent.VK_ENTER:
				if(GameOver)
					startGame();

				break;	
			
			
			case KeyEvent.VK_S:
				if(GameOver)
					showHighScore();
	
				break;	
		
			}
			
		}

	}
	
	public void checkHighScore() throws FileÅxception  {
		
		
		
		
		int [] scores = new int[3];
		
		scores = readScore();
		
		
		for(int i = 0; i<3; i++) {
				if(score>=scores[i]) {
					int pass = scores[i];
					scores[i] = score;
					score = pass;
					
				}
			System.out.println(scores[i]);
		}
		try {
			 FileWriter myWriter = new FileWriter("HighScore");
			 String text = "";
			for(int i = 0; i<3;i++)
				text+=scores[i]+"\n";
				
			
			System.out.println(text);
			myWriter.write(text);
			 
		      myWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 
			 
		
	}
	
	public int[] readScore() throws FileÅxception {
		int [] scores = new int[3];
		
		
			File textfile = new File("HighScore");
			Scanner myReader;
			try {
				myReader = new Scanner(textfile);
			} catch (FileNotFoundException e) {
				throw new FileÅxception(e);
			}
			
			scores[0] =  Integer.parseInt(myReader.next());
			scores[1] = Integer.parseInt(myReader.next());
			scores[2] = Integer.parseInt(myReader.next());
			
			myReader.close();
			
		
		return scores;
	}
	
	
	public void showHighScore() {
		try {
			File textfile = new File("HighScore");
			Scanner myReader = new Scanner(textfile);
			Vector <Integer> scores = new Vector<Integer>();
			while (myReader.hasNext()) {
				scores.add( myReader.nextInt());
			}
			JOptionPane.showMessageDialog(null, "1. "+ scores.get(0) + 
					"\n2. " + scores.get(1) + 
					"\n3. " + scores.get(2) 								, "Top 3 High Score", JOptionPane.INFORMATION_MESSAGE); 
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		
	}
	
	


}
