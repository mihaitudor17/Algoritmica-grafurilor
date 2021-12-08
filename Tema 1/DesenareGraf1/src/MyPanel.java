import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
public class MyPanel extends JPanel {
	private int nodeNr = 1;
	private int node_diam = 30;
	private Vector<Node> listaNoduri;
	private Vector<Arc> listaArce;
	Point pointStart = null;
	Point pointEnd = null;
	Point pointBegin=null;
	boolean isDragging = false;
	int i1,i2,i3=0;
	boolean k=true;
	boolean orientat =false;
	public MyPanel()
	{
		JButton button = new JButton("Graf orientat");

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				//Do what you like here after button is clicked, for example:
				if(k)
				{orientat=true;
				button.setOpaque(false);
				button.setContentAreaFilled(false);
				button.setBorderPainted(false);
				button.setText("Ati ales graf orientat!");
				}

			}
		});
		add(button);
		listaNoduri = new Vector<Node>();
		listaArce = new Vector<Arc>();
		ArrayList<ArrayList<Integer>> matrice= new ArrayList<>();
		matrice.add(new ArrayList());
		// borderul panel-ului
		setBorder(BorderFactory.createLineBorder(Color.black));
		addMouseListener(new MouseAdapter() {
			//evenimentul care se produce la apasarea mousse-ului
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1)
				pointStart = e.getPoint();
				else
					pointBegin=e.getPoint();
			}
			
			//evenimentul care se produce la eliberarea mousse-ului
			public void mouseReleased(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				if (!isDragging) {
					if (i3 == 0) {
						addNode(e.getX(), e.getY());
						matrice.add(new ArrayList());
						i3++;
						if(orientat==false)
						{
							k=false;
							button.setOpaque(false);
							button.setContentAreaFilled(false);
							button.setBorderPainted(false);
							button.setText("Ati ales graf neorientat!");
						}
					} else {
						boolean ok = false;
						for (int i = 0; i < listaNoduri.size(); i++) {
							double x1 = (double) listaNoduri.get(i).getCoordX();
							double y1 = (double) listaNoduri.get(i).getCoordY();
							double x = (double) (e.getX() - x1);
							double y = (double) (e.getY() - y1);
							if (x * x + y * y < node_diam * node_diam)
								ok = true;
						}
						if (!ok) {
							addNode(e.getX(), e.getY());
							matrice.add(new ArrayList());
						}
					}
				} else {
					boolean ok1 = false, ok2 = false;
					for (int i = 0; i < listaNoduri.size(); i++) {
						double x1 = (double) listaNoduri.get(i).getCoordX();
						double y1 = (double) listaNoduri.get(i).getCoordY();
						double x = (double) (pointStart.getX() - x1);
						double y = (double) (pointStart.getY() - y1);
						if (x * x + y * y < node_diam * node_diam / 4) {
							ok1 = true;
							i1 = i;
							i = listaNoduri.size();
						}
					}
					for (int i = 0; i < listaNoduri.size(); i++) {
						double x1 = (double) listaNoduri.get(i).getCoordX();
						double y1 = (double) listaNoduri.get(i).getCoordY();
						double x = (double) (pointEnd.getX() - x1);
						double y = (double) (pointEnd.getY() - y1);
						if (x * x + y * y < node_diam * node_diam / 4) {
							ok2 = true;
							i2 = i;
							i = listaNoduri.size();
						}
					}
					if (ok1 && ok2 && i2 != i1) {
						Arc arc = new Arc(pointStart, pointEnd);
						listaArce.add(arc);
						if (orientat) {
							matrice.get(i1 + 1).add(i2 + 1);
						} else {
							matrice.get(i1 + 1).add(i2 + 1);
							matrice.get(i2 + 1).add(i1 + 1);
						}
					}

				repaint();}
			}
				else
			{
				if(isDragging)
				{boolean ok=false;
					for (int i = 0; i < listaNoduri.size(); i++) {
						double x1 = (double) listaNoduri.get(i).getCoordX();
						double y1 = (double) listaNoduri.get(i).getCoordY();
						double x = (double) (pointEnd.x - x1);
						double y = (double) (pointEnd.y - y1);
						if (x * x + y * y < node_diam * node_diam)
							ok = true;
					}
					if (!ok) {
						for (int i = 0; i < listaNoduri.size(); i++) {
							double x1 = (double) listaNoduri.get(i).getCoordX();
							double y1 = (double) listaNoduri.get(i).getCoordY();
							double x = (double) (pointBegin.getX() - x1);
							double y = (double) (pointBegin.getY() - y1);
							if (x * x + y * y < node_diam * node_diam / 4) {
								for (int j = 0; j < listaArce.size(); j++) {
									Point a = listaArce.get(j).getStart();
									x1 = (double) listaNoduri.get(i).getCoordX();
									y1 = (double) listaNoduri.get(i).getCoordY();
									x = (double) (a.x - x1);
									y = (double) (a.y - y1);
									if (x * x + y * y < node_diam * node_diam) {
										listaArce.get(j).setStart(pointEnd.x, pointEnd.y);
									} else {
										a = listaArce.get(j).getEnd();
										x1 = (double) listaNoduri.get(i).getCoordX();
										y1 = (double) listaNoduri.get(i).getCoordY();
										x = (double) (a.x - x1);
										y = (double) (a.y - y1);
										if (x * x + y * y < node_diam * node_diam) {
											listaArce.get(j).setEnd(pointEnd.x, pointEnd.y);
										}
									}
								}
								listaNoduri.get(i).setCoordX(pointEnd.x);
								listaNoduri.get(i).setCoordY(pointEnd.y);
								i = listaNoduri.size();
							}
						}
					}
				}
				repaint();
			}
				pointStart = null;
				isDragging = false;
				try {
					File myObj = new File("outputJava.txt");
					myObj.createNewFile();
				} catch (IOException g) {
					System.out.println("An error occurred.");
					g.printStackTrace();
				}
				try {
					FileWriter myWriter = new FileWriter("outputJava.txt");
					for(int i=0;i<matrice.size();i++) {
						for (int j = 0; j < matrice.get(i).size(); j++) {
							String s=String.valueOf(matrice.get(i).get(j));
							myWriter.write(s);
							myWriter.write(" ");
						}
						myWriter.write("\n");
					}
					myWriter.close();
				} catch (IOException f) {
					System.out.println("An error occurred.");
					f.printStackTrace();
				}
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			//evenimentul care se produce la drag&drop pe mousse
			public void mouseDragged(MouseEvent e) {
				pointEnd = e.getPoint();
				isDragging = true;
				repaint();
			}
		});

	}

	//metoda care se apeleaza la eliberarea mouse-ului
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
		g.drawString("This is my Graph!", 10, 20);
		//deseneaza arcele existente in lista
		/*for(int i=0;i<listaArce.size();i++)
		{
			listaArce.elementAt(i).drawArc(g);
		}*/
		for (Arc a : listaArce)
		{
			a.drawArc(g);
		}
		//deseneaza arcul curent; cel care e in curs de desenare
		if (pointStart != null)
		{
			g.setColor(Color.RED);
			g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
		}
		//deseneaza lista de noduri
		for(int i=0; i<listaNoduri.size(); i++)
		{
			listaNoduri.elementAt(i).drawNode(g, node_diam);
		}
		/*for (Node nod : listaNoduri)
		{
			nod.drawNode(g, node_diam, node_Diam);
		}*/
		for (Arc a : listaArce)
		{
			if(orientat)
				a.drawArrowHead(g,5,5);
		}
	}
}
