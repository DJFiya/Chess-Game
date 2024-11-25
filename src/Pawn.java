import java.util.ArrayList;

public class Pawn extends Piece{
	public Piece[][] board = new Piece[8][8];
	public boolean type;
	public Coordinate currentPos;
	public char symbol;
	public boolean canPassant = false;
	
	
	public Pawn(Piece[][] board, boolean isWhite, Coordinate currentPos)
	{
		super(board, isWhite, currentPos, 'P');
	}
	@Override

	public void setBoard(Piece[][] board)
	{
		this.board = new Piece[8][8];
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				this.board[i][j] = board[i][j];
			}
		}
	}
	@Override

	public ArrayList<Coordinate> getAvailableMoves(boolean king) {
		ArrayList<Coordinate> pM = new ArrayList<Coordinate>();
		int x = getLoc().getX();
		int y = getLoc().getY();
		if(getType())
		{
			if(y>0 && board[y-1][x]==null) 
			{
				if(!king)pM.add(new Coordinate(x, y-1));
				if(!king) {if(y==6 && board[y-2][x]==null) pM.add(new Coordinate(x, y-2));}
			}
			if(y>0 && x>0 && board[y-1][x-1] != null && board[y-1][x-1].getType()!=getType()) pM.add(new Coordinate(x-1, y-1));
			if(y>0 && x<7 && board[y-1][x+1] != null && board[y-1][x+1].getType()!=getType()) pM.add(new Coordinate(x+1, y-1));
			if(king)
			{
				pM.add(new Coordinate(x-1, y-1));
				pM.add(new Coordinate(x+1, y-1));
			}
			if(getPSNT() && y == 3 && Math.abs(x-passantIndex)==1) pM.add(new Coordinate(passantIndex, 2));
		}
		else
		{
			if(y<7 && board[y+1][x]==null) 
			{
				if(!king) pM.add(new Coordinate(x, y+1));
				if(!king) {if(y==1 && board[y+2][x]==null) pM.add(new Coordinate(x, y+2));}
			}
			if(y<7 && x>0 && board[y+1][x-1] != null && board[y+1][x-1].getType()!=getType()) pM.add(new Coordinate(x-1, y+1));
			if(y<7 && x<7 && board[y+1][x+1] != null && board[y+1][x+1].getType()!=getType()) pM.add(new Coordinate(x+1, y+1));
			if(king)
			{
				pM.add(new Coordinate(x-1, y+1));
				pM.add(new Coordinate(x+1, y+1));
			}
			if(getPSNT() && y == 4 && Math.abs(x-passantIndex)==1) pM.add(new Coordinate(passantIndex, 5));
		}
		return pM;
	}
	@Override

	public int getPointVal() {
		return 1;
	}
	@Override

	public Piece[][] getBoard() {
		return board;
	}
	@Override

	public boolean getIsWhite() {
		return type;
	}
	@Override
	public ArrayList<Coordinate> getAvailableMoves() {
		// TODO Auto-generated method stub
		return null;
	}
}
