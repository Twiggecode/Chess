import java.util.ArrayList;


// the Piece class is an abstract class that serves as a blueprint for a general chess piece
// all 6 types of chess piece objects are constructed from their own individual classes, and these classes are all subclasses of the Piece class

public abstract class Piece {

	
	int x;
	int y;		// properties to define the coordinates, colour and image sprite of a piece
	String colour;
	String sprite;
	
	
	
	public Piece(int x, int y, String colour, String sprite) {		// general constructor method of a piece, each piece must contain an x and y coordinate, a colour (white or black) and an image sprite to be painted onto the board
		super();
		this.x = x;
		this.y = y;
		this.colour = colour;
		this.sprite = sprite;
	}
	
	
	
	
	
	
public abstract boolean IsLegalMove(ArrayList<Piece> pieces, int sqx, int sqy, boolean checkflag);		// abstract method that every piece subclass will implement to check if a move of that specific type of piece is legal, in order to prevent the user from playing illegal moves






protected abstract boolean IsInCheck(ArrayList<Piece> pieces, int capturedpiece);	// a method that the King class will use to detect if a king has been placed into check





protected abstract boolean IsCheckmated(ArrayList<Piece> pieces);	// a method that the King class will use to detect if a king has been placed into checkmate, ending the game








	
	
	
}
