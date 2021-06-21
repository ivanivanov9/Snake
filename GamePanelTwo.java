package GameOfSnake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import GameOfSnake.GamePanel.MyKeyAdapter;

public abstract class GamePanelTwo extends JPanel implements ActionListener{


	static final int SCREEN_WIDTH = 1000;
	static final int SCREEN_HEIGHT = 500;
	
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
	
	public GamePanelTwo(){

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
			
				break;	
		
			}
			
		}

	}
	
}