import java.util.ArrayList;


	//the Pawn class serves as a blueprint to create a Pawn piece
	//the Queen class is a subclass of the Piece abstract class


public class Pawn extends Piece {

	
	
	
	
	public Pawn(int x, int y, String colour, String sprite) {		// the constructor method to create a Pawn object
		super(x, y, colour, sprite);
		
	}







	// this method returns a boolean true or false to indicate if a pawn move is legal
	// sqx and sqy are the coordinates of the square the user is trying to move their pawn to, and pieces is the ArrayList of all of the piece objects
	// checkflag is a flag variable to indicate if we are trying to calculate if a move is legal while in check, or whether we are calculating if a move is legal while not in check
	
	public boolean IsLegalMove(ArrayList<Piece> pieces, int sqx, int sqy, boolean checkflag) {
		
		 boolean pieceflag=false;	// flag variable to indicate whether a piece is on a certain square
		
		
		for (int i=0; i<pieces.size(); i++)
		{
		pieceflag = false;
		
			
		
		
		if (this.colour.equals("white") == true)	// if the pawn is white, calculate the possible illegal moves
		{
			
			if (this.y <= sqy)	// if the pawn does not move forwards, illegal move
			{
				return false;
			}
			
			if (Math.abs(this.x -sqx) == 100)	   // if the pawn is trying to move diagonally
			{
				
				if (this.y - sqy > 100)	  // when capturing a piece, the pawn can only move forwards one square
				{
					return false;
				}
				
				
				if (pieces.get(i).x == sqx && pieces.get(i).y == sqy)
				{
					pieceflag = true;
				}
				
				if (pieces.get(i).x == sqx && pieces.get(i).y == sqy && pieces.get(i).colour.equals("black") == true)	// if there is a black piece on the square, capture the piece
				{
					return true;
				}
				
				if (checkflag == true)
				{
					if (pieces.get(i).x == sqx && pieces.get(i).y == sqy && pieces.get(i).colour.equals("white") == true)	// see if the white pawn can defend the piece on this square
					{
						return true;
					}
				}
				
				
				if (checkflag == false)
				{
				if (pieceflag == false && i == pieces.size()-1)	   // if there is no piece on the square, the pawn cannot move diagonally, illegal move
				{			
					return false;
				}	
				
			}
			}
			
			
			
			if (Math.abs(sqx - this.x) > 100)	// if the pawn moves more than 1 square to the left or right, illegal move
			{
				return false;
			}
			
			
			if (this.y != 710)	  // if the pawn is not on it's starting square
			{
				
				if ((this.y - sqy) > 100)	// if the pawn moves forward more than one square, illegal move
				{
					return false;
				}
				
			}
				
			else	// else the pawn is on it's starting square
			{
				
				if ((this.y - sqy) > 200)	// if the pawn moves forward more than two squares, illegal move
				{
					return false;
				}
				
				if (this.x == sqx && pieces.get(i).x == this.x && pieces.get(i).y == 610)	// if the pawn jumps over a piece when moving by 2 squares, this is an illegal move
				{
					return false;
				}
				
			}
					
					
		if (this.x == sqx)	// if the pawn is moving forwards
		{
			if (pieces.get(i).x == sqx && pieces.get(i).y == sqy)	// if there is a piece on the square, the pawn cannot move forwards
			{
				return false;
			}
		}		
			
		}
		
		
		
				
		
		
		else	// else the pawn is black, calculate the possible legal moves
		{
			
			
			if (this.y >= sqy)	// if the pawn does not move forwards, illegal move
			{
				return false;
			}
			
			if (Math.abs(this.x -sqx) == 100)	   // if the pawn is trying to move diagonally
			{
				
				if (sqy - this.y > 100)	  // when capturing a piece, the pawn can only move forwards one square
				{
					return false;
				}
				
				
				if (pieces.get(i).x == sqx && pieces.get(i).y == sqy)
				{
					pieceflag = true;
				}
				
				if (pieces.get(i).x == sqx && pieces.get(i).y == sqy && pieces.get(i).colour.equals("white") == true)	// if there is a white piece on the square, capture the piece
				{
					return true;
				}
				
				if (checkflag == true)
				{
					if (pieces.get(i).x == sqx && pieces.get(i).y == sqy && pieces.get(i).colour.equals("black") == true)	// see if the black pawn can defend the piece on this square
					{
						return true;
					}
				}
				
				if (checkflag == false)
				{		
				if (pieceflag == false && i == pieces.size()-1)	   // if there is no piece on the square, the pawn cannot move diagonally, illegal move
				{			
					return false;
				}	
				}
				
			}
			
			
			if (Math.abs(sqx - this.x) > 100)	// if the pawn moves more than 1 square to the left or right, illegal move
			{
				return false;
			}
			
			
			if (this.y != 210)	  // if the pawn is not on it's starting square
			{
				
				if ((sqy - this.y) > 100)	// if the pawn moves forward more than one square, illegal move
				{
					return false;
				}
				
			}
				
			else	// else the pawn is on it's starting square
			{
				
				if ((sqy - this.y) > 200)	// if the pawn moves forward more than two squares, illegal move
				{
					return false;
				}
				
				if (this.x == sqx && pieces.get(i).x == this.x && pieces.get(i).y == 310)	// if the pawn jumps over a piece when moving by 2 squares, this is an illegal move
				{
					return false;
				}
				
			}
					
					
		if (this.x == sqx)	// if the pawn is moving forwards
		{
			if (pieces.get(i).x == sqx && pieces.get(i).y == sqy)	// if there is a piece on the square, the pawn cannot move forwards
			{
				return false;
			}
		}	
			
									
		}
			
		if (checkflag == false) 
		{
		
			if (pieces.get(i).x == sqx && pieces.get(i).y == sqy && (pieces.get(i).colour.equals(this.colour) == true))	// if the pawn is attempting to move onto the same square as a piece of the same colour, this is an illegal move
			{
				return false;
			}
			
		}
		}
		
		
		
		return true;	// else the pawn move is legal, return true
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
