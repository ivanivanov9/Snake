package GameOfSnake;

import java.io.FileNotFoundException;

public class File≈xception	 extends Exception{


	public File≈xception(Exception e) {
		System.out.println(e);
	}

	public File≈xception(FileNotFoundException e) {
		System.out.println(e);
	}

	
}
