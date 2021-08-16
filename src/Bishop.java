import java.util.ArrayList;

public class Bishop extends Piece {

	//the Bishop class serves as a blueprint to create a Bishop piece
	//the Bishop class is a subclass of the Piece abstract class
	
	
	public Bishop(int x, int y, String colour, String sprite) {		// constructor method to create an instance of the Bishop class
		super(x, y, colour, sprite);
	}

	
	
	// this method returns a boolean true or false to indicate if a bishop move is legal
	// sqx and sqy are the coordinates of the square the user is trying to move a bishop to, and pieces is the ArrayList of all of the piece objects
	// checkflag is a flag variable to indicate if we are trying to calculate if a move is legal while in check, or whether we are calculating if a move is legal while not in check
	
	public boolean IsLegalMove(ArrayList<Piece> pieces, int sqx, int sqy, boolean checkflag) {
		
		
		if (Math.abs(this.x - sqx) != Math.abs(this.y - sqy))	// if the bishop did not move diagonally, this is an illegal move
		{
			return false;
		}
		
		
		for (int i=0; i<pieces.size(); i++)
		{
			
			if (checkflag == false) 
			{
			
				if (pieces.get(i).x == sqx && pieces.get(i).y == sqy && (pieces.get(i).colour.equals(this.colour) == true))	// if the bishop is attempting to move onto the same square as a piece of the same colour, this is an illegal move
				{
					return false;
				}
			
			}
			
			if ((this.x - sqx) > 0 && (this.y - sqy) < 0)	// if the bishop is travelling on the left/down diagonal (x decreasing and y increasing)
			{
				
				for (int j=100; j<(this.x -sqx); j+= 100)	   // if the bishop has jumped over any pieces on this diagonal, it's an illegal move, so check all of the squares the bishop passed through to see if the bishop jumped over any pieces
				{
									
					
					if ((pieces.get(i).x == this.x -j) && (pieces.get(i).y == this.y +j))	// if the bishop jumped over a piece, this is an illegal move, return false
					{
						return false;
					}
					
					
				}
				
			}
			
			if ((this.x - sqx) < 0 && (this.y - sqy) > 0)	// if the bishop is travelling on the up/right diagonal (x increasing and y decreasing)
			{
						
				for (int j=100; j<(this.y -sqy); j+= 100)	   // if the bishop has jumped over any pieces on this diagonal, it's an illegal move, so check all of the squares the bishop passed through to see if the bishop jumped over any pieces
				{
				
					if ((pieces.get(i).x == this.x + j) && (pieces.get(i).y == this.y - j))	// if the bishop jumped over a piece, this is an illegal move, return false
					{
						return false;
					}			
				}			
			}
		
		
			
			if ((this.x - sqx) > 0 && (this.y - sqy) > 0)	// if the bishop is travelling on the up/left diagonal (x decreasing and y decreasing)
			{
				
			
				for (int j=100; j<(this.y -sqy); j+= 100)	   // if the bishop has jumped over any pieces on this diagonal, it's an illegal move, so check all of the squares the bishop passed through to see if the bishop jumped over any pieces
				{
					
					if ((pieces.get(i).x == this.x - j) && (pieces.get(i).y == this.y - j))	// if the bishop jumped over a piece, this is an illegal move, return false
					{
						return false;
					}	
				
			    }
			
			}
			
			
			if ((this.x - sqx) < 0 && (this.y - sqy) < 0)	// if the bishop is travelling on the down/right diagonal (x increasing and y increasing)
			{
				
				for (int j=100; j<(Math.abs(this.y -sqy)); j+= 100)	   // if the bishop has jumped over any pieces on this diagonal, it's an illegal move, so check all of the squares the bishop passed through to see if the bishop jumped over any pieces
				{
					
					if ((pieces.get(i).x == this.x + j) && (pieces.get(i).y == this.y + j))	// if the bishop jumped over a piece, this is an illegal move, return false
					{
						return false;
					}	
				
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
	