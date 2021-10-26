import java.util.ArrayList;


	// this class contains the entire functionality of the King piece, including legal moves, check and checkmate
	// the King class is used to instantiate a King object and the King class is a subclass of the Piece abstract class

public class King extends Piece {

	
	
	
	protected King(int x, int y, String colour, String sprite) {		// the constructor method to instantiate a King object
		super(x, y, colour, sprite);
		
	}

	
	
	
	
	/* 
	 method to determine if a king is in check or not by analysing the positions of all pieces on the board
	 pieces is the ArrayList containing all of the Piece objects on the board	 
	 capturedpiece is the index within the pieces ArrayList of a piece that is about to be captured, so that we can ignore if the piece that is going to be captured puts a king in check or not
	 */
	
	protected boolean IsInCheck(ArrayList<Piece> pieces, int capturedpiece)
	{
		
			
		for (int i=0; i<pieces.size(); i++)
		{	
			
			if (i != capturedpiece)	   // if a piece is going to be captured, ignore whether this piece puts your king in check or not, because this piece is about to be removed from the board
			{
			
			
				if (pieces.get(i).IsLegalMove(pieces, this.x, this.y, true) == true && pieces.get(i).getClass().getSimpleName().equals("Pawn") == false && pieces.get(i).colour.equals(this.colour) == false)	// if the opponent piece is not a pawn, if it is a legal move to move the piece onto the same square as your king, your king is in check
					{
						return true;
					}
				
								
			
			if (pieces.get(i).getClass().getSimpleName().equals("Pawn") == true && pieces.get(i).colour.equals(this.colour) == false)	// detect an opposite coloured pawn
			{
				if (pieces.get(i).colour.equals("white") == true)	// if the pawn is white, see if the white pawn has put the black king in check
				{
					if (Math.abs(pieces.get(i).x - this.x) == 100 &&  (pieces.get(i).y - this.y) == 100)	// if the king is on front of and diagonal to the pawn, the king is in check
					{
						return true;
					}
				}
				
				else	// else the pawn is black, see if the black pawn has put the white king in check
				{
					if (Math.abs(pieces.get(i).x - this.x) == 100 &&  (this.y - pieces.get(i).y) == 100)	// if the king is on front of and diagonal to the pawn, the king is in check
					{
						return true;
					}
				}
				
			}
			
			if (pieces.get(i).getClass().getSimpleName().equals("King") == true && pieces.get(i).colour.equals(this.colour) == false)	// detect opposite coloured king
			{
				if (Math.abs(this.x - pieces.get(i).x) <= 100 && Math.abs(this.y - pieces.get(i).y) <= 100)		// prevent two kings from moving within 1 square of each other, this puts both kings in check and is an illegal move
				{
					return true;
				}
			}
			 
			
			
			}
		}
			
		
		return false;	// else the king is not in check, return false
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
		/* 
		   method to detect if either player has checkmated the other
	
		   this function firstly sees if the king is in check, the king cannot be in checkmate if it is not in check
		   this function then identifies all the squares around the king that the king can potentially move to (so empty squares or squares with enemy pieces)
		   it then checks to see if any enemy pieces can move to these squares (to see if the king has any 'safe' squares to move to)
		   if the king has at least 1 safe square to move to, it's not checkmate, return false
		
		   it then sees if it is possible to get out of check by capturing an enemy piece, if so, it's not checkmate
		
		   then it sees if it is possible to get out of check by blocking with a piece, if so, it's not checkmate
		
		   if the king is in check, has no safe squares to move to, and there is no way to get out of check by capturing or blocking a piece, it's checkmate, return true	   
		   */
		
	
	
	protected boolean IsCheckmated(ArrayList<Piece> pieces)
	{
		
		boolean validmove = false;
		
		if (this.IsInCheck(pieces, -1) == true)	// see if the king is in check
		{
			
			// identify the index within the pieces arraylist of the piece that is putting the king in check
			
			int checkindex = this.GetIndex(pieces);
			
			
			// now we need to see if the king has any safe squares it can move to in order to get out of check
			
			
			// iterate over the 3 columns surrounding the king
			for (int i=(this.x)-100; i<(this.x)+200; i+=100)
			{
				
				// iterate over the 3 rows surrounding the king
				for (int j=(this.y)-100; j<(this.y)+200; j+=100)
				{
					
					 validmove = false;
					
					// check to see if the square is valid (the coordinate is not outside of the grid), and also if it is a legal move to move your king onto this square
					if ((i >= 550 && i <= 1250) && (j >= 110 && j <= 810) && (this.IsLegalMove(pieces, i, j, false) == true))
					{			
																
					
						// iterate over all of the pieces stored in the pieces array
						for (int k=0; k<pieces.size(); k++)
						{
							
							// detect the opponent's pieces (not including pawns)
							if (this.colour.equals(pieces.get(k).colour) == false && pieces.get(k).getClass().getSimpleName().equals("Pawn") == false)
							{
								
								// if the opponent's piece can move to this square
								if (pieces.get(k).IsLegalMove(pieces, i, j, true) == true)
								{
									validmove = true;
								}														
								
							}
							
							
							// detect the opponent's pawns
							if (this.colour.equals(pieces.get(k).colour) == false && pieces.get(k).getClass().getSimpleName().equals("Pawn") == true)
							{
								
									// if the opponent's pawn threatens this square
									if (pieces.get(k).IsLegalMove(pieces, i, j, true) == true && pieces.get(k).x != i)
									{
										validmove = true;
									}
																
							}
																			
						}
						
						
						if (validmove == false)	// if none of the opponent's pieces can move to a square around the king, this is a safe square for the king to move to, therefore the king is not in checkmate, return false
						{
							return false;
						}
						
						
						validmove = false;
					
					}
				}
				
			}
			
			
			if (this.DoubleCheck(pieces) == false)
			{
			
			for (int n=0; n<pieces.size(); n++)
			{
				validmove = true;
				
				// see if any of your pieces can capture the piece putting your king in check, if so, the king is not in checkmate, return false
				
				if (pieces.get(n).colour.equals(this.colour) == true && pieces.get(n).getClass().getSimpleName().equals("King") == false && pieces.get(n).IsLegalMove(pieces, pieces.get(checkindex).x, pieces.get(checkindex).y , false) == true)
				{
					return false;
				}
				
				
				
				// if the piece putting the king in check is directly to the left of the king (it must be a rook or queen)
				
				if (pieces.get(checkindex).x < this.x && pieces.get(checkindex).y == this.y)
				{
					
					// iterate over the squares between the king and the piece putting the king in check
					
					for (int h=(pieces.get(checkindex).x + 100); h<this.x; h+=100)
					{
						
						// see if is possible to move one of your pieces to one of these squares, blocking the check
						if (pieces.get(n).colour.equals(this.colour) == true && pieces.get(n).IsLegalMove(pieces, h, this.y, false) == true && (pieces.get(n).getClass().getSimpleName().equals("King") == false))
						{
							return false;	// if it is possible to block the check, return false as this is not checkmate
						}
						
					}
					
				}
				
				
				// if the piece putting the king in check is directly to the right of the king (it must be a rook or queen)
				
				if (pieces.get(checkindex).x > this.x && pieces.get(checkindex).y == this.y)
				{
					
					// iterate over the squares between the king and the piece putting the king in check
					
					for (int h=(pieces.get(checkindex).x - 100); h>this.x; h-=100)
					{
						
						// see if is possible to move one of your pieces to one of these squares, blocking the check
						if (pieces.get(n).colour.equals(this.colour) == true && pieces.get(n).IsLegalMove(pieces, h, this.y, false) == true && (pieces.get(n).getClass().getSimpleName().equals("King") == false))
						{
							return false;	// if it is possible to block the check, return false as this is not checkmate
						}
						
					}
					
				}
				
				
				
				// if the piece putting the king in check is directly above the king (it must be a rook or queen)
				
				if (pieces.get(checkindex).x == this.x && pieces.get(checkindex).y < this.y)
				{
					
					// iterate over the squares between the king and the piece putting the king in check
					
					for (int h=(pieces.get(checkindex).y + 100); h<this.y; h+=100)
					{
						
						// see if is possible to move one of your pieces to one of these squares, blocking the check
						if (pieces.get(n).colour.equals(this.colour) == true && pieces.get(n).IsLegalMove(pieces, this.x, h, false) == true && (pieces.get(n).getClass().getSimpleName().equals("King") == false))
						{
							return false;	// if it is possible to block the check, return false as this is not checkmate
						}
						
					}
					
				}
				
				
				
				// if the piece putting the king in check is directly below the king (it must be a rook or queen)
				
				if (pieces.get(checkindex).x == this.x && pieces.get(checkindex).y > this.y)
				{
					
					// iterate over the squares between the king and the piece putting the king in check
					
					for (int h=(pieces.get(checkindex).y - 100); h>this.y; h-=100)
					{
						
						// see if is possible to move one of your pieces to one of these squares, blocking the check
						if (pieces.get(n).colour.equals(this.colour) == true && pieces.get(n).IsLegalMove(pieces, this.x, h, false) == true && (pieces.get(n).getClass().getSimpleName().equals("King") == false))
						{
							return false;	// if it is possible to block the check, return false as this is not checkmate
						}
						
					}
					
				}
				
				
				
				// if the piece putting the king in check is on the diagonal to the top right of the king (it must be a bishop or queen)
				
				if (pieces.get(checkindex).x - this.x == this.y - pieces.get(checkindex).y)
				{
					
					// iterate over the squares between the king and the piece putting the king in check
					
					for (int h=(pieces.get(checkindex).y + 100), b=pieces.get(checkindex).x - 100; h<this.y; h+=100, b-=100)
					{
						
						// see if is possible to move one of your pieces to one of these squares, blocking the check
						if (pieces.get(n).colour.equals(this.colour) == true && pieces.get(n).IsLegalMove(pieces, b, h, false) == true && (pieces.get(n).getClass().getSimpleName().equals("King") == false))
						{
							return false;	// if it is possible to block the check, return false as this is not checkmate
						}
						
					}
					
				}
				
				
				
				// if the piece putting the king in check is on the diagonal to the bottom right of the king (it must be a bishop or queen)
				
				if (pieces.get(checkindex).x - this.x == pieces.get(checkindex).y - this.y)
				{
					
					// iterate over the squares between the king and the piece putting the king in check
					
					for (int h=(pieces.get(checkindex).y - 100), b=pieces.get(checkindex).x - 100; h>this.y; h-=100, b-=100)
					{
						
						// see if is possible to move one of your pieces to one of these squares, blocking the check
						if (pieces.get(n).colour.equals(this.colour) == true && pieces.get(n).IsLegalMove(pieces, b, h, false) == true && (pieces.get(n).getClass().getSimpleName().equals("King") == false))
						{
							return false;	// if it is possible to block the check, return false as this is not checkmate
						}
						
					}
					
				}
				
				
				
				// if the piece putting the king in check is on the diagonal to the top left of the king (it must be a bishop or queen)
				
				if (this.x - pieces.get(checkindex).x == this.y - pieces.get(checkindex).y)
				{
					
					// iterate over the squares between the king and the piece putting the king in check
					
					for (int h=(pieces.get(checkindex).y + 100), b=pieces.get(checkindex).x + 100; h<this.y; h+=100, b+=100)
					{
						
						// see if is possible to move one of your pieces to one of these squares, blocking the check
						if (pieces.get(n).colour.equals(this.colour) == true && pieces.get(n).IsLegalMove(pieces, b, h, false) == true && (pieces.get(n).getClass().getSimpleName().equals("King") == false))
						{
							return false;	// if it is possible to block the check, return false as this is not checkmate
						}
						
					}
					
				}
				
				
				
				// if the piece putting the king in check is on the diagonal to the bottom left of the king (it must be a bishop or queen)
				
				if (this.x - pieces.get(checkindex).x == pieces.get(checkindex).y - this.y)
				{
					
					// iterate over the squares between the king and the piece putting the king in check
					
					for (int h=(pieces.get(checkindex).y - 100), b=pieces.get(checkindex).x + 100; h>this.y; h-=100, b+=100)
					{
						
						// see if is possible to move one of your pieces to one of these squares, blocking the check
						if (pieces.get(n).colour.equals(this.colour) == true && pieces.get(n).IsLegalMove(pieces, b, h, false) == true && (pieces.get(n).getClass().getSimpleName().equals("King") == false))
						{
							return false;	// if it is possible to block the check, return false as this is not checkmate
						}
						
					}
					
				}
				
					
				
			}
			
			
			for (int c=0; c<pieces.size(); c++)
			{
				
				// if the king can move to the same square as the piece checking the king and an enemy piece (not including pawns) can move onto the same square as the piece checking the king
				
				if (this.IsLegalMove(pieces, pieces.get(checkindex).x, pieces.get(checkindex).y, false) == true && pieces.get(c).colour.equals(this.colour) == false && pieces.get(c).IsLegalMove(pieces, pieces.get(checkindex).x, pieces.get(checkindex).y, true) == true && pieces.get(c).getClass().getSimpleName().equals("Pawn") == false && c != checkindex)
				{
					validmove = false;
				}		
				
				// if the king can move to the same square as the piece checking the king and an enemy pawn defends the square that contains the piece checking the king
				
				if (this.IsLegalMove(pieces, pieces.get(checkindex).x, pieces.get(checkindex).y, false) == true && pieces.get(c).colour.equals(this.colour) == false && pieces.get(c).IsLegalMove(pieces, pieces.get(checkindex).x, pieces.get(checkindex).y, true) == true && pieces.get(c).getClass().getSimpleName().equals("Pawn") == true && pieces.get(c).x != pieces.get(checkindex).x && c != checkindex)
				{
					validmove = false;
				}	
				
				// if the king can't move onto the same square as the piece putting the king in check
				
				if (this.IsLegalMove(pieces, pieces.get(checkindex).x, pieces.get(checkindex).y, false) == false)
				{
					validmove = false;
				}
				
				
			
			}
			
			if (validmove == true)
			{
				return false;	// if the king can capture the piece putting it in check, it's not checkmate, return false
			}
			
		}
				
	}	
		
		else   // else the king is not in check, therefore the king cannot be in checkmate, return false
		{
			return false;
		}
		
		
		
		
		return true;  // else the king must be in checkmate, return true
	}
	
	
	
	
	
	
	
	
	
	
	
	
	// this method detects if two pieces are putting a king in check, which is a double check and forces the king to move
	
	protected boolean DoubleCheck (ArrayList<Piece> pieces)
	{
		
		int checkcounter = 0; // counts how many pieces have put the king in check, if the counter reaches 2 then this is a double check 
		
		for (int i=0; i<pieces.size(); i++)
		{	
							
			
				if (pieces.get(i).IsLegalMove(pieces, this.x, this.y, true) == true && pieces.get(i).getClass().getSimpleName().equals("Pawn") == false && pieces.get(i).colour.equals(this.colour) == false)	// if the opponent piece is not a pawn, if it is a legal move to move the piece onto the same square as your king, your king is in check
					{
						checkcounter++;
					}
				
								
			
			if (pieces.get(i).getClass().getSimpleName().equals("Pawn") == true && pieces.get(i).colour.equals(this.colour) == false)	// detect an opposite coloured pawn
			{
				if (pieces.get(i).colour.equals("white") == true)	// if the pawn is white, see if the white pawn has put the black king in check
				{
					if (Math.abs(pieces.get(i).x - this.x) == 100 &&  (pieces.get(i).y - this.y) == 100)	// if the king is on front of and diagonal to the pawn, the king is in check
					{
						checkcounter++;
					}
				}
				
				else	// else the pawn is black, see if the black pawn has put the white king in check
				{
					if (Math.abs(pieces.get(i).x - this.x) == 100 &&  (this.y - pieces.get(i).y) == 100)	// if the king is on front of and diagonal to the pawn, the king is in check
					{
						checkcounter++;
					}
				}
				
			}
			
			if (pieces.get(i).getClass().getSimpleName().equals("King") == true && pieces.get(i).colour.equals(this.colour) == false)	// detect opposite coloured king
			{
				if (Math.abs(this.x - pieces.get(i).x) <= 100 && Math.abs(this.y - pieces.get(i).y) <= 100)		// prevent two kings from moving within 1 square of each other, this puts both kings in check and is an illegal move
				{
					checkcounter++;
				}
			}
			 				
			
		}
			
				
		
		if (checkcounter == 2)	// if two pieces are putting the king in check, this is a double check, return true
		{
			checkcounter = 0;
			return true;
		}
		
		
		else   // else it is not a double check, return false
		{
			checkcounter = 0;
			return false;
		}
		
		
		
	}
	
	
	
	
	
	    // this method will return the index of a piece that is putting the king in check
		protected int GetIndex(ArrayList<Piece> pieces)
		{
			
			for (int i=0; i<pieces.size(); i++)
			{
				
				if (pieces.get(i).IsLegalMove(pieces, this.x, this.y, true) == true && pieces.get(i).getClass().getSimpleName().equals("Pawn") == false && pieces.get(i).colour.equals(this.colour) == false)	// if the opponent piece is not a pawn, if it is a legal move to move the piece onto the same square as your king, your king is in check
				{
					return i;
				}
				
				
				if (pieces.get(i).getClass().getSimpleName().equals("Pawn") == true && pieces.get(i).colour.equals(this.colour) == false)	// detect an opposite coloured pawn
				{
					if (pieces.get(i).colour.equals("white") == true)	// if the pawn is white, see if the white pawn has put the black king in check
					{
						if (Math.abs(pieces.get(i).x - this.x) == 100 &&  (pieces.get(i).y - this.y) == 100)	// if the king is on front of and diagonal to the pawn, the king is in check
						{
							return i;
						}
					}
					
					else	// else the pawn is black, see if the black pawn has put the white king in check
					{
						if (Math.abs(pieces.get(i).x - this.x) == 100 &&  (this.y - pieces.get(i).y) == 100)	// if the king is on front of and diagonal to the pawn, the king is in check
						{
							return i;
						}
					}
					
				}
							
			}	
			
			return -1;	// return -1 if the king is not in check
		}
	
	
	
	
	
	
	
	
	
	// this method returns a boolean true or false to indicate if a king move is legal
	// sqx and sqy are the coordinates of the square the user is trying to move their king to, and pieces is the ArrayList of all of the piece objects
	// checkflag is a flag variable to indicate if we are trying to calculate if a move is legal while in check, or whether we are calculating if a move is legal while not in check
	
	


	protected boolean IsLegalMove(ArrayList<Piece> pieces, int sqx, int sqy, boolean checkflag) {
		
		
		if ((Math.abs(this.x - sqx) > 100) || (Math.abs(this.y - sqy) > 100) )	// if the king moves more than one square, this is an illegal move
		
		{
			return false;
		}
		
		
		for (int i=0; i<pieces.size(); i++)
		{
			
			if (checkflag == false) 
			{
			
				if (pieces.get(i).x == sqx && pieces.get(i).y == sqy && (pieces.get(i).colour.equals(this.colour) == true))	// if the king is attempting to move onto the same square as a piece of the same colour, this is an illegal move
				{
					return false;
				}
			
			}
		}
	
		
		return true;	// else the king move is legal, return true
	}
	
	
}
