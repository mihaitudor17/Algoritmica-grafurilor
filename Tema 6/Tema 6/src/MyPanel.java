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
import boruvka_mst.*;

import static boruvka_mst.Boruvka_MST.Boruvka;

public class MyPanel extends JPanel {
    private int nodeNr = 0;
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
    int w;
    int x=0,x1=0,x2=0;
    ArrayList<ArrayList<Integer>> matrice = new ArrayList<>();
    public MyPanel()
    {
        Scanner scan=new Scanner(System.in);
        System.out.println("Introduceti 1 pentru Prim, 2 pentru Kurskal sau 3 pentru Boruvka");
        int opt=scan.nextInt();
        JButton button = new JButton("Arbore minim");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(opt) {
                    case 1:
                        if(color<5)
                            color+=1;
                        else
                            color=2;
                        MST t = new MST();
                        int graph1[][]=new int[matrice.size()-1][matrice.size()-1];
                    for(int i=0;i<matrice.size()-1;i++)
                    {
                        //System.out.println(i);
                        for(int j=0;j<matrice.get(i).size();j+=2)
                        {
//                            System.out.println(matrice);
//                            System.out.println(matrice.get(i).get(j+1));
                            graph1[i][matrice.get(i).get(j)]=matrice.get(i).get(j+1);
                            graph1[matrice.get(i).get(j)][i]=matrice.get(i).get(j+1);
                        }
                    }
                        t.setV(graph1.length);
                        String temp2=t.primMST(graph1);//nu pot folosi 0 pentru Prim deoarece am matrice de adiacenta
                        //System.out.println(temp2);
                        for(int k=0;k<temp2.length();k++)
                        {
                            char c=temp2.charAt(k);
                            //System.out.print(c);
                            if(Character.isDigit(c))
                            {
                                //System.out.print(c);
                                if(x==0)
                                {x1=Character.getNumericValue(c);

                                    x++;}
                                else
                                if(x==1)
                                {
                                    x2=Character.getNumericValue(c);

                                    x++;
                                }
                                else if(x==2)
                                {
                                    colorareArc(x1,x2);
                                    colorareArc(x2,x1);
                                    x1=Character.getNumericValue(c);
                                    x=1;
                                }
                            }
                        }
                        repaint();
                        break;
                    case 2:
                        if(color<5)
                            color+=1;
                        else
                            color=2;
                        int V = matrice.size()-1; // Number of vertices in graph
                        int E=0;
                        for(int i=0;i<matrice.size()-1;i++) {
                            for (int j = 0; j < matrice.get(i).size(); j++)
                                if (matrice.get(i).get(j) >= 0)
                                    E++;
                        }
                        E/=2; // Number of edges in graph
                        Kruskal graph = new Kruskal(V, E);
                        // add edge 0-1
                        int l=0;
                        for(int i=0;i<matrice.size()-1;i++)
                            for(int j=0;j<matrice.get(i).size()-1;j++)
                            {
                                graph.edge[l].src=i;
                                graph.edge[l].dest=matrice.get(i).get(j);
                                graph.edge[l].weight=matrice.get(i).get(j+1);
                                j++;
                                l++;
                            }

                        // Function call
                        String temp1= graph.KruskalMST();
                            //System.out.println(temp1);
                        for(int k=0;k<temp1.length();k++)
                        {
                            char c=temp1.charAt(k);
                            //System.out.print(c);
                            if(Character.isDigit(c))
                            {
                                //System.out.print(c);
                                if(x==0)
                                {x1=Character.getNumericValue(c);

                                    x++;}
                                else
                                if(x==1)
                                {
                                    x2=Character.getNumericValue(c);

                                    x++;
                                }
                                else if(x==2)
                                {
                                    colorareArc(x1,x2);
                                    x1=Character.getNumericValue(c);

//                                    repaint();
                                    x=1;
                                }
                            }
                        }
                        repaint();
                        break;
                    default:
                        if(color<5)
                            color+=1;
                        else
                            color=2;
                        Graph g = new Graph(matrice.size()-1);
                        // add Edges
                        for(int i=0;i<matrice.size()-1;i++)
                            for(int j=0;j<matrice.get(i).size();j++)
                            {
                                //System.out.println(i+" "+matrice.get(i).get(j)+" "+(float)matrice.get(i).get(j+1));
                                g.addEdge(i,matrice.get(i).get(j),(float)matrice.get(i).get(j+1));
                                j++;
                            }
                        // Boruvka Algorithm
                        Graph mst = Boruvka(g);
                        String temp= mst.printGraph();
//                        System.out.println(temp);
                        for(int k=0;k<temp.length();k++)
                        {
                            char c=temp.charAt(k);
                            //System.out.print(c);

                            if(Character.isDigit(c))
                            {
                               // System.out.print(c);
                                if(x==0)
                                {x1=Character.getNumericValue(c);

                                x++;}
                                else
                                    if(x==1)
                                    {
                                        x2=Character.getNumericValue(c);

                                        x++;
                                    }
                                else if(x==2)
                                {
                                    colorareArc(x1,x2);
                                    x1=Character.getNumericValue(c);

//                                    repaint();
                                    x=1;
                                }
                            }
                        }
                        repaint();
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
                                if (orientat) {
                                    matrice.get(i1).add(i2);
                                } else {

                                    matrice.get(i1).add(i2);
                                    System.out.println("Introduceti greutatea arcului");
                                    w=scan.nextInt();
                                    Arc arc = new Arc(pointStart, pointEnd,w);
                                    listaArce.add(arc);
                                    matrice.get(i1).add(w);
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
                        for (int i = 0; i < matrice.size()-1; i++) {
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
    }
    private void colorareArc(int i,int l)
    {
        //System.out.println(i+" "+l);
        boolean ok = false;
        double x1,y1,x,y;
        //System.out.println(i+" "+l);
                    for (int j = 0; j < listaArce.size(); j++) {
                        Point a = listaArce.get(j).getStart();
                        x1 = (double) listaNoduri.get(i).getCoordX();
                        y1 = (double) listaNoduri.get(i).getCoordY();
                        x = (double) (a.x - x1);
                        y = (double) (a.y - y1);

                        if (x * x + y * y < node_diam * node_diam) {
                            a = listaArce.get(j).getEnd();
                            x1 = (double) listaNoduri.get(l).getCoordX();
                            y1 = (double) listaNoduri.get(l).getCoordY();
                            x = (double) (a.x - x1);
                            y = (double) (a.y - y1);
                            if (x * x + y * y < node_diam * node_diam) {

                                listaArce.get(j).setColor(color);
                                //System.out.println(j);
                               // j = listaNoduri.size();
                            }
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
//            g.setColor(Color.BLACK);
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
    }
}
