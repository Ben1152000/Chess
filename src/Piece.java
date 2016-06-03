
public class Piece
{
	private String type;
	private boolean moved;
	
	public Piece(String t)
	{
		type = t;
		moved = false;
	}
	
	public boolean getColor()
	{
		return type.equals("♖") || type.equals("♘") || type.equals("♗") || type.equals("♕") || type.equals("♔") || type.equals("♙");
	}
	
	public String getType()
	{
		return type;
	}
	
	public boolean moved()
	{
		return moved;
	}
	
	public void wasMoved()
	{
		moved = true;
	}
	
}
