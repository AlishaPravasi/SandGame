package PravasiProject1;

import edu.du.dudraw.DUDraw;

enum Material {SAND, EMPTY, FLOOR, LAVA};

public class SandWorld
{
	//enum 2D array that stores the state of every pixel on the canvas 
	//(each element of the 2D array represents one pixel)
	private Material[][] currentState = new Material[300][300];
	public SandWorld()
	{
		//makes canvas
		DUDraw.setCanvasSize(300,300);
		DUDraw.setXscale(300,0);
		DUDraw.setYscale(0,300);
		DUDraw.clear(192, 235, 240);
		//sets the entire canvas to the empty state
		for(int col = 0; col < currentState.length; col++)
		{
			for(int row = 0; row < currentState[col].length; row++)
			{
				currentState[col][row] = Material.EMPTY;
			}
		}
	}
	//creates and updates button using the keys the user types
	public void displayToolName(int newType)
	{
		//sand button
		if(newType == 0)
		{
			DUDraw.setPenColor(DUDraw.WHITE);
			DUDraw.filledRectangle(240, 270, 60, 30);
			DUDraw.setPenColor(DUDraw.BLACK);
			DUDraw.text(240, 270, "SAND");
		}
		//floor button
		if(newType == 1)
		{
			DUDraw.setPenColor(DUDraw.WHITE);
			DUDraw.filledRectangle(240, 270, 60, 30);
			DUDraw.setPenColor(DUDraw.BLACK);
			DUDraw.text(240, 270, "FLOOR");
		}
		//lava button
		if(newType == 2)
		{
			DUDraw.setPenColor(DUDraw.WHITE);
			DUDraw.filledRectangle(240, 270, 60, 30);
			DUDraw.setPenColor(DUDraw.BLACK);
			DUDraw.text(240, 270, "LAVA");
		}
	}
	//contains physics for falling and lava/sand physics
	public void step()
	{ 
		double randomNumber;
		//makes sand fall from cursor
		for(int col = 0; col < currentState.length; col++)
		{
			for(int row = 3; row < currentState[col].length; row++)
			{
				if(currentState[col][row] == Material.SAND)
				{
					if(currentState[col][row-1] == Material.EMPTY)
					{
						currentState[col][row-1] = Material.SAND;
						currentState[col][row] = Material.EMPTY;
					}
				}
				//physics to let the sand pile up
				if(currentState[col][row] == Material.SAND && currentState[col][row-1] == Material.SAND)
				{
					randomNumber = Math.random();
					if(randomNumber > .5 && currentState[col-1][row-1] == Material.EMPTY)
					{
						currentState[col-1][row-1] = Material.SAND;
						currentState[col][row] = Material.EMPTY;
					}
					if(randomNumber < .5 && currentState[col+1][row-1] == Material.EMPTY)
					{
						currentState[col+1][row-1] = Material.SAND;
						currentState[col][row] = Material.EMPTY;
					}
				}
			}
		}
		//generates floor blocks at the position of the cursor
		for(int col = 0; col < currentState.length; col++)
		{
			for(int row = 1; row < currentState[col].length; row++)
			{
				if(currentState[col][row] == Material.FLOOR)
				{
					if(currentState[col][row-1] == Material.EMPTY)
					{
						currentState[col][row] = Material.FLOOR;
					}
				}
			}
		}
		//makes lava fall from cursor
		for(int col = 3; col < currentState.length - 3; col++)
		{
			for(int row = 3; row < currentState[col].length; row++)
			{
				if(currentState[col][row] == Material.LAVA)
				{
					if(currentState[col][row-1] == Material.EMPTY)
					{
						currentState[col][row-1] = Material.LAVA;
						currentState[col][row] = Material.EMPTY;	
					}
					//physics to make lava flow
					if(currentState[col][row] == Material.LAVA && currentState[col][row-1] == Material.LAVA)
					{
						if(col > -1 && col < 300 && row > -1 && row < 300 && currentState[col-1][row] == Material.EMPTY && currentState[col][row] == Material.LAVA)
						{
							currentState[col-1][row] = Material.LAVA;
							currentState[col][row] = Material.EMPTY;
						}
						if(col > -1 && col < 300 && row > -1 && row < 300 && currentState[col+1][row] == Material.EMPTY && currentState[col][row] == Material.LAVA )
						{
							currentState[col+1][row] = Material.LAVA;
							currentState[col][row] = Material.EMPTY;
						}
					}
				}
			}
		}

	}
	//adds four particles of sand at a time
	public void addSand(int x, int y)
	{
		currentState[x][y] = Material.SAND;
		currentState[x-5][y] = Material.SAND;
		currentState[x + 5][y-5] = Material.SAND;
		currentState[x-4][y-4] = Material.SAND;
	}
	//adds a floor block
	public void addFloor(int x, int y)
	{
		currentState[x][y] = Material.FLOOR;
		currentState[x+1][y] = Material.FLOOR;
		currentState[x][y+1] = Material.FLOOR;
		currentState[x+1][y+1] = Material.FLOOR;
		currentState[x-1][y] = Material.FLOOR;
		currentState[x][y-1] = Material.FLOOR;
		currentState[x-1][y-1] = Material.FLOOR;
		currentState[x+1][y-1] = Material.FLOOR;
		currentState[x-1][y+1] = Material.FLOOR;
	}
	//adds a lava stream
	public void addLava(int x, int y)
	{
		currentState[x][y] = Material.LAVA;
		currentState[x-1][y-1] = Material.LAVA;
		currentState[x][y-2] = Material.LAVA;
		currentState[x-1][y-3] = Material.LAVA;
		currentState[x][y-4] = Material.LAVA;
	}
	//sets the colors of the different states as well as refreshes the world everytime soemthing changes
	public void refreshWorld(int x, int y)
	{

		for(x = 0; x < currentState.length; x++)
		{
			for(y = 0; y < currentState[x].length; y++) 
			{
				if(currentState[x][y] == Material.SAND)
					DUDraw.setPenColor(201, 167, 111);
				if(currentState[x][y] == Material.EMPTY)
					DUDraw.setPenColor(192, 235, 240);
				if(currentState[x][y] == Material.FLOOR)
					DUDraw.setPenColor(0,0,0);
				if(currentState[x][y] == Material.LAVA)
					DUDraw.setPenColor(235, 74, 0);
			}
		}
	}
	//displays the world
	public void displayAll()
	{
		for(int col = 0; col < currentState.length; col++)
		{
			for(int row = 0; row < currentState[col].length; row++) 
			{
				
				if(currentState[col][row] == Material.SAND)
					DUDraw.setPenColor(201, 167, 111);
				if(currentState[col][row] == Material.EMPTY)
					DUDraw.setPenColor(192, 235, 240);
				if(currentState[col][row] == Material.FLOOR)
					DUDraw.setPenColor(0,0,0);
				if(currentState[col][row] == Material.LAVA)
					DUDraw.setPenColor(235, 74, 0);
				DUDraw.filledRectangle(col, row, 1, 1);
			}
		}
	}
}
