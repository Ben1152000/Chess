import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;


public class Chess extends JFrame
{
	private Board board;
	private Player player;
	
	// Render dimensions:
	private final int FRAME_X = 10;
	private final int FRAME_Y = 32;
	private final int FRAME_WIDTH = 400;
	private final int FRAME_HEIGHT = 400;
	private final int TOP_OF_SCREEN = 22;
	private final int SCREEN_WIDTH = FRAME_HEIGHT + 20;
	private final int SCREEN_HEIGHT = FRAME_HEIGHT + TOP_OF_SCREEN + 20;

	public Chess()
	{
		player = new Player(this);
		board = new Board();
		
		// Pre-Render:
		this.addMouseListener(player);
		this.addMouseMotionListener(player);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		
		this.setVisible(true); // first render
		
		boolean gameWon = false;
		boolean playerTurn = true;
		while (!gameWon)
		{
			if (playerTurn)
			{
				// Player makes a move request
				// If it doesnt work, try again
				player.takeTurn();
			}
			else
			{
				// AI makes a move
				AI.takeTurn(board);
			}
			
			// set GameWon
			
			playerTurn = !playerTurn;
		}
	}

	public static void main(String[] args) 
	{
		JFrame frame = new Chess();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void update(Graphics g)
	{
		paint(g);
	}
	
	public void paint(Graphics g) 
	{
		// Clear the window.
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		board.draw(g, FRAME_X, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT);
	}
	
	
	
	
	public boolean moveRequest()
	{
		return board.moveRequest();
	}
	
	public void select(int x, int y)
	{
		if (x > FRAME_X && y > FRAME_Y && x < FRAME_X + FRAME_WIDTH && y < FRAME_Y + FRAME_HEIGHT)
		{
			int col = (int) (((double) (x - FRAME_X) / (double) FRAME_WIDTH) * 8);
			int row = (int) (((double) (y - FRAME_Y) / (double) FRAME_HEIGHT) * 8);
			board.select(row, col);
		}
		else
		{
			board.select(-1, -1);
		}
	}
	
	public boolean highlight(int x, int y)
	{
		if (x > FRAME_X && y > FRAME_Y && x < FRAME_X + FRAME_WIDTH && y < FRAME_Y + FRAME_HEIGHT)
		{
			int col = (int) (((double) (x - FRAME_X) / (double) FRAME_WIDTH) * 8);
			int row = (int) (((double) (y - FRAME_Y) / (double) FRAME_HEIGHT) * 8);
			boolean result = board.highlight(row, col);
			return result;
		}
		else
		{
			boolean result = board.highlight(-1, -1);
			return result;
		}
	}
	
}