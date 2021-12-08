import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class Node
{
	private int coordX;
	private int coordY;
	private int number;
	private boolean visited;
	public Node(int coordX, int coordY, int number)
	{
		this.coordX = coordX;
		this.coordY = coordY;
		this.number = number;
		visited=false;
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
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public void setVisited(boolean temp)
	{
		visited=temp;
	}
	public boolean getVisited()
	{
		return visited;
	}
	public void drawNode(Graphics g, int node_diam,boolean tipDesenare) {
		if(tipDesenare==false)
		{g.setColor(Color.YELLOW);
		g.setFont(new Font("TimesRoman", Font.BOLD, 15));
		g.fillOval(coordX - node_diam / 2, coordY - node_diam / 2, node_diam, node_diam);
		g.setColor(Color.BLACK);
		g.drawOval(coordX - node_diam / 2, coordY - node_diam / 2, node_diam, node_diam);
		if (number < 10)
			g.drawString(((Integer) number).toString(), coordX - node_diam / 2 + 13, coordY - node_diam / 2 + 20);
		else
			g.drawString(((Integer) number).toString(), coordX - node_diam / 2 + 8, coordY - node_diam / 2 + 20);
	} else{
	g.setColor(Color.RED);
	int fact=30/node_diam;
	g.setFont(new Font("TimesRoman", Font.BOLD, 15/fact));
	g.fillOval(coordX, coordY, node_diam, node_diam);
	g.setColor(Color.WHITE);
	g.drawOval(coordX, coordY, node_diam, node_diam);
	if(number < 10)
		g.drawString(((Integer)number).toString(), coordX+13, coordY+20);
	else
		g.drawString(((Integer)number).toString(), coordX+8, coordY+20);
}
}
}
