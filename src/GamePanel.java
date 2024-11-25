import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.swing.JLabel;


public class GamePanel extends JPanel implements Runnable {
	final int originalTileSize = 16;
	final double scale = 3;

	final int tileSize = (int)(originalTileSize*scale);
	final int maxScreenCol = 10;
	final int maxScreenRow = 10;
	final int screenWidth = tileSize*maxScreenCol;
	final int screenHeight = tileSize*maxScreenRow;
	boolean grid = true;
    public boolean[] gameStatus = new boolean[3];
	int FPS = 60;
	
	KeyHandler keyH = new KeyHandler();
	MouseHandler mH = new MouseHandler();
	
	Board b = new Board(this);
	
	Thread gameThread;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.addMouseListener(mH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;
		while(gameThread!=null){
			update();
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime/=1000000;
				
				if(remainingTime<0) remainingTime = 0;
				
				Thread.sleep((long)remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update()
	{
		if(!(gameStatus[0]||gameStatus[1]||gameStatus[2])) b.update();
		else
		{
			if(keyH.entPr) 
			{
				b.reset();
				for(int i = 0; i < 3; i++)
				{
					gameStatus[i] = false;
				}
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(75, 37, 0));
		g2.fillRect(0, 0, screenWidth, screenHeight);
		g2.setColor(new Color(255,255,255, 20));
		g2.fillRect(tileSize/8, tileSize/8, screenWidth-tileSize/4, screenHeight-tileSize/4);
		g2.fillRect(tileSize/4, tileSize/4, screenWidth-tileSize/2, screenHeight-tileSize/2);
		g2.fillRect(tileSize/2, tileSize/2, screenWidth-tileSize, screenHeight-tileSize);
		b.paint(g2);
		if(gameStatus[0]||gameStatus[2])
		{
			g2.setColor(new Color(gameStatus[0]? 255:0, gameStatus[0]? 255:0, gameStatus[0]? 255:0, 200));
			g2.fillRect(0, 0, screenWidth, screenHeight);
			Text.setColor(gameStatus[0]? new Color(0, 0, 0): new Color(255, 255, 255));
			Text.drawText(gameStatus[0]? "White":"Black", "Won", g2, tileSize);
		}
		else if(gameStatus[1])
		{
			g2.setColor(new Color(gameStatus[0]? 255:0, gameStatus[0]? 255:0, gameStatus[0]? 255:0, 200));
			g2.fillRect(0, 0, screenWidth, screenHeight);
			Text.setColor(Color.GRAY);
			Text.drawText("Draw", g2, tileSize);
		}
		g2.dispose();
	}
}

