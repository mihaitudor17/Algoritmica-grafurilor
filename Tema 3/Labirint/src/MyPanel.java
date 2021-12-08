import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MyPanel extends JPanel {
	private int node_diam = 50;
	private Vector<Node> listaNoduri= new Vector<Node>();
	private Vector<Vector<Integer>> maze=new Vector<Vector<Integer>>();
	private Vector<Vector<Integer>> bfs= new Vector<Vector<Integer>>();
	private Vector<Integer> exit=new Vector<Integer>();
	public MyPanel() {
		Point punct=new Point();
		// borderul panel-ului
		setBorder(BorderFactory.createLineBorder(Color.black));
		try {
			File myObj = new File("input.txt");
			Scanner myReader = new Scanner(myObj);
			Integer data = myReader.nextInt();
			int n = data;
			data = myReader.nextInt();
			int m = data;
			for (int i = 0; i < n; i++)
			{	Vector<Integer> temp=new Vector<Integer>();
				Vector<Integer> temp1=new Vector<Integer>();
				for (int j = 0; j < m; j++) {
					data = myReader.nextInt();
					int ntemp=data;
					temp.add(ntemp);
					if(ntemp==2) {
						temp1.add(-2);
						exit.add(i);
						exit.add(j);
					}
					else
					if(ntemp==3)
					{
						temp1.add(-3);
						punct.x=i;
						punct.y=j;
					}
					else if(ntemp==0)
					{
						temp1.add(-1);
					}
					else
					{
						temp1.add(0);
					}
				}
				maze.add(temp);
				bfs.add(temp1);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		Queue<Point> coada= new LinkedList<Point>();
		coada.add(punct);
		int i=1;
		while(!coada.isEmpty()) //implementare bfs
		{
			int i1 = coada.peek().x;
			int j1=  coada.peek().y;
			coada.remove();
			if(i1>0&&bfs.get(i1-1).get(j1)==0)
			{
				Point punct1=new Point();
				punct1.x=i1-1;
				punct1.y=j1;
				coada.add(punct1);
				bfs.get(i1-1).set(j1,i);
			}
			if(j1>0&&bfs.get(i1).get(j1-1)==0)
			{
				Point punct2=new Point();
				punct2.x=i1;
				punct2.y=j1-1;
				coada.add(punct2);
				bfs.get(i1).set(j1-1,i);
			}
			if(i1<bfs.size()-1&&bfs.get(i1+1).get(j1)==0)
			{
				Point punct3=new Point();
				punct3.x=i1+1;
				punct3.y=j1;
				coada.add(punct3);
				bfs.get(i1+1).set(j1,i);
			}
			if(j1<bfs.get(0).size()-1&&bfs.get(i1).get(j1+1)==0)
			{
				Point punct4=new Point();
				punct4.x=i1;
				punct4.y=j1+1;
				coada.add(punct4);
				bfs.get(i1).set(j1+1,i);

			}
			i++;
		}
		for(i=0;i<exit.size()-1;i++)
		{
			int i1=exit.get(i);
			int j1=exit.get(i+1);
			i++;
			int minim=1000000;
			int i2=-1,j2=-1;
			boolean ok=true;
			do{
				if(i1>0)
					if(bfs.get(i1-1).get(j1)==-3)
						ok=false;
					else
					if(bfs.get(i1-1).get(j1)<minim&&bfs.get(i1-1).get(j1)>0)
					{
						i2=i1-1;
						j2=j1;
						minim=bfs.get(i1-1).get(j1);
					}
				if(j1>0)
					if(bfs.get(i1).get(j1-1)==-3)
						ok=false;
					else
					if(bfs.get(i1).get(j1-1)<minim&&bfs.get(i1).get(j1-1)>0)
					{
						i2=i1;
						j2=j1-1;
						minim=bfs.get(i1).get(j1-1);

					}
				if(i1<bfs.size()-1)
					if(bfs.get(i1+1).get(j1)==-3)
						ok=false;
					else
					if(bfs.get(i1+1).get(j1)<minim&&bfs.get(i1+1).get(j1)>0)
					{
						i2=i1+1;
						j2=j1;
						minim=bfs.get(i1+1).get(j1);
					}
				if(j1<bfs.get(0).size()-1)
					if(bfs.get(i1).get(j1+1)==-3)
						ok=false;
					else
					if(bfs.get(i1).get(j1+1)<minim&&bfs.get(i1).get(j1+1)>0)
					{
						i2=i1;
						j2=j1+1;
						minim=bfs.get(i1).get(j1+1);
					}
				if(i2>=0&&j2>=0)
				{maze.get(i2).set(j2,4);
					i1=i2;
					j1=j2;}
				else
					ok=false;
			}while(ok);
		}
		int x=20,y=20;
		for(i=0;i<maze.size();i++) {
			for (int j = 0; j < maze.get(i).size(); j++) {
				addNode(x, y, maze.get(i).get(j));
				x += node_diam;
			}
			x=20;
			y+=node_diam;
		}
	}
	private void addNode ( int x, int y,int color){
		Node node = new Node(x, y,color);
		listaNoduri.add(node);
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
		//deseneaza arcul curent; cel care e in curs de desenare
		//deseneaza lista de noduri
		for(int i=0; i<listaNoduri.size(); i++)
		{
			listaNoduri.get(i).drawNode(g, node_diam);
		}
		/*for (Node nod : listaNoduri)
		{
			nod.drawNode(g, node_diam, node_Diam);
		}*/
	}
}
