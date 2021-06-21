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
}
