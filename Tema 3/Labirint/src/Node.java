import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Node
{
	private int coordX;
	private int coordY;
	private int color;
	
	public Node(int coordX, int coordY,int color)
	{
		this.coordX = coordX;
		this.coordY = coordY;
		this.color=color;
	}
	
	public int getCoordX() {
		return coordX;
	}
	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}
	public int getCoordY() {
		return coordY;
	}
	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	public void drawNode(Graphics g, int node_diam)
	{
		switch (color) {
			case 0:
				g.setColor(Color.BLACK);
				break;
			case 1:
				g.setColor(Color.LIGHT_GRAY);
				break;
			case 2:
				g.setColor(Color.BLUE);
				break;
			case 3:
				g.setColor(Color.RED);
				break;
			case 4:
				g.setColor(Color.GREEN);
				break;
		}
        g.fillOval(coordX, coordY, node_diam, node_diam);
        g.drawOval(coordX, coordY, node_diam, node_diam);
	}
}
