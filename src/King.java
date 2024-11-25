import java.util.ArrayList;

public class King extends Piece
{

	public Piece[][] board = new Piece[8][8];
	public boolean type;
	public Coordinate currentPos;
	public char symbol = 'K';
	public King(Piece[][] board, boolean isWhite, Coordinate currentPos)
	{
		super(board, isWhite, currentPos, 'K');
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
	public ArrayList<Coordinate> getAvailableMoves() 
	{
		ArrayList<Coordinate> pM = new ArrayList<Coordinate>();
		for(int i = -1; i < 2; i++)
		{
			int a = getLoc().getY()+i;
			if(a<0 || a>7) continue;
			for(int j = -1; j < 2; j++)
			{
				int b = getLoc().getX()+j;
				if(b<0 || b>7 || (0 == j && i == 0)) continue;
				boolean contained = false;
				for(Coordinate c: getOppMoves())
				{
					if(c.equals(new Coordinate(b, a)))
					{
						contained = true;
						break;
					}
				}
				if(contained) continue;
				if(board[a][b]!=null && board[a][b].getType()==getType()) continue;
				pM.add(new Coordinate(b, a));
			}
		}
		return pM;
	}
	
	public ArrayList<Coordinate> getOppMoves()
	{
		ArrayList<Coordinate> OM = new ArrayList<Coordinate>();
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(getBoard()[i][j]!=null && getBoard()[i][j].getType()!=getType())
				{
					if(getBoard()[i][j].getSymbol()=='K')
					{
						
						for(int k = -1; k < 2; k++)
						{
							int a = getBoard()[i][j].getLoc().getY()+k;
							if(a<0 || a>7) continue;
							for(int l = -1; l < 2; l++)
							{
								int b = getBoard()[i][j].getLoc().getX()+l;
								if(b<0 || b>7) continue;
								OM.add(new Coordinate(b, a));
							}
						}
						
					}
					else 
					{
						char s = board[i][j].getSymbol();
						if(s=='R' || s=='Q' || s=='B' || s=='P') 
						{
							OM.addAll(board[i][j].getAvailableMoves(true));
						}
						else
						{
							OM.addAll(board[i][j].getAvailableMoves());
						}
					}
				}
			}
		}
		return OM;
	}

	@Override
	public int getPointVal() {
		return Integer.MAX_VALUE;
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
