import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Player implements MouseListener, MouseMotionListener
{

	private Chess chess;
	private boolean myTurn;
	
	public Player(Chess game)
	{
		chess = game;
		myTurn = false;
	}
	
	public void takeTurn()
	{
		myTurn = true;
		while (myTurn)
		{
			try
			{
				Thread.sleep(5);
			}
			catch (InterruptedException e)
			{
				System.out.println("Could not sleep. I was interrupted.");
				e.printStackTrace();
			}
		}
		return;
	}
	
	public void mousePressed(MouseEvent event)
	{
		if (myTurn)
		{
			chess.select(event.getX(), event.getY());
			chess.repaint();
		}
	}
	
	public void mouseClicked(MouseEvent e) {}
	
	public void mouseReleased(MouseEvent e)
	{
		if (myTurn)
		{
			boolean result = chess.moveRequest();
			if (result)
			{
				chess.highlight(-1, -1);
				chess.repaint();
				myTurn = false;
			}
		}
	}
	
	public void mouseEntered(MouseEvent e) {}
		
	public void mouseExited(MouseEvent e) {}
	
	public void mouseDragged(MouseEvent e)
	{
		if (myTurn)
		{
			boolean result = chess.highlight(e.getX(), e.getY());
			if (result)
			{
				chess.repaint();
			}
		}
	}

	public void mouseMoved(MouseEvent e) {}
	
}
