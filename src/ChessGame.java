import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;



   /*
   an instance of this class represents an instance of a game of chess
   this class contains the general functionality of my chess game, allowing the user to click on a piece to select it, and click on a square to move the piece to the square
   my program will prevent the user from making illegal moves
   the game will end in either a win for white or a win for black if either player can checkmate the other
   the game will end in a draw if there is not a sufficient amount of pieces held by both players to checkmate the other (what's called a draw by insufficient material) 
   */



@SuppressWarnings("serial")
public class ChessGame extends JPanel implements MouseListener {	// this class implements the MouseListener interface to detect mouse button presses

	
	private int sqx=0;	// the coordinates of a square clicked on by the user
	private int sqy=0;
	
	private boolean start = false;	 // boolean variable to indicate whether the game has started or not
	
	private boolean menu = true;	// boolean variable to indicate whether the instruction menu is to be shown or not
		
	private boolean turn=false;	  // this variable indicates who's move it is. false means it's white's move and true means it's black's move.
		
	private boolean validsquare = false;	// boolean variable to indicate whether the user has clicked on one of their own pieces when trying to move a piece
	
	private Boolean win = null;	  // boolean variable set to true if white has won the game, set to false if black has won the game, and set to null while the game is still ongoing
	
	private boolean draw = false; // variable set to true if the game has resulted in a draw
	
	private int selectedpiece;	   // temp storage variable that stores the index of a particular piece within the pieces ArrayList
		
	private ArrayList<Piece> pieces = new ArrayList<Piece>();		// ArrayList to store all of the piece objects to represent the chess pieces
	
			
	
	
	
	
	protected ChessGame ()	// the constructor of the ChessGame class, this method is called in Main.java to instantiate the chess game
	{
		addMouseListener(this);	// add the mouse listener to detect mouse clicks
		setFocusable(true);		
		setFocusTraversalKeysEnabled(false);
	}

	
	
	
	
	
	
