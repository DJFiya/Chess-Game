import java.util.*;
public abstract class Piece 
{
	public char symbol;
	//private int pointVal;
	//private ArrayList<Coordinate> possibleMoves = new ArrayList<Coordinate>();
	boolean type;
	boolean canPassant = false;
	int passantIndex = -1;
	Coordinate currentPos;	
	boolean hasMoved;
	public Piece(Piece[][] board, boolean isWhite, Coordinate currentPos, char s)
	{
		type = isWhite;
		this.currentPos = currentPos;
		setBoard(board);
		symbol = s;
	}
	public abstract ArrayList<Coordinate> getAvailableMoves();
	public abstract int getPointVal();	
	public abstract void setBoard(Piece[][] board);
	public abstract Piece[][] getBoard();
	public abstract boolean getIsWhite();
	public boolean getType() {return type;}
	public Coordinate getLoc() {return currentPos;}
	public char getSymbol() {return symbol;}
	
	public void setLoc(int i, int j)
	{
		currentPos = new Coordinate(i, j);
	}
	
	public boolean getPSNT() {return canPassant;}
	public void setPSNT(boolean b) {canPassant = b;}
	
	public int getPIndex() {return passantIndex;}
	public void setPIndex(int b) {passantIndex = b;}
	protected abstract ArrayList<Coordinate> getAvailableMoves(boolean b);
	public boolean getHasMoved() {return hasMoved;}
	public void setHasMoved(boolean b) {hasMoved = b;}
}
