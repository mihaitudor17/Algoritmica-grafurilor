import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Node
{
	public int coordX;
	public int coordY;
	public int number;
	
	public Node(int coordX, int coordY, int number)
	{
		this.coordX = coordX;
		this.coordY = coordY;
		this.number = number;
	}

	public void DrawNode(Graphics graphicsComponent, int node_diam)
	{
		graphicsComponent.setFont(new Font("TimesRoman", Font.BOLD, 15));
        graphicsComponent.fillOval(coordX, coordY, node_diam, node_diam);
        graphicsComponent.setColor(Color.WHITE);
        graphicsComponent.drawOval(coordX, coordY, node_diam, node_diam);
        if(number < 10)
        	graphicsComponent.drawString(((Integer)number).toString(), coordX+13, coordY+20);
        else
        	graphicsComponent.drawString(((Integer)number).toString(), coordX+8, coordY+20);	
	}
	
	//Used to check if the node we want to instantiate is too close to this node
	public boolean IsTooClose(int x, int y, int diam)
	{
		int xResult = coordX - x;
		if(xResult < 0) xResult *= -1;
		
		int yResult = coordY - y;
		if(yResult < 0) yResult *= -1;
		
		if(xResult < diam && yResult < diam)
			return true;
		return false;
	}
}
