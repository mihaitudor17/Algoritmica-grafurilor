import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
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
	int tipGraf=0;
	int color=1;
	ArrayList<ArrayList<Integer>> matrice = new ArrayList<>();
	ArrayList<ArrayList<Integer>> componente = new ArrayList<>();
	public MyPanel()
	{
		System.out.println("Alegeti 0 pentru graf manual sau 1 pentru automat: ");
		Scanner P=new Scanner(System.in);
		tipGraf=P.nextInt();
		System.out.println("Alegeti 0 pentru neorientat si 1 pentru orientat: ");
		Scanner Q=new Scanner(System.in);
		if(Q.nextInt()==0)
			orientat=false;
		else
			orientat=true;
		JButton button = new JButton("Colorare componente");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(orientat==false)
				{
					CGraph g=new CGraph(matrice.size());
					for(int i=0;i<matrice.size()-1;i++)
					{
						for(int j=0;j<matrice.get(i).size();j++)
						g.addEdge(i,matrice.get(i).get(j));
					}
						g.connectedComponents();
					componente=g.getMatrice();
					int color=1;
					for(int i=0;i<componente.size()-1;i++)
					{if(color>5)
							color=1;
						for(int j=0;j<componente.get(i).size();j++)
						{
							if(j<matrice.size()&&(componente.get(i).get(j)<matrice.size()-1))
							{
							listaNoduri.get(componente.get(i).get(j)).setColor(color);
							repaint();}
						    }
					color++;
					}
				}
				else
				{
					Graph g=new Graph(matrice.size());
					for(int i=0;i<matrice.size()-1;i++)
					{
						for(int j=0;j<matrice.get(i).size();j++)
							g.addEdge(i,matrice.get(i).get(j));
					}
					g.printSCCs();
					try {
						File myObj = new File("Lista de adiacenta.txt");
						Scanner myReader = new Scanner(myObj);
						int i=0;
						while (myReader.hasNextLine()) {
							i++;
							String data = myReader.nextLine();
							//System.out.println(data);
							for(int j=0;j<data.length();j++)
							{
								if(i>5)
									i=1;
								if(Character.isDigit(data.charAt(j)))
								{
									listaNoduri.get(Character.getNumericValue(data.charAt(j))).setColor(i);
									repaint();
								}
							}
						}
						myReader.close();
					} catch (FileNotFoundException f) {
						System.out.println("An error occurred.");
						f.printStackTrace();
					}


				}
			}
		});
		add(button);
		if(tipGraf==0) {
			listaNoduri = new Vector<Node>();
			listaArce = new Vector<Arc>();
			matrice.add(new ArrayList());
			// borderul panel-ului
			setBorder(BorderFactory.createLineBorder(Color.black));
			addMouseListener(new MouseAdapter() {
				//evenimentul care se produce la apasarea mousse-ului
				public void mousePressed(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1)
						pointStart = e.getPoint();
					else
						pointBegin = e.getPoint();
				}

				//evenimentul care se produce la eliberarea mousse-ului
				public void mouseReleased(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						if (!isDragging) {
							if (i3 == 0) {
								addNode(e.getX(), e.getY());
								matrice.add(new ArrayList());
								i3++;
								if (orientat == false) {
									k = false;
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
									matrice.get(i1).add(i2);
								} else {
									matrice.get(i1).add(i2);
									matrice.get(i2).add(i1);
								}
							}

							repaint();
						}
					} else {
						if (isDragging) {
							boolean ok = false;
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
						for (int i = 0; i < matrice.size(); i++) {
							for (int j = 0; j < matrice.get(i).size(); j++) {
								String s = String.valueOf(matrice.get(i).get(j)+1);
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
		else
		{
			System.out.println("Introduceti numarul de noduri:");
			int nodeNumber;
			Scanner S=new Scanner(System.in);
			nodeNumber=S.nextInt();
			for(int i=0;i<nodeNumber;i++)
				matrice.add(new ArrayList());
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
						ok++;
						pointStart=new Point();
						pointStart.setLocation(listaNoduri.get(k).getCoordX(),listaNoduri.get(k).getCoordY());
						pointEnd=new Point();
						pointEnd.setLocation(listaNoduri.get(listaNoduri.size()-1).getCoordX(),listaNoduri.get(listaNoduri.size()-1).getCoordY());
						Arc arc=new Arc(pointStart,pointEnd);
						matrice.get(k).add(listaNoduri.size()-1);
						listaArce.add(arc);
					}
					rand_float=(float)(Math.random()*100);
					if(rand_float<prob)
					{
						ok++;
						pointStart=new Point();
						pointStart.setLocation(listaNoduri.get(listaNoduri.size()-1).getCoordX(),listaNoduri.get(listaNoduri.size()-1).getCoordY());
						pointEnd=new Point();
						pointEnd.setLocation(listaNoduri.get(k).getCoordX(),listaNoduri.get(k).getCoordY());
						Arc arc=new Arc(pointStart,pointEnd);
						matrice.get(listaNoduri.size()-1).add(k);
						listaArce.add(arc);
					}
				}
			}
			try {
				File myObj = new File("outputJava.txt");
				myObj.createNewFile();
			} catch (IOException g) {
				System.out.println("An error occurred.");
				g.printStackTrace();
			}
			try {
				FileWriter myWriter = new FileWriter("outputJava.txt");
				for (int i = 0; i < matrice.size(); i++) {
					for (int j = 0; j < matrice.get(i).size(); j++) {
						String s = String.valueOf(matrice.get(i).get(j)+1);
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
			g.setColor(Color.BLACK);
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
