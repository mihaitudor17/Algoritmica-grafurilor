import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Arc
{
    private Point2D.Double start;
    private Point2D.Double end;
    private int weight;
    private int color;
    public Point2D.Double getStart() {
        return start ;
    }
    public Point2D.Double getEnd() {
        return end ;
    }
    public void setStart(int coordx, int coordy){start.x=coordx;start.y=coordy;}
    public void setEnd(int coordx, int coordy){end.x=coordx;end.y=coordy;}
    public void setColor(int Color){
        color=Color;
    }
    public Arc(Point2D.Double start, Point2D.Double end,int Weight)
    {
        this.start = start;
        this.end = end;
        weight=Weight;
        color=0;
    }

    public void drawArc(Graphics g)
    {
        if (start != null)
        {
            if(color==0)
                g.setColor(Color.BLACK);
            else
                g.setColor(Color.RED);
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(new Line2D.Double(start.x,start.y, end.x, end.y));
        }
    }
}
