import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.*;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
public class MyPanel extends JPanel {
	private int nodeNr = 1;
	private int node_diam = 30;
	private Vector<Node> listaNoduri;
	private Vector<Arc> listaArce;
	Point pointStart=null;
	Point pointEnd=null;
	public MyPanel()
	{
		System.out.println("Introduceti numarul de noduri:");
		int nodeNumber;
		Scanner S=new Scanner(System.in);
		nodeNumber=S.nextInt();
		System.out.println("Introduceti probabilitatea arcelor:");
		float prob;
		prob=S.nextFloat();
		ArrayList<Integer> randx=new ArrayList<>();
		ArrayList<Integer> randy=new ArrayList<>();
		for(int i=0;i<1010;i++)
		{randx.add(i);
		randy.add(i);}
		for(int i=1010;i<1870;i++)
		{
			randx.add(i);
		}
		Collections.shuffle(randx);
		Collections.shuffle(randy);
		listaNoduri = new Vector<Node>();
		listaArce = new Vector<Arc>();
		addNode(randx.get(0),randy.get(0));
		setBorder(BorderFactory.createLineBorder(Color.black));
		int i1=0;
		int i2=1;
		if(nodeNumber>1000)
			node_diam/=(nodeNumber/1000);
		int ok=0;
		for(int i=1;i<nodeNumber;i++)
		{

				for (int j = 0; j < listaNoduri.size(); j++)
				{
					int x1 = listaNoduri.get(j).getCoordX() ;
					int y1 = listaNoduri.get(j).getCoordY() ;
					int x = (randx.get(i1)  - x1);
					int y = (randy.get(i2)  - y1);
					if (x * x + y * y < node_diam * node_diam)
					{
						if(i1<randx.size()-1&&i2<randy.size()-1)
						{
							i1++;
							i2++;
						}
						else
							if(i1<randx.size()-1)
							{
								i1++;
								i2=0;
							}
							else
								if(i2<randy.size()-1)
								{
									i1=0;
									i2++;
								}
								else
								{
								i1=0;
								i2=0;
								}
						j=0;
					}
				}

			addNode(randx.get(i1),randy.get(i2));
			for(int k=listaNoduri.size()-2;k>=0;k--)
			{
				float rand_float=(float)(Math.random()*100);
				if(rand_float<prob)
				{
					System.out.println(rand_float);
					ok++;
					pointStart=new Point();
					pointStart.setLocation(listaNoduri.get(k).getCoordX()+node_diam/2,listaNoduri.get(k).getCoordY()+node_diam/2);
					pointEnd=new Point();
					pointEnd.setLocation(listaNoduri.get(listaNoduri.size()-1).getCoordX()+node_diam/2,listaNoduri.get(listaNoduri.size()-1).getCoordY()+node_diam/2);
					Arc arc=new Arc(pointStart,pointEnd);
					listaArce.add(arc);
				}
			}
		}
		System.out.println(ok);
	}
	private void addNode(int x, int y) {
		Node node = new Node(x, y, nodeNr);
		listaNoduri.add(node);
		nodeNr++;
		repaint();
	}
	
	//se executa atunci cand apelam repaint()
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);//apelez metoda paintComponent din clasa de baza
		for (Arc a : listaArce)
		{
			a.drawArc(g);
		}
		//deseneaza arcul curent; cel care e in curs de desenare
		if (pointStart != null)
		{
			g.setColor(Color.BLACK);
			g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
		}
		//deseneaza lista de noduri
		for(int i=0; i<listaNoduri.size(); i++)
		{
			listaNoduri.elementAt(i).drawNode(g, node_diam);
		}
	}
}
