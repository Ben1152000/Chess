import java.awt.Graphics;

public class Board
{
	// DATA:
	private Tile[][] tiles;
	private Tile selected;
	private Tile highlighted;
	
	private static String[][] startBoard = {
			{"♜", "♞", "♝", "♛", "♚", "♝", "♞", "♜"},
			{"♟", "♟", "♟", "♟", "♟", "♟", "♟", "♟"},
			{ "",   "",   "",  "",   "",  "",   "",   ""},
			{ "",   "",   "",  "",   "",  "",   "",   ""},
			{ "",   "",   "",  "",   "",  "",   "",   ""},
			{ "",   "",   "",  "",   "",  "",   "",   ""},
			{"♙", "♙", "♙", "♙", "♙", "♙", "♙", "♙"},
			{"♖", "♘", "♗", "♕", "♔", "♗", "♘", "♖"}
				};
	
	// METHODS:
	
	private static String[][] letterCodes = {
			{"A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8"},
			{"A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7"},
			{"A6", "B6", "C6", "D6", "E6", "F6", "G6", "H6"},
			{"A5", "B5", "C5", "D5", "E5", "F5", "G5", "H5"},
			{"A4", "B4", "C4", "D4", "E4", "F4", "G4", "H4"},
			{"A3", "B3", "C3", "D3", "E3", "F3", "G3", "H3"},
			{"A2", "B2", "C2", "D2", "E2", "F2", "G2", "H2"},
			{"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1"}
				};
	
