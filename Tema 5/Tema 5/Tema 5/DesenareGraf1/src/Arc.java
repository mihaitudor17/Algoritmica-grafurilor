import java.awt.*;

public class Arc
{
	private Point start;
	private Point end;
	public Point getStart() {
		return start ;
	}
	public Point getEnd() {
		return end ;
	}
	public void setStart(int coordx, int coordy){start.x=coordx;start.y=coordy;}
	public void setEnd(int coordx, int coordy){end.x=coordx;end.y=coordy;}
	public Arc(Point start, Point end)
	{
		this.start = start;
		this.end = end;
	}
	
	public void drawArc(Graphics g)
	{
		if (start != null)
		{
            g.setColor(Color.BLACK);
            g.drawLine(start.x, start.y, end.x, end.y);
        }
	}
	public void drawArrowHead(Graphics g, int d, int h) {
		int x2=end.x;int x1=start.x;int y2= end.y;int y1= start.y;
		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx*dx + dy*dy);
		double xm = D - d, xn = xm, ym = h, yn = -h, x;
		double sin = dy / D, cos = dx / D;

		x = xm*cos - ym*sin + x1;
		ym = xm*sin + ym*cos + y1;
		xm = x;

		x = xn*cos - yn*sin + x1;
		yn = xn*sin + yn*cos + y1;
		xn = x;

		int[] xpoints = {x2, (int) xm, (int) xn};
		int[] ypoints = {y2, (int) ym, (int) yn};

		g.drawLine(x1, y1, x2, y2);
		g.fillPolygon(xpoints, ypoints, 3);
	}
}
