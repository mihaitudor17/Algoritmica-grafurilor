import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.LinkedList;

// This class represents a directed graph using adjacency list
// representation
class Graph {
    private int V;   // No. of vertices
    private LinkedList<Integer> adj[]; //Adjacency List

    void writeToFile(String text){
        try {
            Files.write(Paths.get("Lista de adiacenta.txt"), text.getBytes(), StandardOpenOption.APPEND);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Constructor
    Graph(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    void addEdge(int v, int w) {
        adj[v].add(w);
    }

    void DFSUtil(int v, boolean visited[]) {

        visited[v] = true;
        writeToFile(Integer.toString(v));
        writeToFile(" ");
        int n;

        Iterator<Integer> i = adj[v].iterator();
        while (i.hasNext()) {
            n = i.next();
            if (!visited[n])
                DFSUtil(n, visited);
        }

    }

    Graph getTranspose() {
        Graph g = new Graph(V);
        for (int v = 0; v < V; v++) {

            Iterator<Integer> i = adj[v].listIterator();
            while (i.hasNext())
                g.adj[i.next()].add(v);
        }
        return g;
    }

    void fillOrder(int v, boolean visited[], Stack stack) {

        visited[v] = true;


        Iterator<Integer> i = adj[v].iterator();
        while (i.hasNext()) {
            int n = i.next();
            if (!visited[n])
                fillOrder(n, visited, stack);
        }


        stack.push(new Integer(v));
    }


    void printSCCs() {
        try{
            FileWriter myWriter = new FileWriter("Lista de adiacenta.txt");
            myWriter.write("");
            myWriter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        Stack stack = new Stack();


        boolean visited[] = new boolean[V];

        for (int i = 0; i < V; i++)
            visited[i] = false;


        for (int i = 0; i < V; i++)
            if (visited[i] == false)
                fillOrder(i, visited, stack);


        Graph gr = getTranspose();


        for (int i = 0; i < V; i++)
            visited[i] = false;


        stack.pop();
        while (stack.empty() == false) {

            int v = (int) stack.pop();

            if (visited[v] == false) {
                gr.DFSUtil(v, visited);
                writeToFile("\n");
            }
        }
    }
}