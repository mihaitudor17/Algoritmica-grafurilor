import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Arc
{
	public int color=0;
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
		int d=8;
		int h=8;
		if (start != null)
		{
			if(color==0)
				graphicsComponent.setColor(Color.BLACK);
			else
				graphicsComponent.setColor(Color.BLUE);
            graphicsComponent.drawLine(start.x, start.y, end.x, end.y);
			graphicsComponent.setColor(Color.BLACK);
			int dx = end.x - start.x, dy = end.y - start.y;
			double D = Math.sqrt(dx*dx + dy*dy);
			double xm = D - d, xn = xm, ym = h, yn = -h, x;
			double sin = dy / D, cos = dx / D;
			x = xm*cos - ym*sin + start.x;
			ym = xm*sin + ym*cos + start.y;
			xm = x;

			x = xn*cos - yn*sin + start.x;
			yn = xn*sin + yn*cos + start.y;
			xn = x;
			int[] xpoints = {end.x-1, (int) xm, (int) xn};
			int[] ypoints = {end.y-1, (int) ym, (int) yn};
			graphicsComponent.fillPolygon(xpoints, ypoints, 3);
		}
	}
}
