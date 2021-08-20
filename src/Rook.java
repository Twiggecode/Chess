import java.util.ArrayList;


//the Rook class serves as a blueprint to create a Rook piece
//the Rook class is a subclass of the Piece abstract class


public class Rook extends Piece {

	
	
	
	protected Rook(int x, int y, String colour, String sprite) {		// constructor method to create an instance of the Rook class
		super(x, y, colour, sprite);
	}

	
	
	
	
	
	// this method returns a boolean true or false to indicate if a rook move is legal
	// sqx and sqy are the coordinates of the square the user is trying to move a rook to, and pieces is the ArrayList of all of the piece objects
	// checkflag is a flag variable to indicate if we are trying to calculate if a move is legal while in check, or whether we are calculating if a move is legal while not in check
	
	protected boolean IsLegalMove(ArrayList<Piece> pieces, int sqx, int sqy, boolean checkflag) {
		
		// if both the row and column of the rook change in the same move, this is an illegal move because the rook can only move left/right or up/down
		
		if (this.x != sqx && this.y != sqy)
		{
			return false;
		}
		
		
		
		
		for (int i=0; i<pieces.size(); i++)
		{
			
			if (pieces.get(i).x == this.x && pieces.get(i).x == sqx)	// if the rook is moving up/down, identify all pieces on the same column as the rook
			{		
				
				if ((pieces.get(i).y > sqy && pieces.get(i).y < this.y) || (pieces.get(i).y < sqy && pieces.get(i).y > this.y))	// detect if the rook jumps over another piece when moving up/down, this is an illegal move
				{
					return false;
				}
			}
			
			
			if (pieces.get(i).y == this.y && pieces.get(i).y == sqy)	// if the rook is moving left/right, identify all pieces on the same row as the rook
			{
				
				if ((pieces.get(i).x > sqx && pieces.get(i).x < this.x) || (pieces.get(i).x < sqx && pieces.get(i).x > this.x))	// detect if the rook jumps over another piece when moving left/right, this is an illegal move
				{
					return false;
				}
				
			}
			
			if (checkflag == false) 
			{
				
				if (pieces.get(i).x == sqx && pieces.get(i).y == sqy && (pieces.get(i).colour.equals(this.colour) == true))	// if the rook is attempting to move onto the same square as a piece of the same colour, this is an illegal move
				{
					return false;
				}
			
			}					
				
		}
			
		return true;	// else the move is legal, return true
	}





	
	
	// this method is only needed for a King piece, for all other pieces, return false by default
	
	protected boolean IsInCheck(ArrayList<Piece> pieces, int capturedpiece) {
		
		return false;
	}





	// this method is only needed for a King piece, for all other pieces, return false by default
	
	protected boolean IsCheckmated(ArrayList<Piece> pieces) {
		
		return false;
	}
	
	
	
	
}
	
