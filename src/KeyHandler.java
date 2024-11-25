import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	public boolean rPr, dPr, entPr;
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code==KeyEvent.VK_D) {dPr = true;}
		if(code==KeyEvent.VK_R) {rPr = true;}
		if(code==KeyEvent.VK_ENTER) {entPr = true;}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		if(code==KeyEvent.VK_D) {dPr = false;}
		if(code==KeyEvent.VK_R) {rPr = false;}
		if(code==KeyEvent.VK_ENTER) {entPr = false;}

	}
}
