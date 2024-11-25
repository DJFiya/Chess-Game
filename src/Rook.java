import java.util.ArrayList;

public class Rook extends Piece{
	public Piece[][] board = new Piece[8][8];
	public boolean type;
	public Coordinate currentPos;
	public char symbol = 'R';
	public Rook(Piece[][] board, boolean isWhite, Coordinate currentPos)
	{
		super(board, isWhite, currentPos, 'R');
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
		//TODO: Castling
		ArrayList<Coordinate> pM = new ArrayList<Coordinate>();
		int x = getLoc().getX();
		int y = getLoc().getY();
		int tempX = x;
		int tempY = y;
		boolean valid = true;
		while(valid)
		{
			tempX++;
			if(tempX > 7 || tempX < 0)
			{
				valid = false;
				continue;
			}
			if (board[tempY][tempX]==null|| ((king)&&board[tempY][tempX].getSymbol()=='K' && board[tempY][tempX].getType()!=getType()))
			{
				pM.add(new Coordinate(tempX, tempY));
				continue;
			}
			if(board[tempY][tempX].getType()!=getType()) pM.add(new Coordinate(tempX, tempY));
			valid = false;
		}
		tempX = x;
		tempY = y;
		valid = true;
		while(valid)
		{
			tempX--;
			if(tempX > 7 || tempX < 0)
			{
				valid = false;
				continue;
			}
			if (board[tempY][tempX]==null|| ((king)&&board[tempY][tempX].getSymbol()=='K' && board[tempY][tempX].getType()!=getType()))
			{
				pM.add(new Coordinate(tempX, tempY));
				continue;
			}
			if(board[tempY][tempX].getType()!=getType()) pM.add(new Coordinate(tempX, tempY));
			valid = false;
		}
		tempX = x;
		tempY = y;
		valid = true;
		while(valid)
		{
			tempY--;
			if(tempY > 7 || tempY < 0)
			{
				valid = false;
				continue;
			}
			if (board[tempY][tempX]==null|| ((king)&&board[tempY][tempX].getSymbol()=='K' && board[tempY][tempX].getType()!=getType()))
			{
				pM.add(new Coordinate(tempX, tempY));
				continue;
			}
			if(board[tempY][tempX].getType()!=getType()) pM.add(new Coordinate(tempX, tempY));
			valid = false;
		}
		tempX = x;
		tempY = y;
		valid = true;
		while(valid)
		{
			tempY++;
			if(tempY > 7 || tempY < 0)
			{
				valid = false;
				continue;
			}
			if (board[tempY][tempX]==null|| ((king)&&board[tempY][tempX].getSymbol()=='K' && board[tempY][tempX].getType()!=getType()))
			{
				pM.add(new Coordinate(tempX, tempY));
				continue;
			}
			if(board[tempY][tempX].getType()!=getType()) pM.add(new Coordinate(tempX, tempY));
			valid = false;
		}
		return pM;
	}
	@Override

	public int getPointVal() {
		return 5;
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
