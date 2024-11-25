import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener
{
	private int cx, cy, mx, my;
	public int getCx() {return cx;}
	public int getCy() {return cy;}
	public int getMx() {return mx;}
	public int getMy() {return my;}
	
	public void setCx(int a) {cx = a;}
	public void setCy(int a) {cy = a;}
	public void setMx(int a) {mx = a;}
	public void setMy(int a) {my = a;}


	@Override
	public void mouseClicked(MouseEvent e) {
		cx = e.getX();
		cy = e.getY();
	}	
	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void resetClick()
	{
		setCx(0);
		setCy(0);
	}
}
