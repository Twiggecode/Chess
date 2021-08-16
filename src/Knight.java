import java.util.ArrayList;


// the Knight class serves as a blueprint to create a Knight piece
// the Knight class is a subclass of the Piece abstract class


public class Knight extends Piece {

	
	
	
	public Knight(int x, int y, String colour, String sprite) {		// constructor method to create an instance of the Knight class
		super(x, y, colour, sprite);
	}

	
	
	
	// this method returns a boolean true or false to indicate if a knight move is legal
	// sqx and sqy are the coordinates of the square the user is trying to move a knight to, and pieces is the ArrayList of all of the piece objects
	// checkflag is a flag variable to indicate if we are trying to calculate if a move is legal while in check, or whether we are calculating if a move is legal while not in check
	
	public boolean IsLegalMove(ArrayList<Piece> pieces, int sqx, int sqy, boolean checkflag) {		
		
		
		
		for (int i=0; i<pieces.size(); i++)
		{
			if (checkflag == false) 
			{
				
				if (pieces.get(i).x == sqx && pieces.get(i).y == sqy && (pieces.get(i).colour.equals(this.colour) == true))	// if the knight is attempting to move onto the same square as a piece of the same colour, this is an illegal move
				{
					return false;
				}
			
			}
		}
		
		
		// if the row the knight is on changes by 2, the column must change by 1 so that the knight moves in an L shape
		if (Math.abs(sqy-this.y) == 200)
		{
			if (Math.abs(sqx-this.x) == 100)
			{
				return true;
			}
		}
		
		// if the column the knight is on changes by 2, the row must change by 1 so that the knight moves in an L shape
		if (Math.abs(sqx-this.x) == 200)
		{
			if (Math.abs(sqy-this.y) == 100)
			{
				return true;
			}
		}
		
			
		
		return false;	// else return false, a legal move was not detected and this is therefore an illegal move
	}




	// this method is only needed for a King piece, for all other pieces, return false by default
	
	protected boolean IsInCheck(ArrayList<Piece> pieces, int capturedpiece) {
		
		return false;
	}




	// this method is only needed for a King piece, for all other pieces, return false by default
	
	protected boolean IsCheckmated(ArrayList<Piece> pieces) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
}
	