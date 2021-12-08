import java.util.ArrayList;
class CGraph
{
    ArrayList<ArrayList<Integer>> matrice = new ArrayList<>();
    boolean ok=false;
    int i=0;
    ArrayList<ArrayList<Integer>> getMatrice()
    {
        return matrice;
    }

    int V;
    ArrayList<ArrayList<Integer> > adjListArray;
    // constructor
    CGraph(int V)
    {
        this.V = V;

        adjListArray = new ArrayList<>();



        for (int i = 0; i < V; i++) {
            adjListArray.add(i, new ArrayList<>());
        }
    }

    void addEdge(int src, int dest)
    {

        adjListArray.get(src).add(dest);


        adjListArray.get(dest).add(src);
    }

    void DFSUtil(int v, boolean[] visited)
    {

        visited[v] = true;
        if(ok==false)
        {matrice.add(new ArrayList());
            ok=true;
        }
        matrice.get(i).add(v);

        for (int x : adjListArray.get(v)) {
            if (!visited[x])
                DFSUtil(x, visited);
        }
    }
    void connectedComponents()
    {

        boolean[] visited = new boolean[V];
        for (int v = 0; v < V; ++v) {
            if (!visited[v]) {

                DFSUtil(v, visited);
                matrice.add(new ArrayList());
                i++;
            }
        }
    }
    }
