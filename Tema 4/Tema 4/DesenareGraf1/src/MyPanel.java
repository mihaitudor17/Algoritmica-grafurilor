import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
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
	boolean orientat =true;
	boolean tipDesenare;
	ArrayList<ArrayList<Integer>> matrice = new ArrayList<>();
	public MyPanel()
	{
		System.out.println("Introduceti 1 pentru desenare manuala sau 2 pt automata: ");
		int temp;
		Scanner F=new Scanner(System.in);
		temp=F.nextInt();
		if(temp==1) {
			tipDesenare = false;
			JButton button = new JButton("Sortare Topologica");

			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					//Do what you like here after button is clicked, for example
						sortareTopologica(matrice,listaNoduri);
						System.out.println();
				}
			});
			add(button);
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
								matrice.get(i1).add(i2+1);
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
								String s = String.valueOf(matrice.get(i).get(j));
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
			tipDesenare=true;
			System.out.println("Introduceti numarul de noduri:");
			int nodeNumber;
			Scanner S=new Scanner(System.in);
			nodeNumber=S.nextInt();
			for(int i=0;i<nodeNumber;i++)
			{
				matrice.add(new ArrayList());
			}
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
					pointStart=new Point();
					pointStart.setLocation(listaNoduri.get(k).getCoordX()+node_diam/2,listaNoduri.get(k).getCoordY()+node_diam/2);
					pointEnd=new Point();
					pointEnd.setLocation(listaNoduri.get(listaNoduri.size()-1).getCoordX()+node_diam/2,listaNoduri.get(listaNoduri.size()-1).getCoordY()+node_diam/2);
					float rand_float=(float)(Math.random()*100);
					if(rand_float<prob)
					{
						Arc arc=new Arc(pointStart,pointEnd);
						listaArce.add(arc);
						matrice.get(k).add(listaNoduri.size()-1);
					}
					rand_float=(float)(Math.random()*100);
					System.out.println(rand_float);
					if(rand_float<prob)
					{
						Arc arc1=new Arc(pointEnd,pointStart);
						listaArce.add(arc1);
						matrice.get(listaNoduri.size()-1).add(k);
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
						String s = String.valueOf(matrice.get(i).get(j));
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
			sortareTopologica(matrice,listaNoduri);
		}
	}
	private void sortareTopologica(ArrayList<ArrayList<Integer>> List,Vector<Node> listaNoduri)
	{


		for(int i=0;i<listaNoduri.size();i++)
			listaNoduri.get(i).setVisited(false);

		Vector<Integer> vizit = new Vector<Integer>();

		for(int i=0;i<listaNoduri.size();i++)
			vizit.add(0);

		Vector<Integer> predecesor = new Vector<Integer>();

		for(int i=0;i<listaNoduri.size();i++)

			predecesor.add(0);
		Stack<Node> Nodes = new Stack<Node>();
		Stack<Node> Progress = new Stack<Node>();

		boolean found=false;

		for(int i=0;i<listaNoduri.size();i++)
			if(listaNoduri.get(i).getVisited()==false)
			{

				for(int j=0;j<listaNoduri.size();j++)
					vizit.set(j,0);
				Node curent= listaNoduri.get(i);
				Progress.push(curent);
				while(Progress.size()>0)
				{
					Node copy =curent;
					vizit.set(copy.getNumber()-1, 1);
					found=false;
					for(int k=0;k<List.get(copy.getNumber()-1).size();k++)
					{

						if(vizit.get(List.get(copy.getNumber()-1).get(k)-1)==0)
						{

							predecesor.set(List.get(copy.getNumber()-1).get(k)-1,copy.getNumber()-1);
							listaNoduri.get(List.get(copy.getNumber()-1).get(k)-1).setVisited(true);
							vizit.set(List.get(copy.getNumber()-1).get(k)-1,1);                 //setez vizitat nr din lista diacenta
							curent= listaNoduri.get(List.get(copy.getNumber()-1).get(k)-1);   //iau elementul din lista cu nr din lista de adicenta
							found = true;
							Progress.push(curent);
							break;

						}
						else
						{
							if(Progress.search(listaNoduri.get(List.get(copy.getNumber()-1).get(k)-1))!=-1)
							{System.out.print("Exista ciclu!");
								return;

							}
						}
					}
					if(!found)
					{
						Progress.pop();
						if(Nodes.search(copy)==-1)
							Nodes.push(copy);
						listaNoduri.get(copy.getNumber()-1).setVisited(true);
						curent=listaNoduri.get(predecesor.get(curent.getNumber()-1));
					}
				}


			}
		Vector<Node> aux = new Vector<Node>();
		while(Nodes.size()>0)
		{
			aux.add(Nodes.peek());
			Nodes.pop();
		}


		for(Node a : aux)
			System.out.print(a.getNumber()+" ");

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
		if(tipDesenare=false)
		{super.paintComponent(g);//apelez metoda paintComponent din clasa de baza
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
			listaNoduri.elementAt(i).drawNode(g, node_diam,tipDesenare);
		}
		/*for (Node nod : listaNoduri)
		{
			nod.drawNode(g, node_diam, node_Diam);
		}*/
			for (Arc a : listaArce)
			{
				a.drawArrowHead(g,5,5);
			}
		}
		else
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
				listaNoduri.elementAt(i).drawNode(g,node_diam,tipDesenare);
			}
			for (Arc a : listaArce)
			{
				a.drawArrowHead(g,5,5);
			}
		}
	}
}
