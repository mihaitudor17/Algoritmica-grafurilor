import java.awt.Color;

import java.awt.Graphics;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class MyPannel extends JPanel {
	
	private Graph myGraph = new Graph();
	
	public MyPannel()
	{
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		myGraph.ReadData();
		myGraph.GenerateGraph();
		myGraph.FindMaxFlow();
		repaint();
	}
	
	//called when we call repaint()
	protected void paintComponent(Graphics graphicsComponent)
	{
		super.paintComponent(graphicsComponent);	//call the method from the base class
		graphicsComponent.drawString("This is my Graph!", 10, 20);

		myGraph.DrawGraph(graphicsComponent);
	}
}
