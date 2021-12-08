import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class Node
{
    private int coordX;
    private int coordY;
    private int number;
    private int color;
    public Node(int coordX, int coordY, int number)
    {
        this.coordX = coordX;
        this.coordY = coordY;
        this.number = number;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
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

    public void drawNode(Graphics g, int node_diam) {
        switch(color){
            case 1:
                g.setColor(Color.GREEN);
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
            default:
                g.setColor(Color.YELLOW);
                break;
        }
        g.setFont(new Font("TimesRoman", Font.BOLD, 15));
        g.fillOval(coordX - node_diam / 2, coordY - node_diam / 2, node_diam, node_diam);
        g.setColor(Color.BLACK);
        g.drawOval(coordX - node_diam / 2, coordY - node_diam / 2, node_diam, node_diam);
        if (number < 10)
            g.drawString(((Integer) number).toString(), coordX - node_diam / 2 + 13, coordY - node_diam / 2 + 20);
        else
            g.drawString(((Integer) number).toString(), coordX - node_diam / 2 + 8, coordY - node_diam / 2 + 20);
    }
}