	public void mousePressed(MouseEvent e) {	// this method runs if a mouse press is detected
			
		menu = false;	// after the user presses the mouse button, the instruction menu is removed and the game starts
		
		if (menu == false)
		{
		
		
		int mousex;		// stores the x and y coordinates of a mouse click
	    int mousey;
			
	    int tempx=0;	// stores the coordinates of the square a piece is on before a move is made
		int tempy=0;
	    
		int capturedpiece;   // stores the index of a piece you are trying to capture
	    
		
		repaint();	// repaint the frame every time the mouse button is pressed
		
		mousex = e.getX();	  // get the coordinates of the location of the mouse press
		mousey = e.getY();
		
		
		

		
	
		// identify the x coordinate of the square clicked on by the user
		
		for (int i=0; i<8; i++)
		{
			if (mousex > (530+100*i) && mousex <= (630+100*i))
			{
				sqx = 550+100*i;
			}
			
		}
	

		// identify the y coordinate of the square clicked on by the user

		if (sqx != 0)
		{
		
		for (int i=0; i<8; i++)
		{
			if (mousey > (90+100*i) && mousey <= (190+100*i))
			{
				sqy = 110+100*i;
			}
	
		}
		}
		
		
		
			if (validsquare == false)	  // if this is the first square the user is selecting
			{
		
		
		
		if (sqx != 0 && sqy != 0)	// if the user clicked on a square on the board, identify what piece is on that square, if any
		{
			for (int i=0; i<pieces.size(); i++)
			{
				if (turn == false)	// if it's white's move
				{
					if (pieces.get(i).x == sqx && pieces.get(i).y == sqy && pieces.get(i).colour.equals("white") == true)	// if the user clicked on one of their pieces, indicate this by setting validsquare equal to true
					{
						validsquare = true;
						selectedpiece = i;   // store the index of the piece selected by the user
						break;
					}
					
					else	// else the user did not click on one of their pieces
					{
						validsquare = false;
					}
				}
				
				else	// else it's black's move
				{
					
					if (pieces.get(i).x == sqx && pieces.get(i).y == sqy && pieces.get(i).colour.equals("black") == true)	// if the user clicked on one of their pieces, indicate this by setting validsquare equal to true
					{
						validsquare = true;
						selectedpiece = i;	  // store the index of the piece selected by the user	
						break;
					}
					
					else	// else the user did not click on one of their pieces
					{
						validsquare = false;
					}
					
				}
			}
		}
		
		
		}
			
			
			else	// else we are checking the square the user is attempting to move a piece too
			{
				if (sqx != 0 && sqy != 0)	// check to see if the user clicked on a square on the board or clicked on some point off of the board
					
				{
					
									
					
					// prevent the user from 'moving' a piece onto the square it is already on, and prevent the user from making illegal moves
					if ((sqx != pieces.get(selectedpiece).x || sqy != pieces.get(selectedpiece).y) && pieces.get(selectedpiece).IsLegalMove(pieces, sqx, sqy, false) == true)	
					{
						
						tempx = pieces.get(selectedpiece).x;	// store the coordinates of the piece before it moves
						tempy = pieces.get(selectedpiece).y;
						
						pieces.get(selectedpiece).x = sqx;	  // update the coordinates of the piece if the move is legal
						pieces.get(selectedpiece).y = sqy;
						
						capturedpiece = -1;
						
						for (int k=0; k<pieces.size(); k++)
						{
							// if the user has moved a piece onto the same square as an opponent's piece, store the arraylist index of the piece you are trying to capture
							if (pieces.get(k).x == pieces.get(selectedpiece).x && pieces.get(k).y == pieces.get(selectedpiece).y && (pieces.get(k).colour.equals(pieces.get(selectedpiece).colour) == false))
							{
								capturedpiece = k;
							}
																														
						}
						
																	
						
						for (int i=0; i<pieces.size(); i++)	  // identify which piece is your king and see if the move you made has put your king in check
						{
							if ((pieces.get(i).getClass().getSimpleName().equals("King") == true) && (pieces.get(i).colour.equals(pieces.get(selectedpiece).colour) == true))
									{
																								
										
										if (pieces.get(i).IsInCheck(pieces, capturedpiece) == true)	// if the user put their king in check, the user has made an illegal move, reverse the move 
										{
											pieces.get(selectedpiece).y = tempy;
											pieces.get(selectedpiece).x = tempx;
											capturedpiece = -1;																																						
											break;
										}
										
										else	// else the king is not in check, the move is valid and it's now the opponent's turn
										{
											turn = !turn;
											capturedpiece = -1;																																												
											break;
										}
																			
										
									
									}
						}
						
									
						validsquare = false;
						
											
										
						// if the selected piece is a pawn, we need to check if has reached the back rank and needs to be promoted						
						if((pieces.get(selectedpiece).getClass().getSimpleName().equals("Pawn") == true))
						{
													
							if (pieces.get(selectedpiece).y == 110) // promote the pawn to a white queen
							{																	
								pieces.set(selectedpiece, new Queen(pieces.get(selectedpiece).x, pieces.get(selectedpiece).y, "white", "images/w_queen.png"));							
							}
							
							
							if (pieces.get(selectedpiece).y == 810) // promote the pawn to a black queen
							{
								pieces.set(selectedpiece, new Queen(pieces.get(selectedpiece).x, pieces.get(selectedpiece).y, "black", "images/b_queen.png"));
							}
						}
						
								
						
						
						for (int j=0; j<pieces.size(); j++)
						{
							
							// if the user has moved a piece onto the same square as an opponent's piece, capture the piece and remove it from the game
							if (pieces.get(j).x == pieces.get(selectedpiece).x && pieces.get(j).y == pieces.get(selectedpiece).y && (pieces.get(j).colour.equals(pieces.get(selectedpiece).colour) == false))
							{
								pieces.remove(j);
								j--;
							}
																														
						}
												
						
					}
					
					else	// else the user has tried to make an illegal move or tried to move a piece onto the square it is already on
					{
						validsquare = false;
					}
					
				}
				
				else	// else the user clicked on a point that is not on the board
				{
					validsquare = false;
				}
			}
			
		
		
			for (int i=0; i<pieces.size(); i++)	// see if the black king has been checkmated
			{
				if (pieces.get(i).getClass().getSimpleName().equals("King") == true && pieces.get(i).colour.equals("black") == true)
				{
					
					
					if (pieces.get(i).IsCheckmated(pieces) == true)
					{
						win = true;		// set win to true to indicate that white has won the game
					}
					
					
				}
				
				if (pieces.get(i).getClass().getSimpleName().equals("King") == true && pieces.get(i).colour.equals("white") == true)	// see if the white king has been checkmated
				{
					
					
					if (pieces.get(i).IsCheckmated(pieces) == true)
					{
						win = false;		// set win to false to indicate that black has won the game
					}
				}
				
			}
		}						
	}
	
	
	
	
	
	
	
	
	// this graphics method is responsible for painting the chess game onto the frame
	
