import java.awt.Color;
import java.awt.Graphics;

public class Tile
{
	private Piece piece;
	private boolean selected;
	private boolean highlighted;
	private String letterCode;
	private int row;
	private int col;
	
	public Tile(String code, int r, int c)
	{
		piece = null;
		letterCode = code;
		row = r;
		col = c;
	}
	
	public Tile(Piece p, String code, int r, int c)
	{
		piece = p;
		letterCode = code;
		row = r;
		col = c;
	}
	
	public void draw(Graphics g, int minX, int minY, int width, int height)
	{
		//g.setColor((row + col) % 2 == 0? Color.decode("#FFBB22"): Color.white);
		//g.fillRect(minX, minY, width, height);
		//too harsh on the cpu
		g.setColor(Color.black);
		g.drawRect(minX, minY, width, height);
		
		
		if (selected)
		{
			g.setColor(Color.red);
			g.drawRect(minX + 1, minY + 1, width - 2, height - 2);
		}
		else if (highlighted)
		{
			g.setColor(Color.cyan);
			g.drawRect(minX + 1, minY + 1, width - 2, height - 2);
		}
		
		if (piece != null)
		{
			g.drawString(piece.getType(), minX + width / 2, minY + height / 2);
		}
	}
	
	public String getLetterCode()
	{
		return letterCode;
	}
	
	public boolean hasPiece()
	{
		return piece != null;
	}
	
	public Piece takePiece()
	{
		Piece tempPiece = piece;
		piece = null;
		return tempPiece;
	}
	
	public Piece getPiece()
	{
		return piece;
	}
	
	public void placePiece(Piece p)
	{
		piece = p;
	}
	
	public void select()
	{
		selected = true;
	}
	
	public void deselect()
	{
		selected = false;
	}
	
	public void highlight()
	{
		highlighted = true;
	}
	
	public void unhighlight()
	{
		highlighted = false;
	}
	
	public int[] getCoordinates()
	{
		return new int[]{row, col};
	}
	
	public int[] getDistance(Tile other)
	{
		return new int[]{row - other.getCoordinates()[0], col - other.getCoordinates()[1]};
	}
	
	

}
