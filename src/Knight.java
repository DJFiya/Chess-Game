import java.util.ArrayList;

public class Knight extends Piece{
	public Piece[][] board = new Piece[8][8];
	public boolean type;
	public Coordinate currentPos;
	public char symbol = 'N';
	public Knight(Piece[][] board, boolean isWhite, Coordinate currentPos)
	{
		super(board, isWhite, currentPos, 'N');
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

	public ArrayList<Coordinate> getAvailableMoves() {
		ArrayList<Coordinate> pM = new ArrayList<Coordinate>();
		for(int i = -2; i < 3; i++)
		{
			if(i == 0 || getLoc().getY()+i>7 || getLoc().getY()+i<0) continue;
			int x = 3-Math.abs(i);
			for(int j = -x; j<= x; j+=(2*x))
			{
				if(getLoc().getX()+j>7 || getLoc().getX()+j<0) continue;
				if(board[getLoc().getY()+i][getLoc().getX()+j]==null)
				{
					pM.add(new Coordinate(getLoc().getX()+j, getLoc().getY()+i));
					continue;
				}
				if(board[getLoc().getY()+i][getLoc().getX()+j].getType()!=getType())
				{
					pM.add(new Coordinate(getLoc().getX()+j, getLoc().getY()+i));
				}
			}
		}
		return pM;
	}
	@Override

	public int getPointVal() {
		return 3;
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
	protected ArrayList<Coordinate> getAvailableMoves(boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

}
