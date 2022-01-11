import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Arc
{
	public int startNodeIndex;
	public int endNodeIndex;
	
	public Point start;
	public Point end;
	
	public Arc(Point start, Point end)
	{
		this.start = start;
		this.end = end;
	}
	
	public void DrawArc(Graphics graphicsComponent)
	{
		if (start != null)
		{
            graphicsComponent.setColor(Color.BLACK);
            graphicsComponent.drawLine(start.x, start.y, end.x, end.y);
        }
	}
}
