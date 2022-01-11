import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;
import javafx.util.Pair;
import java.util.Random; 

public class Graph {

	int[][] f;
	int[][] adjacentMatrix;
	ArrayList<ArrayList<Integer>> adjacentList;
	ArrayList<ArrayList<Integer>> adjacentListCapacity;
	
	Vector<Node> nodeList = new Vector<Node>();
	Vector<Arc> arcList = new Vector<Arc>();
	Vector<Point> circleList = new Vector<Point>();
	
	boolean calculatedMaxFlow = false;
	int startIndex, endIndex;
	
	int nodeDiameter = 30;
	static int window_width;
	static int window_height;
	
	public boolean useMatrix = true;
	
	public static void Init(int width, int height)
	{
		window_width = width;
		window_height = height;
	}
	
	public void GenerateGraph()
	{
		Random rand = new Random();
		try
		{
			GenerateNodes(rand);
			GenerateArcs(rand);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	private void GenerateNodes(Random rand)
	{
		int xCoord = 0, yCoord = 0;
		int dim = useMatrix ? adjacentMatrix.length : adjacentList.size();
		
		for(int index = 0; index < dim; index++)
		{
			boolean stopGenerating = false;
			while(stopGenerating == false)
			{
				xCoord = rand.nextInt(window_width - nodeDiameter * 4) + nodeDiameter;
				yCoord = rand.nextInt(window_height - nodeDiameter * 4) + nodeDiameter;
				boolean foundCloseNode = false;
					
				for(int index2 = 0; index2 < nodeList.size(); index2++)
				{
					if(nodeList.get(index2).IsTooClose(xCoord, yCoord, nodeDiameter * 2))
					{
						foundCloseNode = true;
						break;
					}
				}
				if(foundCloseNode == false)
					stopGenerating = true;
			}
			nodeList.add(new Node(xCoord, yCoord, index));
		}
	}
	private void GenerateArcs(Random rand)
	{
		if(useMatrix)
		{
			for(int index = 0; index < adjacentMatrix.length; index++)
			{
				for(int index2 = 0; index2 < adjacentMatrix[index].length; index2++)
				{
					if(adjacentMatrix[index][index2] > 0)
					{
						Point start = new Point(nodeList.get(index).coordX + nodeDiameter / 2, 
								nodeList.get(index).coordY + nodeDiameter / 2);
						Point end = new Point(nodeList.get(index2).coordX + nodeDiameter / 2, 
								nodeList.get(index2).coordY + nodeDiameter / 2);
						arcList.add(new Arc(start, end));
						arcList.get(arcList.size() - 1).startNodeIndex = index;
						arcList.get(arcList.size() - 1).endNodeIndex = index2;
						
						int xCoord = end.x - start.x;
						int yCoord = end.y - start.y;
						Point circlePoint = new Point((int)(start.x + xCoord * 3.0 / 4) - nodeDiameter / 4, 
								(int)(start.y + yCoord * 3.0 / 4) - nodeDiameter / 4);
						circleList.add(circlePoint);
					}
				}
			}
		}
		else
		{
			for(int index = 0; index < adjacentList.size(); index++)
			{
				for(int index2 = 0; index2 < adjacentList.get(index).size(); index2++)
				{
					Point start = new Point(nodeList.get(index).coordX + nodeDiameter / 2, 
							nodeList.get(index).coordY + nodeDiameter / 2);
					Point end = new Point(nodeList.get(adjacentList.get(index).get(index2)).coordX + nodeDiameter / 2, 
							nodeList.get(adjacentList.get(index).get(index2)).coordY + nodeDiameter / 2);
					
					arcList.add(new Arc(start, end));
					arcList.get(arcList.size() - 1).startNodeIndex = index;
					arcList.get(arcList.size() - 1).endNodeIndex = adjacentList.get(index).get(index2);

					int xCoord = end.x - start.x;
					int yCoord = end.y - start.y;
					Point circlePoint = new Point((int)(start.x + xCoord * 2.0 / 3) - nodeDiameter / 4, 
							(int)(start.y + yCoord * 2.0 / 3) - nodeDiameter / 4);
					circleList.add(circlePoint);
				}
			}
		}
	}
	
	public void ReadData()
	{
		System.out.println("Do you want to read a matrix or a list?\n0 - matrix\n1 - list");
		Scanner myScanner = new Scanner(System.in);
		int option = myScanner.nextInt();
		while(option != 0 && option != 1)
		{
			System.out.println("Input unspecified, try again: ");
			option = myScanner.nextInt();
		}
		myScanner.close();
		
		try
		{
			if(option == 0)
				ReadMatrix();
			else
				ReadList();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		PrintContainer();
	}
	
	private void ReadMatrix() throws FileNotFoundException
	{
		useMatrix = true;
		File inputFile = new File("src/adjacentMatrix.in");
		if(inputFile.exists())
		{
			Scanner myScanner = new Scanner(inputFile);
			
			int dim = myScanner.nextInt();
			adjacentMatrix = new int[dim][dim];
			
			for(int index = 0; index < dim; index++)
			{
				for(int index2 = 0; index2 < dim; index2++)
				{
					adjacentMatrix[index][index2] = myScanner.nextInt();
				}
			}
			
			startIndex = myScanner.nextInt() - 1;
			endIndex = myScanner.nextInt() - 1;
			
			myScanner.close();
		}
		
	}
	private void ReadList() throws FileNotFoundException
	{
		useMatrix = false;
		File inputFile = new File("src/adjacentList.in");
		if(inputFile.exists())
		{
			Scanner myScanner = new Scanner(inputFile);
			
			int dim = myScanner.nextInt();
			adjacentList = new ArrayList<ArrayList<Integer>>();
			adjacentListCapacity = new ArrayList<ArrayList<Integer>>();
			int aux;
			
			for(int index = 0; index < dim; index++)
			{
				aux = myScanner.nextInt();
				adjacentList.add(new ArrayList<Integer>());
				adjacentListCapacity.add(new ArrayList<Integer>());
				for(int index2 = 0; index2 < aux; index2++)
				{
					adjacentList.get(index).add(myScanner.nextInt() - 1);
					adjacentListCapacity.get(index).add(myScanner.nextInt());
				}
			}

			startIndex = myScanner.nextInt() - 1;
			endIndex = myScanner.nextInt() - 1;
			
			myScanner.close();
		}
	}
	
	public void DrawGraph(Graphics graphicsComponent)
	{
		for(int index = 0; index < nodeList.size(); index++)
		{
			if(index == startIndex)
				graphicsComponent.setColor(Color.GREEN);
			else if (index == endIndex)
				graphicsComponent.setColor(Color.RED);
			else
				graphicsComponent.setColor(Color.BLUE);
				
			nodeList.get(index).DrawNode(graphicsComponent, nodeDiameter);
		}
		for(int index = 0; index < arcList.size(); index++)
		{
			arcList.get(index).DrawArc(graphicsComponent);
			
			int xCoord = (arcList.get(index).start.x + arcList.get(index).end.x) / 2;
			int yCoord = (arcList.get(index).start.y + arcList.get(index).end.y) / 2;
			if(calculatedMaxFlow)
			{
				if(useMatrix)
				{
					graphicsComponent.drawString("" + 
						f[arcList.get(index).startNodeIndex][arcList.get(index).endNodeIndex] + 
						", " + adjacentMatrix[arcList.get(index).startNodeIndex][arcList.get(index).endNodeIndex]
						, xCoord, yCoord);
				}
				else
				{
					int targetIndex = 0;
					for(int index2 = 0; index2 < adjacentList.get(arcList.get(index).startNodeIndex).size(); index2++)
					{
						if(adjacentList.get(arcList.get(index).startNodeIndex).get(index2) == 
								arcList.get(index).endNodeIndex)
						{
							targetIndex = index2;
							break;
						}
					}
					graphicsComponent.drawString("" + 
						f[arcList.get(index).startNodeIndex][arcList.get(index).endNodeIndex] + 
						", " + adjacentListCapacity.get(arcList.get(index).startNodeIndex).get(targetIndex)
						, xCoord, yCoord);
				}
			}
		}
		for(int index = 0; index < circleList.size(); index++)
		{
			graphicsComponent.setColor(Color.GRAY);
	        graphicsComponent.fillOval(circleList.get(index).x, circleList.get(index).y, 
	        		nodeDiameter/2, nodeDiameter/2);
		}
	}
	
	public void PrintContainer()
	{
		if(useMatrix)
			PrintMatrix();
		else
			PrintList();
	}
	private void PrintMatrix()
	{
		try
		{
			for(int index = 0; index < adjacentMatrix.length; index++)
			{
				for(int index2 = 0; index2 < adjacentMatrix[index].length; index2++)
				{
					System.out.print(adjacentMatrix[index][index2] + " ");
				}
				System.out.println();
			}
		}
		catch(Exception ex)
		{
			System.out.println("Print matrix exception: " + ex.getMessage());
		}
	}
	private void PrintList()
	{
		try
		{
			for(int index = 0; index < adjacentList.size(); index++)
			{
				System.out.print(index + ": ");
				for(int index2 = 0; index2 < adjacentList.get(index).size(); index2++)
				{
					System.out.print(adjacentList.get(index).get(index2) + " ");
				}
				System.out.println();
			}
		}
		catch(Exception ex)
		{
			System.out.println("Print list exception: " + ex.getMessage());
		}
	}

	public void FindMaxFlow()
	{
		calculatedMaxFlow = true;
		int[][] r = new int[nodeList.size()][nodeList.size()];
		f = new int[nodeList.size()][nodeList.size()];
		
		Vector<Pair<Integer, Integer>> rezidualNetwork = new Vector<Pair<Integer, Integer>>();
		Vector<Pair<Integer, Integer>> dmf = new Vector<Pair<Integer, Integer>>();
		
		if(useMatrix)
		{
			for(int index = 0; index < nodeList.size(); index++)
			{
				for(int index2 = 0; index2 < nodeList.size(); index2++)
				{
					r[index][index2] = adjacentMatrix[index][index2];
				}
			}
		}
		else
		{
			for(int index = 0; index < adjacentList.size(); index++)
			{
				for(int index2 = 0; index2 < adjacentList.get(index).size(); index2++)
				{
					r[index][adjacentList.get(index).get(index2)] = adjacentListCapacity.get(index).get(index2);
				}
			}
		}
		
		ConstructRezidualNetwork(rezidualNetwork, r);
		FindDmf(dmf, rezidualNetwork);
		
		int min, first, second;
		while(dmf.size() != 0)
		{
			min = Integer.MAX_VALUE;
			for(int index = 0; index < dmf.size(); index++)
			{
				first = dmf.get(index).getKey();
				second = dmf.get(index).getValue();
				
				if(r[first][second] < min)
				{
					min = r[first][second];
				}
			}
			
			for(int index = 0; index < dmf.size(); index++)
			{
				first = dmf.get(index).getKey();
				second = dmf.get(index).getValue();
				
				r[first][second] -= min;
				r[second][first] += min;
			}
			
			ConstructRezidualNetwork(rezidualNetwork, r);
			FindDmf(dmf, rezidualNetwork);
		}
		
		if(useMatrix)
		{
			for(int index = 0; index < nodeList.size(); index++)
			{
				for(int index2 = 0; index2 < nodeList.size(); index2++)
				{
					if(adjacentMatrix[index][index2] - r[index][index2] >= 0)
					{
						f[index][index2] = adjacentMatrix[index][index2] - r[index][index2];
						f[index2][index] = 0;
					}
					else
					{
						f[index][index2] = 0;
						f[index2][index] = Math.abs(r[index][index2] - adjacentMatrix[index][index2]);
					}
				}
			}
		}
		else
		{
			for(int index = 0; index < adjacentList.size(); index++)
			{
				for(int index2 = 0; index2 < adjacentList.get(index).size(); index2++)
				{
					second = adjacentList.get(index).get(index2);
					if(adjacentListCapacity.get(index).get(index2) - r[index][second] >= 0)
					{
						f[index][second] = adjacentListCapacity.get(index).get(index2) - r[index][second];
						f[second][index] = 0;
					}
					else
					{
						f[index][second] = 0;
						f[second][index] = r[index][second] - adjacentListCapacity.get(index).get(index2);
					}
				}
			}
		}
	}
	
	private void FindDmf(Vector<Pair<Integer, Integer>> dmf, Vector<Pair<Integer, Integer>> rezidualNetwork)
	{
		dmf.clear();
		
		Stack<Integer> traversalStack = new Stack<Integer>();
		boolean[] visited = new boolean[nodeList.size()];
		int[] predecessor = new int[nodeList.size()];
		
		int currentIndex;
		int first, second;
		
		traversalStack.push(startIndex);
		predecessor[startIndex] = -1;
		visited[startIndex] = true;
		
		boolean foundEnd = false;
		while(!traversalStack.isEmpty() && foundEnd == false)
		{
			currentIndex = traversalStack.pop();
			for(int index = 0; index < rezidualNetwork.size(); index++)
			{
				first = rezidualNetwork.get(index).getKey();
				second = rezidualNetwork.get(index).getValue();
				
				if(first == currentIndex && visited[second] == false)
				{
					traversalStack.push(second);
					predecessor[second] = currentIndex;
					visited[second] = true;
					
					if(second == endIndex)
					{
						foundEnd = true;
						break;
					}
				}
			}
		}
		
		if(foundEnd == false)
			return;
		
		currentIndex = endIndex;
		while(predecessor[currentIndex] != -1)
		{
			dmf.add(new Pair<Integer, Integer>(predecessor[currentIndex], currentIndex));
			currentIndex = predecessor[currentIndex];
		}
	}
	
	private void ConstructRezidualNetwork(Vector<Pair<Integer, Integer>> rezidualNetwork, int[][] r)
	{
		int second;
		rezidualNetwork.clear();
		if(useMatrix)
		{
			for(int index = 0; index < nodeList.size(); index++)
			{
				for(int index2 = 0; index2 < nodeList.size(); index2++)
				{
					if(r[index][index2] > 0)
					{
						rezidualNetwork.add(new Pair(index, index2));
					}
				}
			}
		}
		else
		{
			for(int first = 0; first < adjacentList.size(); first++)
			{
				for(int index2 = 0; index2 < adjacentList.get(first).size(); index2++)
				{
					second = adjacentList.get(first).get(index2);
					if(r[first][second] > 0)
					{
						rezidualNetwork.add(new Pair(first, second));
					}
					if(r[second][first] > 0)
					{
						rezidualNetwork.add(new Pair(second, first));
					}
				}
			}
		}
	}
}
