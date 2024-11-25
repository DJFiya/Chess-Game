import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Board 
{
	
	private  Piece[][] board = new Piece[8][8];
	public BufferedImage blackPawn, whitePawn, blackRook, whiteRook, blackKnight, whiteKnight, blackBishop, whiteBishop, blackQueen, whiteQueen, blackKing, whiteKing;
	int s;
	Coordinate cell;
	MouseHandler mH;
	public boolean passantFlag = false;
	public int passantIndex = -1;
	boolean whiteTurn = true;
	public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    private GamePanel gp;
    private boolean jP = false;
    private boolean promotingPawn = false;
    private int fiftyMoveRule = 0;
    private List<String> positionsHistory = new ArrayList<>();
    
	
    public void reset()
    {
    	resetBoard();
    	informPieces();
    	jP = false;
    	promotingPawn = false;
    	passantFlag = false;
    	passantIndex = -1;
    	whiteTurn = true;
    	fiftyMoveRule = 0;
    	positionsHistory.clear();
    }
    
	public Board(GamePanel gp)
	{
		setS(gp.tileSize);
		setImages();
		resetBoard();
		mH = gp.mH;
		this.gp = gp;
	}
	
	public Board(Piece[][] b)
	{
		setImages();
		for(int i = 0; i < 8; i ++)
		{
			for(int j = 0; j < 8; j++)
			{
				setBoard(i, j, b[i][j]);
			}
		}
	}	
	
	public  Piece[][] getBoard() {return board;}
	public int getS() {return s;}
	public Coordinate getCell() {return cell;}
	public GamePanel getGP() {return gp;}
	
	public void setS(int a) {s=a;}
	public  void setBoard(int x, int y, Piece n) {board[x][y] = n;}
	public void setCell(Coordinate c) {cell  = c;}
	
	
	public void setImages()
	{
		try
		{
			blackPawn = ImageIO.read(new File("pb.png"));
			whitePawn = ImageIO.read(new File("pw.png"));
			blackRook = ImageIO.read(new File("rb.png"));
			whiteRook = ImageIO.read(new File("rw.png"));
			blackKnight = ImageIO.read(new File("nb.png"));
			whiteKnight = ImageIO.read(new File("nw.png"));
			blackBishop = ImageIO.read(new File("bb.png"));
			whiteBishop = ImageIO.read(new File("bw.png"));
			blackQueen = ImageIO.read(new File("qb.png"));
			whiteQueen = ImageIO.read(new File("qw.png"));
			blackKing = ImageIO.read(new File("kb.png"));
			whiteKing = ImageIO.read(new File("kw.png"));
		}catch(IOException e) {System.out.println("error loading images");}
	}

	public Piece[][] copyBoard()
	{
		Piece[][] cBoard = new Piece[8][8];
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(getBoard()[i][j]==null) cBoard[i][j] = null;
				else if(getBoard()[i][j].symbol=='P') cBoard[i][j] = new Pawn(getBoard(), getBoard()[i][j].getType(), new Coordinate(j, i));
				else if(getBoard()[i][j].symbol=='R') cBoard[i][j] = new Rook(getBoard(), getBoard()[i][j].getType(), new Coordinate(j, i));
				else if(getBoard()[i][j].symbol=='N') cBoard[i][j] = new Knight(getBoard(), getBoard()[i][j].getType(), new Coordinate(j, i));
				else if(getBoard()[i][j].symbol=='B') cBoard[i][j] = new Bishop(getBoard(), getBoard()[i][j].getType(), new Coordinate(j, i));			
				else if(getBoard()[i][j].symbol=='Q') cBoard[i][j] = new Queen(getBoard(), getBoard()[i][j].getType(), new Coordinate(j, i));
				else if(getBoard()[i][j].symbol=='K') cBoard[i][j] = new King(getBoard(), getBoard()[i][j].getType(), new Coordinate(j, i));
			}
		}
		return cBoard;
	}
	
	public char[][] copyBoard(char[][] nBoard)
	{
		char[][] cBoard = new char[8][8];
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				cBoard[i][j] = nBoard[i][j];
			}
		}
		return cBoard;
	}
	
	public void resetBoard()
	{
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j<8; j++)
			{
				if(1<i&&i<6) board[i][j] = null;
				else if(i==1) board[i][j] = new Pawn(board, false, new Coordinate(j, i));
				else if(i==6) board[i][j] = new Pawn(board, true, new Coordinate(j, i));
				else if(i==0)
				{
					if(j%7==0) board[i][j] = new Rook(board, false, new Coordinate(j, i));
					else if(j%5==1) board[i][j] = new Knight(board, false, new Coordinate(j, i));
					else if(j==2||j==5)board[i][j] = new Bishop(board, false, new Coordinate(j, i));
					else if(j==3) board[i][j] = new Queen(board, false, new Coordinate(j, i));
					else board[i][j] = new King(board, false, new Coordinate(j, i));;
				}
				else
				{
					if(j%7==0) board[i][j] = new Rook(board, true, new Coordinate(j, i));
					else if(j%5==1) board[i][j] = new Knight(board, true, new Coordinate(j, i));
					else if(j==2||j==5)board[i][j] = new Bishop(board, true, new Coordinate(j, i));
					else if(j==3) board[i][j] = new Queen(board, true, new Coordinate(j, i));
					else board[i][j] = new King(board, true, new Coordinate(j, i));
				}
			}
		}
		informPieces();
	}
	
	public boolean isInsufficientMaterial() {
	    // Count pieces on the board
	    int whitePieces = countPieces(true);
	    int blackPieces = countPieces(false);
	    
	    // Check for specific insufficient material scenarios
	    // Scenario 1: King versus King
	    if (whitePieces == 1 && blackPieces == 1) {
	        return true;
	    }
	    
	    // Scenario 2: King and bishop versus king
	    // Scenario 3: King and knight versus king
	    // Scenario 4: King and bishop versus king and bishop with bishops on the same color
	    // Implement these checks based on your board and piece logic
	    
	    // Scenario 2: King and bishop versus king
	    if (whitePieces == 2 && blackPieces == 1) {
	        if (hasKingAndBishop(true)) {
	            return true;
	        }
	    }
	    if (blackPieces == 2 && whitePieces == 1) {
	        if (hasKingAndBishop(false)) {
	            return true;
	        }
	    }
	    
	    // Scenario 3: King and knight versus king
	    if (whitePieces == 2 && blackPieces == 1) {
	        if (hasKingAndKnight(true)) {
	            return true;
	        }
	    }
	    if (blackPieces == 2 && whitePieces == 1) {
	        if (hasKingAndKnight(false)) {
	            return true;
	        }
	    }
	    
	    // Scenario 4: King and bishop versus king and bishop with bishops on the same color
	    if (whitePieces == 2 && blackPieces == 2) {
	        if (hasKingAndBishop(true) && hasKingAndBishop(false) && bishopsOnSameColor()) {
	            return true;
	        }
	    }
	    
	    return false;
	}

	private int countPieces(boolean isWhite) {
	    int count = 0;
	    Piece[][] currentBoard = getBoard();
	    for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
	            Piece piece = currentBoard[i][j];
	            if (piece != null && piece.getType() == isWhite) {
	                count++;
	            }
	        }
	    }
	    return count;
	}

	private boolean hasKingAndBishop(boolean isWhite) {
	    Piece[][] currentBoard = getBoard();
	    boolean hasKing = false;
	    boolean hasBishop = false;
	    
	    for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
	            Piece piece = currentBoard[i][j];
	            if (piece != null && piece.getType() == isWhite) {
	                if (piece.getSymbol() == 'K') {
	                    hasKing = true;
	                } else if (piece.getSymbol() == 'B') {
	                    hasBishop = true;
	                }
	            }
	        }
	    }
	    
	    return hasKing && hasBishop;
	}

	private boolean hasKingAndKnight(boolean isWhite) {
	    Piece[][] currentBoard = getBoard();
	    boolean hasKing = false;
	    boolean hasKnight = false;
	    
	    for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
	            Piece piece = currentBoard[i][j];
	            if (piece != null && piece.getType() == isWhite) {
	                if (piece.getSymbol() == 'K') {
	                    hasKing = true;
	                } else if (piece.getSymbol() == 'N') {
	                    hasKnight = true;
	                }
	            }
	        }
	    }
	    
	    return hasKing && hasKnight;
	}

	private boolean bishopsOnSameColor() {
	    Piece[][] currentBoard = getBoard();
	    Coordinate whiteBishop = null;
	    Coordinate blackBishop = null;
	    
	    for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
	            Piece piece = currentBoard[i][j];
	            if (piece != null) {
	                if (piece.getSymbol() == 'B') {
	                    if (piece.getType()) { // White bishop
	                        if (whiteBishop == null) {
	                            whiteBishop = new Coordinate(j, i);
	                        } else {
	                            return isWhiteSquare(whiteBishop) == isWhiteSquare(new Coordinate(j, i));
	                        }
	                    } else { // Black bishop
	                        if (blackBishop == null) {
	                            blackBishop = new Coordinate(j, i);
	                        } else {
	                            return isWhiteSquare(blackBishop) == isWhiteSquare(new Coordinate(j, i));
	                        }
	                    }
	                }
	            }
	        }
	    }
	    
	    return false;
	}

	private boolean isWhiteSquare(Coordinate c) {
	    return (c.getX() + c.getY()) % 2 == 0;
	}
	
	public boolean canCastle(boolean isWhite, boolean isLeft)
	{
		Coordinate king = new Coordinate(0,0);
		outerloop:
		    for (int i = 0; i < 8; i++) {
		        for (int j = 0; j < 8; j++) {
		            if (getBoard()[i][j] != null && getBoard()[i][j].getType() == isWhite && getBoard()[i][j].getSymbol() == 'K') {
		                king = new Coordinate(j, i);
		                break outerloop;
		            }
		        }
		    }
		if(isLeft && (getBoard()[king.getY()][0]==null || getBoard()[king.getY()][0].getHasMoved() || getBoard()[king.getY()][king.getX()].getHasMoved())) return false;
		if(!isLeft &&(getBoard()[king.getY()][7]==null || getBoard()[king.getY()][7].getHasMoved() || getBoard()[king.getY()][king.getX()].getHasMoved())) return false;
		if(isLeft)
		{
			for(int i = 1; i < king.getX(); i++)
			{
				if(getBoard()[king.getY()][i]!=null) return false;
			}
		}
		else
		{
			for(int i = 6; i > king.getX(); i--)
			{
				if(getBoard()[king.getY()][i]!=null) return false;
			}
		}
		if(kingInCheck(isWhite)) return false;
		Piece[][] bo = copyBoard();
		if(isLeft) 
		{
			bo[king.getY()][2] = bo[king.getY()][king.getX()];
			bo[king.getY()][3] = bo[king.getY()][0];
			bo[king.getY()][0] = null;
			bo[king.getY()][king.getX()] = null;
		}
		else 
		{
			bo[king.getY()][6] = bo[king.getY()][king.getX()];
			bo[king.getY()][5] = bo[king.getY()][0];
			bo[king.getY()][7] = null;
			bo[king.getY()][king.getX()] = null;
		}
		bo[getCell().getY()][getCell().getX()] = null;
		Board b = new Board(bo);
		b.informPieces();
		if(b.kingInCheck(isWhite)) return false;
		return true;
	}
	
	public void update()
	{
		int cx = mH.getCx();
		int cy = mH.getCy();
		mH.resetClick();
		if(promotingPawn)
		{
			if(0<cx && cx < getS()*5 && 0 < cy && cy < getS()*5)
			{
				for(int i = 0; i < 8; i++)
				{
					if(getBoard()[whiteTurn? 7:0][i]!=null && getBoard()[whiteTurn? 7:0][i].getSymbol()=='P')
					{
						getBoard()[whiteTurn? 7:0][i] = new Queen(getBoard(), !whiteTurn, new Coordinate(i, whiteTurn? 7:0));
					}
				}
				promotingPawn = false;
				informPieces();
			}
			else if(0<cx && cx < getS()*5 && getS()*5 < cy && cy < getS()*10)
			{
				for(int i = 0; i < 8; i++)
				{
					if(getBoard()[whiteTurn? 7:0][i]!=null && getBoard()[whiteTurn? 7:0][i].getSymbol()=='P')
					{
						getBoard()[whiteTurn? 7:0][i] = new Bishop(getBoard(), !whiteTurn, new Coordinate(i, whiteTurn? 7:0));
					}
				}
				promotingPawn = false;
				informPieces();
			}
			else if(getS()*5<cx && cx < getS()*10 && getS()*5 < cy && cy < getS()*10)
			{
				for(int i = 0; i < 8; i++)
				{
					if(getBoard()[whiteTurn? 7:0][i]!=null && getBoard()[whiteTurn? 7:0][i].getSymbol()=='P')
					{
						getBoard()[whiteTurn? 7:0][i] = new Knight(getBoard(), !whiteTurn, new Coordinate(i, whiteTurn? 7:0));
					}
				}
				promotingPawn = false;
				informPieces();
			}
			else if(getS()*5<cx && cx < getS()*10 && 0 < cy && cy < getS()*5)
			{
				for(int i = 0; i < 8; i++)
				{
					if(getBoard()[whiteTurn? 7:0][i]!=null && getBoard()[whiteTurn? 7:0][i].getSymbol()=='P')
					{
						getBoard()[whiteTurn? 7:0][i] = new Rook(getBoard(), !whiteTurn, new Coordinate(i, whiteTurn? 7:0));
					}
				}
				promotingPawn = false;
				informPieces();
			}
		}
		if(isInsufficientMaterial()) {
	        getGP().gameStatus[1] = true;
	        System.out.println("Insufficient Material");
	    }
		else if(getGP().keyH.dPr) 
		{
			getGP().gameStatus[1] = true;
	        System.out.println("Draw by Mutual Agreement");
		}
		else if(getGP().keyH.rPr)
		{
			getGP().gameStatus[(!whiteTurn)? 0: 2] = true;
			System.out.println((whiteTurn? "White":"Black") + " Resigned");
		}
		else if(jP)
		{
			if(noValidMoves(whiteTurn))
			{
				if(kingInCheck(whiteTurn))
				{
					getGP().gameStatus[(!whiteTurn)? 0: 2] = true;
					System.out.println("Checkmate");
				}
				else 
				{
					getGP().gameStatus[1] = true;
					System.out.println("Stalemate");
				}
			}
			jP = false;
		}
		else if(fiftyMoveRule == 100) 
		{
			getGP().gameStatus[1] = true;
			System.out.println("50 Move Rule");
		}
		else if (isThreefoldRepetition()) 
		{
			getGP().gameStatus[1] = true;
	        System.out.println("Threefold Repitition");
		}
		int tempNum = count();
		if(getS()<=cx && cx <= getS()*9 && getS()<=cy && cy <= getS()*9 && !promotingPawn && !getGP().gameStatus[0] && !getGP().gameStatus[1] && !getGP().gameStatus[2])
		{
			Coordinate c = new Coordinate(cx/getS()-1, cy/getS()-1);
			if(getCell()!=null && getCell().getX()==c.getX()&&getCell().getY()==c.getY()) setCell(null);
			else 
			{
				//Movement
				if(getCell()!=null && c!=null && board[getCell().getY()][getCell().getX()] != null && validMove(c)) 
				{
					boolean justFlagged = false;
					if(board[getCell().getY()][getCell().getX()].getSymbol() == 'P')
					{
						
						if(Math.abs(c.getY()-getCell().getY())==2)
						{
							passantFlag = true;
							passantIndex = c.getX();
							informPawns(true, c.getX());
							justFlagged = true;
						}
					}
					if(passantFlag)
					{
						if(board[getCell().getY()][getCell().getX()].getSymbol()=='P')
						{
							if(board[getCell().getY()][getCell().getX()].getType())
							{
								if(c.getX()==passantIndex && c.getY()==2)
								{
									board[c.getY()+1][c.getX()] = null;
								}
							}
							else
							{
								if(c.getX()==passantIndex && c.getY()==5)
								{
									board[c.getY()-1][c.getX()] = null;
								}
							}
						}
					}
					
					if(passantFlag && !justFlagged) 
					{
						informPawns(false, -1);
						passantFlag = false;
						passantIndex = -1;
					}
					justFlagged = false;
					
					if(getBoard()[getCell().getY()][getCell().getX()].getSymbol() == 'K' && Math.abs(c.getX() - getCell().getX())==2)
					{
						if(c.getX() - getCell().getX()==2)
						{
							board[c.getY()][5] = board[c.getY()][7];
							board[c.getY()][7] = null;
						}
						else
						{
							board[c.getY()][3] = board[c.getY()][0];
							board[c.getY()][0] = null;
						}
					}
					
					if(getBoard()[getCell().getY()][getCell().getX()].getSymbol() == 'P' && (whiteTurn? c.getY():7-c.getY()) == 0) promotingPawn  = true;
					
					board[c.getY()][c.getX()] = board[getCell().getY()][getCell().getX()];
					board[getCell().getY()][getCell().getX()] = null;
					board[c.getY()][c.getX()].setLoc(c.getX(), c.getY());
					board[c.getY()][c.getX()].setHasMoved(true);
					informPieces();
					int tempNum2 = count();
					if(board[c.getY()][c.getX()].getSymbol()!='P' && tempNum == tempNum2) fiftyMoveRule++;
					else fiftyMoveRule = 0;
					addCurrentPosition();
					setCell(null);
					whiteTurn = !whiteTurn;
					jP = true;
				}
				
				//New Pos
				else setCell(c);
			}
		}
	}
	
	private void addCurrentPosition()
	{
		StringBuilder currentPosition = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece == null) {
                    currentPosition.append("-");
                } else {
                    currentPosition.append(piece.getSymbol()); // Append piece symbol
                    currentPosition.append(piece.getType() ? 'w' : 'b'); // Append color
                }
            }
        }
        currentPosition.append(whiteTurn ? 'w' : 'b');
        positionsHistory.add(currentPosition.toString());
	}
	
	private boolean isThreefoldRepetition() 
	{
		if(positionsHistory.isEmpty()) return false;
		String currentPosition = positionsHistory.get(positionsHistory.size()-1);
        int count = 0;
        for (String position : positionsHistory) {
            if (position.equals(currentPosition.toString())) {
                count++;
            }
        }
        return count >= 3;
	}

	private int count() 
	{
		int sum = 0;
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(board[i][j]!=null) sum++;
			}
		}
		return sum;
	}

	public boolean noValidMoves(boolean isWhite)
	{
		Piece[][] cBoard = copyBoard();
		Board b = new Board(cBoard);
		b.whiteTurn = isWhite;
		b.informPieces();
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(b.getBoard()[i][j]!=null && b.getBoard()[i][j].getType()==isWhite)
				{
					b.setCell(new Coordinate(j, i));
					ArrayList<Coordinate> p = new ArrayList<Coordinate>();
					char a = b.getBoard()[i][j].getSymbol();
					if(a=='R' || a=='Q' || a=='B' || a=='P')
					{
						p = b.getBoard()[i][j].getAvailableMoves(false);
					}
					else
					{
						p = b.getBoard()[i][j].getAvailableMoves();
					}
					for(Coordinate c: p)
					{
						if(b.validMove(c)) return false;
					}
				}
			}
		}
		return true;
	}
	
	private void informPawns(boolean b, int index) 
	{
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(board[i][j]!=null && board[i][j].getSymbol()=='P') {board[i][j].setPSNT(b); board[i][j].setPIndex(index);}
			}
		}
	}

	private boolean validMove(Coordinate c) 
	{
		//Check valid moves for pinned pieces (e.g. King can be captured)
		if (getCell() == null) return false;
		char s = board[getCell().getY()][getCell().getX()].getSymbol();
		ArrayList<Coordinate> pM = new ArrayList<Coordinate>();
		if(s=='R' || s=='Q' || s=='B' || s=='P') 
		{
			pM = board[getCell().getY()][getCell().getX()].getAvailableMoves(false);
		}
		else
		{
			pM = board[getCell().getY()][getCell().getX()].getAvailableMoves();
		}
		boolean had = false;
		for(Coordinate co: pM)
		{
			if(co.equals(c)) {had = true; break;}
		}
		if(getCell() != null && board[getCell().getY()][getCell().getX()]!=null && board[getCell().getY()][getCell().getX()].getSymbol()=='K')
		{
			if(canCastle(getCell().getY()==7? true:false, false)) 
			{
				if(getCell()!=null && c.getY()==getCell().getY() && c.getX()==6) return true;
			}
			if(canCastle(getCell().getY()==7? true:false, true)) 
			{
				if(getCell()!=null && c.getY()==getCell().getY() && c.getX()==2) return true;
			}
		}
		if(!had) return false;
		
		Piece[][] bo = copyBoard();
		bo[c.getY()][c.getX()] = bo[getCell().getY()][getCell().getX()];
		bo[getCell().getY()][getCell().getX()] = null;
		Board b = new Board(bo);
		b.informPieces();
		if(b.kingInCheck(whiteTurn)) return false;
		if(getCell()==null) return false;
		return getBoard()[getCell().getY()][getCell().getX()].getType()==whiteTurn;
	}

	public void informPieces()
	{
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(getBoard()[i][j]!=null) getBoard()[i][j].setBoard(getBoard());
			}
		}
	}
	
	public void paint(Graphics g)
	{
		Graphics2D b = (Graphics2D)g;
		b.setColor(Color.BLACK);
		b.setStroke(new BasicStroke(10));
		b.drawRect(getS(), getS(), getS()*8, getS()*8);
		b.setStroke(new BasicStroke(3));
		b.setColor(new Color((whiteTurn) ? 255 : 0, (whiteTurn) ? 255 : 0, (whiteTurn) ? 255 : 0));
		b.fillRect((int)Math.round(getS()*4.8), (int)Math.round(getS()*0.3), (int)Math.round(getS()*0.4), (int)Math.round(getS()*0.4));
		b.fillRect((int)Math.round(getS()*4.8), (int)Math.round(getS()*9.3), (int)Math.round(getS()*0.4), (int)Math.round(getS()*0.4));
		b.setColor(new Color((whiteTurn) ? 0 : 255, (whiteTurn) ? 0 : 255, (whiteTurn) ? 0 : 255));
		b.drawRect((int)Math.round(getS()*4.8), (int)Math.round(getS()*0.3), (int)Math.round(getS()*0.4), (int)Math.round(getS()*0.4));
		b.drawRect((int)Math.round(getS()*4.8), (int)Math.round(getS()*9.3), (int)Math.round(getS()*0.4), (int)Math.round(getS()*0.4));
		b.setStroke(new BasicStroke(1));
		if(!promotingPawn)
		{
			for(int i = 0; i < 8; i++)
			{
				for(int j = 0; j<8; j++)
				{
					int m = getS()*(j+1);
					int n = getS()*(i+1);
					if((i+j)%2==0) b.setColor(new Color(50, 205, 0));
					else b.setColor(new Color(1, 121, 111));
					b.fillRect(m, n, getS(), getS());
					int x = m+getS()/4;
					int y = n+getS()/4;
					if(board[i][j]==null) continue;
					else if(board[i][j].symbol=='P' && board[i][j].getType() == false) b.drawImage(blackPawn, x, y, getS()/2, getS()/2, null);
					else if(board[i][j].symbol=='P' && board[i][j].getType() == true) b.drawImage(whitePawn, x, y, getS()/2, getS()/2, null);
					else if(board[i][j].symbol=='R' && board[i][j].getType() == false) b.drawImage(blackRook, x, y, getS()/2, getS()/2, null);
					else if(board[i][j].symbol=='R' && board[i][j].getType() == true) b.drawImage(whiteRook, x, y, getS()/2, getS()/2, null);
					else if(board[i][j].symbol=='N' && board[i][j].getType() == false) b.drawImage(blackKnight, x, y, getS()/2, getS()/2, null);
					else if(board[i][j].symbol=='N' && board[i][j].getType() == true) b.drawImage(whiteKnight, x, y, getS()/2, getS()/2, null);
					else if(board[i][j].symbol=='B' && board[i][j].getType() == false) b.drawImage(blackBishop, x, y, getS()/2, getS()/2, null);
					else if(board[i][j].symbol=='B' && board[i][j].getType() == true) b.drawImage(whiteBishop, x, y, getS()/2, getS()/2, null);
					else if(board[i][j].symbol=='Q' && board[i][j].getType() == false) b.drawImage(blackQueen, x, y, getS()/2, getS()/2, null);
					else if(board[i][j].symbol=='Q' && board[i][j].getType() == true) b.drawImage(whiteQueen, x, y, getS()/2, getS()/2, null);
					else if(board[i][j].symbol=='K' && board[i][j].getType() == false) b.drawImage(blackKing, x, y, getS()/2, getS()/2, null);
					else if(board[i][j].symbol=='K' && board[i][j].getType() == true) b.drawImage(whiteKing, x, y, getS()/2, getS()/2, null);
				}
			}
		}
		if(getCell()!=null && !promotingPawn)
		{
			Color c = b.getColor();
			b.setColor(new Color(255, 0, 0, 70));
			Stroke s = b.getStroke();
			b.setStroke(new BasicStroke(10));
			b.drawRect(getS()*(1+cell.getX()), getS()*(1+cell.getY()), getS(), getS());
			b.setColor(c);
			b.setStroke(s);
			if(getCell()!= null && board[getCell().getY()][getCell().getX()]!=null && board[getCell().getY()][getCell().getX()].getType()==whiteTurn)
			{
				b.setColor(new Color(0, 0, 0, 30));
				char sy = board[getCell().getY()][getCell().getX()].getSymbol();
				ArrayList<Coordinate> pM = new ArrayList<Coordinate>();
				if(sy=='R' || sy=='Q' || sy=='B' || sy == 'P') 
				{
					pM = board[getCell().getY()][getCell().getX()].getAvailableMoves(false);
				}
				else
				{
					pM = board[getCell().getY()][getCell().getX()].getAvailableMoves();
				}
				for(Coordinate co: pM)
				{
					if(validMove(co))b.fillOval((co.getX()+1)*getS()+getS()/4, (co.getY()+1)*getS()+getS()/4, getS()/2, getS()/2);
				}
				if(getCell()!=null && board[getCell().getY()][getCell().getX()]!=null && board[getCell().getY()][getCell().getX()].getSymbol()=='K')
				{
					if(canCastle(getCell().getY()==7? true:false, false)) 
					{
						b.fillOval((getCell().getX()+3)*getS()+getS()/4, (getCell().getY()+1)*getS()+getS()/4, getS()/2, getS()/2);
					}
					if(canCastle(getCell().getY()==7? true:false, true)) 
					{
						b.fillOval((getCell().getX()-1)*getS()+getS()/4, (getCell().getY()+1)*getS()+getS()/4, getS()/2, getS()/2);
					}
				}
				b.setColor(c);
				
			}
		}
		if(promotingPawn)
		{
			b.setColor(whiteTurn? Color.white:Color.BLACK);
			b.fillRect(0, 0, getS()*10, getS()*10);
			b.setColor(!whiteTurn? Color.white:Color.BLACK);
			b.setStroke(new BasicStroke(3));
			b.drawLine(0, getS()*5, getS()*10, getS()*5);
			b.drawLine(getS()*5, getS()*10, getS()*5, 0);
			b.setStroke(new BasicStroke(1));
			b.drawImage(whiteTurn? blackQueen:whiteQueen, getS()*3/2, getS()*3/2, getS()*2, getS()*2, null);
			b.drawImage(whiteTurn? blackRook:whiteRook, getS()*13/2, getS()*3/2, getS()*2, getS()*2, null);
			b.drawImage(whiteTurn? blackBishop:whiteBishop, getS()*3/2, getS()*13/2, getS()*2, getS()*2, null);
			b.drawImage(whiteTurn? blackKnight:whiteKnight, getS()*13/2, getS()*13/2, getS()*2, getS()*2, null);
			int mx = getGP().getMousePosition().x/(getS()*5);
			int my = getGP().getMousePosition().y/(getS()*5);
			b.setColor(new Color(127, 127, 127, 100));
			b.fillRect(mx*getS()*5, my*getS()*5, getS()*5, getS()*5);
		}
	}
	public boolean kingInCheck(boolean isWhite) {
	    Coordinate c = new Coordinate(0, 0);
	    ArrayList<Coordinate> pm = new ArrayList<Coordinate>();
	    // Find the coordinates of the king of the specified color
	    outerloop:
	    for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
	            if (getBoard()[i][j] != null && getBoard()[i][j].getType() == isWhite && getBoard()[i][j].getSymbol() == 'K') {
	                c = new Coordinate(j, i);
	                break outerloop;
	            }
	        }
	    }
	    
	    //System.out.println("King's Coordinate: " + c.getX() + " " + c.getY());
	    
	    // Check if the king is in check by iterating through opponent's pieces
	    for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
	            if (getBoard()[i][j] != null && getBoard()[i][j].getType() != isWhite) 
	            {
	            	/*
	            	System.out.println();
	            	for(int k = 0; k < 8; k++)
	            	{
	            		for(int l = 0; l < 8; l++)
	            		{
	            			if(getBoard()[k][l]==null) System.out.print("_");
	            			else System.out.print(getBoard()[k][l].getSymbol());
	            		}
	            		System.out.println();
	            	}
	            	*/
	            	char s1 = getBoard()[i][j].getSymbol();
	                if(s1=='R' || s1=='Q' || s1=='B' || s1=='P') pm = getBoard()[i][j].getAvailableMoves(false);
	                else pm = getBoard()[i][j].getAvailableMoves();
	                if (pm == null) {
	                    System.out.println("Available moves list is null");
	                    continue;
	                }
	                
	                for (Coordinate co : pm) {
	                    if (co == null) {
	                        System.out.println("Coordinate in available moves is null");
	                        continue;
	                    }
	                    
	                    if (c == null) {
	                        System.out.println("King's coordinate is null");
	                        continue;
	                    }
	                    
	                    if (c.equals(co)) {
	                        return true;
	                    }
	                }
	            }
	        }
	    }
	    
	    return false;
	}
}
