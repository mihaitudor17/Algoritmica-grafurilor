package boruvka_mst;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Boruvka_MST {

    public static Graph Boruvka(Graph g) {
        int v = g.getvCount();

        // initialize mst
        Graph mst = new Graph(v);

        // loop until everything is connected
        while (!mst.isConnected()) {

            // loop through all vertices
            for (int i = 0; i < v; i++) {

                // check if all nodes are reachable
                if (mst.CountReachableNodes(i) < v) {

                    // iterate through all the edges of the current node
                    Iterator<Edge> it = g.neighbours(i).iterator();
                    while(it.hasNext()){
                        Edge e = it.next();

                        // add edge to mst if not added already
                        // AND
                        // endPoint is not reachable from elsewhere (no cycles allowed!)
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