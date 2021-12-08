import java.awt.*;

public class Arc
{
    private Point start;
    private Point end;
    private int weight;
    private int color;
    public Point getStart() {
        return start ;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public Point getEnd() {
        return end ;
    }
    public void setStart(int coordx, int coordy){start.x=coordx;start.y=coordy;}
    public void setEnd(int coordx, int coordy){end.x=coordx;end.y=coordy;}
    public Arc(Point start, Point end,int weight)
    {
        this.start = start;
        this.end = end;
        this.weight=weight;
    }

    public void drawArc(Graphics g)
    {

        if (start != null)
        {
            switch(color) {
                case 1:
                    g.setColor(Color.BLACK);
                    break;
                case 2:
                    g.setColor(Color.RED);
                    break;
                case 3:
                    g.setColor(Color.BLUE);
                    break;
                case 4:
                    g.setColor(Color.CYAN);
                    break;
                case 5:
                    g.setColor(Color.MAGENTA);
                    break;
            }
            g.drawLine(start.x, start.y, end.x, end.y);
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.BOLD, 15));
            g.drawString(((Integer) weight).toString(), (start.x + end.x) / 2+13 , (start.y + end.y)/2);
        }
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