	public void paint(Graphics g)
	{
		
		Font font = new Font("Verdana", Font.BOLD, 40);  // create the main text font that will be used	
		
		Font font1 = new Font("Verdana", Font.BOLD, 60);  // create the larger text font that will be used in the instructions menu
		
		super.paint(g);       // clear all graphics on the screen before repainting the frame
		
		g.setFont(font);    // set the font that will be used
		
		
		if (menu == false)
			
		{
		
		boolean colourflag = false;	  // boolean variable to indicate what colour of grid square to paint
		
	
		
		if (start==false)   // if the game hasn't started yet, initialise all of the chess piece objects by calling their constructor methods and store them in ArrayList<Piece> pieces
		{
			start=true;
			
			pieces.add(new Rook(550,810, "white", "/w_rook.png"));
			pieces.add(new Knight(650,810, "white", "/w_knight.png"));
			pieces.add(new Bishop(750,810, "white", "/w_bishop.png"));
			pieces.add(new Queen(850,810, "white", "/w_queen.png"));
			pieces.add(new King(950,810, "white", "/w_king.png"));
			pieces.add(new Bishop(1050,810, "white", "/w_bishop.png"));
			pieces.add(new Knight(1150,810, "white", "/w_knight.png"));
			pieces.add(new Rook(1250,810, "white", "/w_rook.png"));
			pieces.add(new Pawn(550,710, "white", "/w_pawn.png"));
			pieces.add(new Pawn(650,710, "white", "/w_pawn.png"));		// create all of the white pieces at their default positions
			pieces.add(new Pawn(750,710, "white", "/w_pawn.png"));
			pieces.add(new Pawn(850,710, "white", "/w_pawn.png"));
			pieces.add(new Pawn(950,710, "white", "/w_pawn.png"));
			pieces.add(new Pawn(1050,710, "white", "/w_pawn.png"));
			pieces.add(new Pawn(1150,710, "white", "/w_pawn.png"));
			pieces.add(new Pawn(1250,710, "white", "/w_pawn.png"));
			
			pieces.add(new Rook(550,110, "black", "/b_rook.png"));
			pieces.add(new Knight(650,110, "black", "/b_knight.png"));
			pieces.add(new Bishop(750,110, "black", "/b_bishop.png"));
			pieces.add(new Queen(850,110, "black", "/b_queen.png"));
			pieces.add(new King(950,110, "black", "/b_king.png"));
			pieces.add(new Bishop(1050,110, "black", "/b_bishop.png"));
			pieces.add(new Knight(1150,110, "black", "/b_knight.png"));
			pieces.add(new Rook(1250,110, "black", "/b_rook.png"));
			pieces.add(new Pawn(550,210, "black", "/b_pawn.png"));
			pieces.add(new Pawn(650,210, "black", "/b_pawn.png"));		// create all of the black pieces at their default positions
			pieces.add(new Pawn(750,210, "black", "/b_pawn.png"));
			pieces.add(new Pawn(850,210, "black", "/b_pawn.png"));
			pieces.add(new Pawn(950,210, "black", "/b_pawn.png"));
			pieces.add(new Pawn(1050,210, "black", "/b_pawn.png"));
			pieces.add(new Pawn(1150,210, "black", "/b_pawn.png"));
			pieces.add(new Pawn(1250,210, "black", "/b_pawn.png"));
							
		}
		
		
		g.setColor(Color.CYAN);		// set the background colour
		g.fillRect(0,0,1920,1080);
		
		
		// draw the grid to represent the board
		
		for (int i=0; i<8; i++)		// iterate over each row
		{
			for (int j=0; j<8; j++)		// iterate over each column
			{
				
				if (colourflag == false)	// if colourflag is false, the next grid square should be a dark square
				{
					g.setColor(Color.DARK_GRAY);
				}
				
				else	// else colourflag is true and the next grid square should be a light square
				{
					g.setColor(Color.WHITE);
				}
				
				if (j!=7)
				{
					colourflag = !colourflag;	// indicate that the next grid square to be painted needs to be the opposite colour 

				}
								
				g.fillRect((530+100*j),(790-100*i), 100,100);	// draw a grid square				
			}						
		}
		
		
		if (win == null && draw == false)	// if the game is still ongoing
		{
		
		// if the user has selected a square, highlight the square selected by the user in green
		if (validsquare == true && sqx!=0 && sqx!=0)
		{
			g.setColor(Color.GREEN);
			g.fillRect(sqx-20, sqy-20, 100, 100);
		}
		
		}
		
		// iterate over the pieces ArrayList, painting each chess piece onto the board
		
		for (int i = 0; i < pieces.size(); i++)
		{
			new ImageIcon(ChessGame.class.getResource(pieces.get(i).sprite)).paintIcon(this, g, pieces.get(i).x, pieces.get(i).y);
		}
		
		
		
		
		
		
		// detect a draw by insufficient material
		if (IsDraw(pieces) == true)
		{
			draw = true;	// set this variable to true to end the game in a draw, preventing the users from playing any more moves
			
			g.setColor(Color.WHITE);
			g.drawString("DRAW", 110, 500);		// paint onto the screen that it is a draw
			g.setColor(Color.BLACK);
			g.drawString("BY INSUFFICIENT", 1400 , 500);
			g.drawString("MATERIAL", 1480 , 570);
			
		}
		
		
		if (win == null && draw == false)
		{
		
		if (turn == false)	// if it's white's move, paint onto the screen that it is white's move
		{
			g.setColor(Color.WHITE);
			g.drawString("WHITE'S MOVE", 30, 500);
		}
		
		else	// if it's black's move, paint onto the screen that it is black's move
		{
			g.setColor(Color.BLACK);
			g.drawString("BLACK'S MOVE", 1450, 500);
		}
		}
		
		
		if (win != null)	// if either player has won the game
			
		{
		
			if (win == true)	// if white has won the game, paint onto the screen that white is victorious by checkmating black
			{
			
				g.setColor(Color.WHITE);
				g.drawString("CHECKMATE", 110, 500);
				g.drawString("WHITE IS VICTORIOUS", 1360 , 500);
			
			}
			
		
			else 	// if black has won the game, paint onto the screen that black is victorious by checkmating white
			{
			
				g.setColor(Color.BLACK);
				g.drawString("CHECKMATE", 110, 500);
				g.drawString("BLACK IS VICTORIOUS", 1360 , 500);
		
			
			}
		
		}
		
		sqx=0;	// reset the coordinates of the square selected by the user
		sqy=0;	
		}
		
		
		else	// else the game has not started and we need to paint the instructions menu onto the screen
		{
			g.setColor(Color.CYAN);		// set the background colour
			g.fillRect(0,0,1920,1080);
			g.setColor(Color.BLACK);
			
			g.setFont(font1);
			g.drawString("Welcome to Chess!", 600, 150);
			g.setFont(font);
			g.drawString("Play with two people and try to checkmate your opponent", 300, 300);
			g.drawString("When the game begins, click on a piece to select it, and then click on a square", 60, 400);
			g.drawString("to move the selected piece to that square", 490, 450);
			g.drawString("The game will only let you play moves that are legal within the rules of Chess", 70, 600);
			g.drawString("Click anywhere on the screen to continue to the game, good luck!", 180, 850);
			
		}
	}
	
	
	
	
	
	
	
	
	
