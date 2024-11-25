public class Coordinate
{
	private int x;
	private int y;
	public int getX() {return x;}
	public int getY() {return y;}
	public Coordinate(int a, int b)
	{
		x = a;
		y = b;
	}
	public boolean equals(Coordinate other)
	{
		return (other.getX()==getX() && other.getY() == getY());
	}
}