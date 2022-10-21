package PravasiProject1;

import edu.du.dudraw.DUDraw;

public class SandGame {

	public static void main(String[] args) 
	{
		//links the classes
		SandWorld obj1 = new SandWorld();
		//used later to keep track of the state of the game
		int material = 0;
		DUDraw.enableDoubleBuffering();
		//animation loop
		while (true)
		{
			//variable to make the animation quicker
			int clicked = DUDraw.getMousePressed();
			DUDraw.clear();
			//animates the particles falling from cursor and their physics
			obj1.step();
			//generates new particles depending on the state of the game
			if(clicked > 0 || DUDraw.isMousePressed())
			{
				if(material == 0)
					obj1.addSand((int)DUDraw.mouseX(), (int)DUDraw.mouseY());
				if(material == 1)
					obj1.addFloor((int)DUDraw.mouseX(), (int)DUDraw.mouseY());
				if(material == 2)
					obj1.addLava((int)DUDraw.mouseX(), (int)DUDraw.mouseY());
				obj1.refreshWorld((int)DUDraw.mouseX(), (int)DUDraw.mouseY());
			}
			obj1.displayAll();
			//checks if the user wants to change the state of the game and updates accordingly
			if(DUDraw.isKeyPressed('F'))
			{
				material = 1;
			}
			if(DUDraw.isKeyPressed('S'))
			{
				material = 0;
			}
			if(DUDraw.isKeyPressed('L'))
			{
				material = 2;			
			}
			obj1.displayToolName(material);
			DUDraw.show();
			DUDraw.pause(5);
		}
	}

}