	public Board()
	{
		tiles = new Tile[8][8];
		for (int row = 0; row < 8; row++)
		{
			for (int col = 0; col < 8; col++)
			{
				if (startBoard[row][col].equals(new String()))
				{
					tiles[row][col] = new Tile(letterCodes[row][col], row, col);
				}
				else
				{
					tiles[row][col] = new Tile(new Piece(startBoard[row][col]), letterCodes[row][col], row, col);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param start Tile which starts the move
	 * @param end Target of the move
	 * @return
	 */
	public boolean isLegalMove(Tile start, Tile end)
	{
		if (!start.hasPiece() || start.getPiece().getColor() != true) // if not moving a white piece
		{
			return false;
		}
		if (end.hasPiece()) // if the move is an attack or castle
		{
			if (end.getPiece().getColor() == true) // possible castle
			{
				boolean startIsKing = start.getPiece().getType().equals("♔");
				boolean startIsRook = start.getPiece().getType().equals("♖");
				boolean endIsKing = end.getPiece().getType().equals("♔");
				boolean endIsRook = end.getPiece().getType().equals("♖");
				if ((startIsKing && endIsRook) || (startIsRook && endIsKing)) // if the move is an actual castle
				{
					if (!(start.getPiece().moved() || end.getPiece().moved())) // if not moved before
					{
						return true;
					}
					else // pieces have moved before
					{
						return false;
					}
				}
				else // pieces are not king and rook
				{
					return false;
				}
				
			}
			else // move is an attack
			{
				if (start.getPiece().getType().equals("♙"))
				{
					if ((start.getDistance(end)[0] == 1 && start.getDistance(end)[1] == 1) || (start.getDistance(end)[0] == 1 && start.getDistance(end)[1] == -1))
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else if (start.getPiece().getType().equals("♘"))
				{
					if ((Math.abs(start.getDistance(end)[0]) == 2 && Math.abs(start.getDistance(end)[1]) == 1) || (Math.abs(start.getDistance(end)[0]) == 1 && Math.abs(start.getDistance(end)[1]) == 2))
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else if (start.getPiece().getType().equals("♔"))
				{
					if ((Math.abs(start.getDistance(end)[0]) <= 1 && Math.abs(start.getDistance(end)[1]) <= 1))
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else if (start.getPiece().getType().equals("♖"))
				{
					if (start.getCoordinates()[0] == end.getCoordinates()[0] || start.getCoordinates()[1] == end.getCoordinates()[1])
					{
						if (lineOfSight(start, end))
						{
							return true;
						}
						else
						{
							return false;
						}
					}
					else
					{
						return false;
					}
				}
				else if (start.getPiece().getType().equals("♗"))
				{
					if (start.getCoordinates()[1] - end.getCoordinates()[1] == start.getCoordinates()[0] - end.getCoordinates()[0] || start.getCoordinates()[1] - end.getCoordinates()[1] == - start.getCoordinates()[0] + end.getCoordinates()[0])
					{
						if (lineOfSight(start, end))
						{
							return true;
						}
						else
						{
							return false;
						}
					}
					else
					{
						return false;
					}
				}
				else if (start.getPiece().getType().equals("♕"))
				{
					if (start.getCoordinates()[0] == end.getCoordinates()[0] || start.getCoordinates()[1] == end.getCoordinates()[1])
					{
						if (lineOfSight(start, end))
						{
							return true;
						}
						else
						{
							return false;
						}
					}
					if (start.getCoordinates()[1] - end.getCoordinates()[1] == start.getCoordinates()[0] - end.getCoordinates()[0] || start.getCoordinates()[1] - end.getCoordinates()[1] == - start.getCoordinates()[0] + end.getCoordinates()[0])
					{
						if (lineOfSight(start, end))
						{
							return true;
						}
						else
						{
							return false;
						}
					}
					else
					{
						return false;
					}
				}
				else
				{
					return true;
				}
			}
		}
		else // if the move is not an attack or castle
		{
			if (start.getPiece().getType().equals("♙"))
			{
				if (start.getPiece().moved())
				{
					if (start.getDistance(end)[0] == 1 && start.getDistance(end)[1] == 0)
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else
				{
					if ((start.getDistance(end)[0] == 1 || start.getDistance(end)[0] == 2) && start.getDistance(end)[1] == 0)
					{
						return true;
					}
					else
					{
						return false;
					}
				}
			}
			else if (start.getPiece().getType().equals("♘"))
			{
				if ((Math.abs(start.getDistance(end)[0]) == 2 && Math.abs(start.getDistance(end)[1]) == 1) || (Math.abs(start.getDistance(end)[0]) == 1 && Math.abs(start.getDistance(end)[1]) == 2))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else if (start.getPiece().getType().equals("♔"))
			{
				if ((Math.abs(start.getDistance(end)[0]) <= 1 && Math.abs(start.getDistance(end)[1]) <= 1))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else if (start.getPiece().getType().equals("♖"))
			{
				if (start.getCoordinates()[0] == end.getCoordinates()[0] || start.getCoordinates()[1] == end.getCoordinates()[1])
				{
					if (lineOfSight(start, end))
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else
				{
					return false;
				}
			}
			else if (start.getPiece().getType().equals("♗"))
			{
				if (start.getCoordinates()[1] - end.getCoordinates()[1] == start.getCoordinates()[0] - end.getCoordinates()[0] || start.getCoordinates()[1] - end.getCoordinates()[1] == - start.getCoordinates()[0] + end.getCoordinates()[0])
				{
					if (lineOfSight(start, end))
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else
				{
					return false;
				}
			}
			else if (start.getPiece().getType().equals("♕"))
			{
				if (start.getCoordinates()[0] == end.getCoordinates()[0] || start.getCoordinates()[1] == end.getCoordinates()[1])
				{
					if (lineOfSight(start, end))
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				if (start.getCoordinates()[1] - end.getCoordinates()[1] == start.getCoordinates()[0] - end.getCoordinates()[0] || start.getCoordinates()[1] - end.getCoordinates()[1] == - start.getCoordinates()[0] + end.getCoordinates()[0])
				{
					if (lineOfSight(start, end))
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else
				{
					return false;
				}
			}
			else
			{
				System.out.println(lineOfSight(start, end));
				return true;
			}
		}
		//return true;
	}
	
	public boolean moveRequest()
	{
		if (selected != highlighted && selected != null && highlighted != null) // If both selected and highlighted are valid
		{
			//System.out.println(selected.getDistance(highlighted)[0] + " " + selected.getDistance(highlighted)[1]);
			if (isLegalMove(selected, highlighted)) // If the move is legal
			{
				Piece sPiece = selected.takePiece();
				Piece hPiece = highlighted.takePiece();
				if (sPiece != null)
				{
					sPiece.wasMoved();
				}
				if (hPiece != null)
				{
					hPiece.wasMoved();
				}
				if (sPiece != null && hPiece != null && sPiece.getColor() == hPiece.getColor())
				{
					selected.placePiece(hPiece);
				}
				highlighted.placePiece(sPiece);
				return true;
			}
			else
			{
				return false; // The move is illegal
			}
		}
		else
		{
			return false; // The move is legal
		}
	}
	
	public boolean lineOfSight(Tile piece, Tile other)
	{
		int[] pieceCoor = piece.getCoordinates();
		int[] otherCoor = other.getCoordinates();
		if (pieceCoor[0] == otherCoor[0])
		{
			for (int c = Math.min(pieceCoor[1], otherCoor[1]) + 1; c < Math.max(pieceCoor[1], otherCoor[1]); c++)
			{
				if (tiles[pieceCoor[0]][c].hasPiece())
				{
					return false;
				}
			}
			return true;
		}
		else if (pieceCoor[1] == otherCoor[1])
		{
			for (int r = Math.min(pieceCoor[0], otherCoor[0]) + 1; r < Math.max(pieceCoor[0], otherCoor[0]); r++)
			{
				if (tiles[r][pieceCoor[1]].hasPiece())
				{
					return false;
				}
			}
			return true;
		}
		else if (pieceCoor[1] - otherCoor[1] == pieceCoor[0] - otherCoor[0] || pieceCoor[1] - otherCoor[1] == - pieceCoor[0] + otherCoor[0])
		{
			boolean positive = !(pieceCoor[0] - otherCoor[0] > 0 ^ pieceCoor[1] - otherCoor[1] > 0);
			int minRow = Math.min(pieceCoor[0], otherCoor[0]);
			int maxRow = Math.max(pieceCoor[0], otherCoor[0]);
			int minCol = pieceCoor[0] > otherCoor[0]? otherCoor[1]: pieceCoor[1];
			int maxCol = pieceCoor[0] > otherCoor[0]? pieceCoor[1]: otherCoor[1];
			if (positive)
			{
				int c = minCol + 1;
				for (int r = minRow + 1; r < maxRow; r++, c++)
				{
					if (tiles[r][c].hasPiece())
					{
						return false;
					}
				}
			}
			else
			{
				int c = minCol - 1;
				for (int r = minRow + 1; r < maxRow; r++, c--)
				{
					if (tiles[r][c].hasPiece())
					{
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	
	public void draw(Graphics g, int minX, int minY, int width, int height)
	{
		for (int row = 0; row < tiles.length; row++)
		{
			for (int col = 0; col < tiles[row].length; col++)
			{
				int x = (int) (minX + ((double) col / (double) tiles.length) * width);
				int y = (int) (minY + ((double) row / (double) tiles[0].length) * height);
				int w = width / 8;
				int h = height / 8;
				
				tiles[row][col].draw(g, x, y, w, h);
			}
		}
	}
	
	public void select(int row, int col)
	{
		if (selected == null)
		{
			if (row != -1 && col != -1)
			{
				selected = tiles[row][col];
				selected.select();
			}
		}
		
		else 
		{
			if (row == -1 && col == -1)
			{
				selected.deselect();
				selected = null;
			}
			else
			{
				selected.deselect();
				selected = tiles[row][col];
				selected.select();
			}
		}
	}
	
	public boolean highlight(int row, int col)
	{
		if (selected != null)
		{
			if (highlighted == null)
			{
				if (row != -1 && col != -1)
				{
					highlighted = tiles[row][col];
					if (isLegalMove(selected, highlighted))
					{
						highlighted.highlight();
					}
					else
					{
						highlighted = null;
						return false;
					}
				}
				else
				{
					return false;
				}
			}
			else 
			{
				if (row == -1 && col == -1)
				{
					highlighted.unhighlight();
					highlighted = null;
				}
				else
				{
					highlighted.unhighlight();
					highlighted = tiles[row][col];
					if (isLegalMove(selected, highlighted))
					{
						highlighted.highlight();
					}
					else
					{
						highlighted = null;
					}
				}
				
			}
		}
		else
		{
			return false;
		}
		return true;
	}
	
}
