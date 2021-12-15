import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
public class MyPanel extends JPanel {
        private int punct_diam;
        private static final String FILENAME = "hartaLuxembourg.xml";
        private Vector<Punct> listaPuncte;
        private Vector<Punct> listaNoduri;
        private Vector<Arc> listaArce;
        ArrayList<ArrayList<Integer>> matrice = new ArrayList<>();
        Point pointStart = null;
        Point pointEnd = null;
        boolean isDragging=false;
        int longMin=Integer.MAX_VALUE;
        int longMax=Integer.MIN_VALUE;
        int latMin=Integer.MAX_VALUE;
        int latMax=Integer.MIN_VALUE;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        public MyPanel() {
            listaNoduri = new Vector<Punct>();
            listaPuncte = new Vector<Punct>();
            listaArce = new Vector<Arc>();
            setBorder(BorderFactory.createLineBorder(Color.black));
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            int size;
            try {
                dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new File(FILENAME));
                doc.getDocumentElement().normalize();
                NodeList list = doc.getElementsByTagName("node");
                size=list.getLength();
                for (int temp = 0; temp < list.getLength(); temp++) {
                    Node node = list.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String id = element.getAttribute("id");
                        String longitude = element.getAttribute("longitude");
                        String latitude = element.getAttribute("latitude");
                        if(Integer.parseInt(latitude)<latMin)
                            latMin=Integer.parseInt(latitude);
                        if(Integer.parseInt(latitude)>latMax)
                            latMax=Integer.parseInt(latitude);
                        if(Integer.parseInt(longitude)<longMin)
                            longMin=Integer.parseInt(longitude);
                        if(Integer.parseInt(longitude)>longMax)
                            longMax=Integer.parseInt(longitude);
                        Punct punct=new Punct(Integer.parseInt(latitude),Integer.parseInt(longitude),Integer.parseInt(id));
                        listaPuncte.add(punct);
                    }
                }
                list = doc.getElementsByTagName("arc");
                for(int i=0;i< size;i++)
                {
                    matrice.add(new ArrayList<>());
                }
                for (int temp = 0; temp < list.getLength(); temp++) {
                    listaNoduri.setSize(size);
                    Node node = list.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String from = element.getAttribute("from");
                        String to = element.getAttribute("to");
                        String length = element.getAttribute("length");
                        matrice.get(Integer.parseInt(from)).add(Integer.parseInt(to));
                        matrice.get(Integer.parseInt(from)).add(Integer.parseInt(length));
                        matrice.get(Integer.parseInt(to)).add(Integer.parseInt(from));
                        matrice.get(Integer.parseInt(to)).add(Integer.parseInt(length));
                        float a,b;
                        if(listaPuncte.get(Integer.parseInt(from)).getCoordX()==latMin)
                        {
                            a=0;
                        }
                        else
                        {
                            float c=((float)(latMax-latMin)/(float)(listaPuncte.get(Integer.parseInt(from)).getCoordX()-latMin));
                            a=(screenSize.height/c);
                        }
                        if(listaPuncte.get(Integer.parseInt(from)).getCoordY()==longMin)
                        {
                            b=0;
                        }
                        else
                        {
                            float c=((float)(longMax-longMin)/(float)(listaPuncte.get(Integer.parseInt(from)).getCoordY()-longMin));
                            b=(screenSize.height/c);
                        }
                        Point2D.Double start=new Point2D.Double(a,b);
                        Punct alfa=new Punct(a,b,listaPuncte.get(Integer.parseInt(from)).getNumber());
                        listaNoduri.set(listaPuncte.get(Integer.parseInt(from)).getNumber(),alfa);
                        float d,e;
                        if(listaPuncte.get(Integer.parseInt(to)).getCoordX()==latMin)
                        {
                            d=0;
                        }
                        else
                        {
                            float c=((float)(latMax-latMin)/(float)(listaPuncte.get(Integer.parseInt(to)).getCoordX()-latMin));
                            d=(screenSize.height/c);
                        }
                        if(listaPuncte.get(Integer.parseInt(to)).getCoordY()==longMin)
                        {
                            e=0;
                        }
                        else
                        {
                            float c=((float)(longMax-longMin)/(float)(listaPuncte.get(Integer.parseInt(to)).getCoordY()-longMin));
                            e=(screenSize.height/c);
                        }
                        Point2D.Double end=new Point2D.Double(d,e);
                        Arc arc=new Arc(start,end,Integer.parseInt(length));
                        Punct beta=new Punct(d,e,listaPuncte.get(Integer.parseInt(to)).getNumber());
                        listaNoduri.set(listaPuncte.get(Integer.parseInt(to)).getNumber(),beta);
                        listaArce.add(arc);
                    }
                }
            } catch (ParserConfigurationException | SAXException | IOException f) {
                f.printStackTrace();
            }
            repaint();
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    pointStart = e.getPoint();
                }

                public void mouseReleased(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        if (isDragging) {
                            ArrayList<ArrayList<Djikstra.AdjListNode> > graph= new ArrayList<>();;
                            for (int i = 0; i < matrice.size(); i++) {
                                graph.add(new ArrayList<>());
                            }
                            for(int i=0;i<matrice.size();i++)
                            {
                                for(int j=0;j<matrice.get(i).size();j++)
                                {
                                    graph.get(i).add(new Djikstra.AdjListNode(matrice.get(i).get(j),matrice.get(i).get(j+1)));
                                    j++;
                                }
                            }
                            double minimS=Integer.MAX_VALUE,minimF=Integer.MAX_VALUE;;
                            int indMinS=-1,indMinF=-1;
                            for(Punct n:listaNoduri)
                            {
                                if(n!=null) {
                                    double ac = Math.abs(n.getCoordY() - pointStart.y);
                                    double cb = Math.abs(n.getCoordX() - pointStart.x);
                                    if (Math.hypot(ac, cb) < minimS) {
                                        minimS = Math.hypot(ac, cb);
                                        indMinS = n.getNumber();
                                    }
                                    double ad = Math.abs(n.getCoordY() - pointEnd.y);
                                    double db = Math.abs(n.getCoordX() - pointEnd.x);
                                    if (Math.hypot(ad, db) < minimF) {
                                        minimF = Math.hypot(ad, db);
                                        indMinF = n.getNumber();
                                    }
                                }
                            }
                            Djikstra.dijkstra(graph.size(), graph, indMinS,listaPuncte,indMinF);
                            listaPuncte.get(indMinS).setParent(-1);
                            int k=indMinF;
                            while(listaPuncte.get(k).getParent()!=-1)
                            {
                                int temp=listaPuncte.get(k).getParent();
                                Point2D.Double t=new Point2D.Double(listaNoduri.get(k).getCoordX(),listaNoduri.get(k).getCoordY());
                                Point2D.Double r=new Point2D.Double(listaNoduri.get(temp).getCoordX(),listaNoduri.get(temp).getCoordY());
                                Arc tr=new Arc(t,r,Integer.MAX_VALUE);
                                tr.setColor(1);
                                listaArce.add(tr);
                                k=listaPuncte.get(k).getParent();
                            }
                            repaint();
                        }
                    }
                    pointStart = null;
                    isDragging = false;
                }

            });
            addMouseMotionListener(new MouseMotionAdapter() {
                //evenimentul care se produce la drag&drop pe mousse
                public void mouseDragged(MouseEvent e) {
                    pointEnd = e.getPoint();
                    isDragging = true;
                }
            });
            isDragging=false;
        }

        //se executa atunci cand apelam repaint()
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            for (Arc a : listaArce)
            {
                a.drawArc(g);
            }

        }
    }