	// method to detect if the game is a draw
	
	private static boolean IsDraw (ArrayList<Piece> pieces)
	{
		
		int bishopcounter= 0; // local variable to count how many bishops are on the board
		int bishopindex = 0;  // local variable to store the index of a bishop within the pieces arraylist
		
		if (pieces.size() == 2)	// if there is only two pieces left on the board, this must be the two kings and this is a draw by insufficient material
		{
			return true;
		}
		
		if (pieces.size() == 3)	// if there is 3 pieces left on the board, we need to detect if this is king versus bishop & king OR king vs knight & king, because these scenarios result in a draw
		{
			
			for (int i=0; i<pieces.size(); i++)
			{
				if (pieces.get(i).getClass().getSimpleName().equals("Bishop") == true || pieces.get(i).getClass().getSimpleName().equals("Knight") == true)	// detect a bishop or knight
				{
					return true;
				}
			}
			
		}
		
		if (pieces.size() == 4)	  // if there are 4 pieces left on the board, with both black and white having one bishop remaining each, with the two bishops on the same colour of square, this is a draw
		{
			
			for (int i=0; i<pieces.size(); i++)
			{
			
			if (pieces.get(i).getClass().getSimpleName().equals("Bishop") == true)
				{
					bishopcounter++;
					
					if (bishopcounter == 1)	// store the index of the first bishop
					{
						bishopindex = i;
					}
					
					else	// else bishopcounter is equal to 2 and we have identified the second bishop
					{
						if (pieces.get(i).colour.equals(pieces.get(bishopindex).colour) == true)  // if the two bishops are the same colour (e.g they're both white or they're both black), this is not a draw							
						{
							return false;
						}
						
						else	// else one of the bishops is white and one of them is black
						{
							
						
						int x1 = ((pieces.get(bishopindex).x)-550)/100;	 // normalize the coordinates of the first bishop
						int y1 = ((pieces.get(bishopindex).y)-110)/100;
							
						int x2 = ((pieces.get(i).x)-550)/100;	 // normalize the coordinates of the second bishop
						int y2 = ((pieces.get(i).y)-110)/100;
							
						
						// if the first bishop is on a light square
						if ((((double) x1/2 == Math.floor((double) x1/2)) && ((double) y1/2 == Math.floor((double) y1/2))) || (((double) x1/2 != Math.floor((double) x1/2)) && ((double) y1/2 != Math.floor((double) y1/2))))
						{
							
							// if the second bishop is on a light square
							if (((x2/2 == Math.floor(x2/2)) && (y2/2 == Math.floor(y2/2))) || ((x2/2 != Math.floor(x2/2)) && (y2/2 != Math.floor(y2/2))))
							{
								return true;	// this is a draw, return true
							}
							
						}
													
						else	// else the first bishop is on a dark square
						{
							
							// if the second bishop is on a dark square
							if (((x2/2 == Math.floor(x2/2)) && (y2/2 != Math.floor(y2/2))) || ((x2/2 != Math.floor(x2/2)) && (y2/2 == Math.floor(y2/2))))
							{
								return true;	// this is a draw, return true
							}
						}
																			
						}
					}															
				}				
			}				
		}				
		return false;	// else it is not a draw, return false
	}
	
	
	

	
	
	
	
	
	public void mouseClicked(MouseEvent e) {	
	}
	public void mouseReleased(MouseEvent e) {		// these mouse methods are not needed, but must be implemented in the program anyway because we have implemented the MouseListener interface
	}
	public void mouseEntered(MouseEvent e) {	
	}
	public void mouseExited(MouseEvent e) {		
	}
}
