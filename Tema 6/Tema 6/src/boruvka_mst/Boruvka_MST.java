package boruvka_mst;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Boruvka_MST {

    public static Graph Boruvka(Graph g) {
        int v = g.getvCount();
        Graph mst = new Graph(v);
        while (!mst.isConnected()) {
            for (int i = 0; i < v; i++) {
                if (mst.CountReachableNodes(i) < v) {
                    Iterator<Edge> it = g.neighbours(i).iterator();
                    while(it.hasNext()){
                        Edge e = it.next();
                        if(!mst.hasEdge(e) && !mst.Reachable(i, e.getEndPoint())){
                            mst.addEdge(e);
                            break;
                        }
                    }
                }
            }
        }

        return mst;
    }

}