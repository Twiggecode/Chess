import java.awt.Color;
import javax.swing.JFrame;


// the Main class contains the main method which will instantiate the chess game and add the game to the JFrame object

public class Main {

	public static void main(String[] args) {
		

		JFrame jframe = new JFrame ();	// create a JFrame object to represent the window the game is played on
		
		jframe.setBackground(Color.CYAN);
		jframe.setBounds(10,10,1920,1040);
		jframe.setVisible(true);
		jframe.setResizable(false);			// set the basic properties of the frame the game will be played in by calling these JFrame methods
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ChessGame chessgame  = new ChessGame();		// call the constructor method of the game class, instantiating the chess game	
		
		jframe.add(chessgame);	// add the chess game to the frame
		
	}

}










//	REFERENCES	//

// Pablok, John. August 10, 2017. "Chess Pieces and Board squares"  https://opengameart.org/content/chess-pieces-and-board-squares

// REFERENCES ///


// The above reference contains the images used for the chess pieces, I created my own board squares. The creator of the chess piece images has used the CC-BY-SA 3.0 license, allowing me to use these images within my project.