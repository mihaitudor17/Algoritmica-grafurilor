import java.util.*;

class Djikstra {
    static class AdjListNode {
        int vertex, weight;
        AdjListNode(int v, int w) {
            vertex = v;
            weight = w;
        }
        int getVertex() {
            return vertex;
        }

        int getWeight() {
            return weight;
        }
    }
    public static void dijkstra(int V, ArrayList<ArrayList<AdjListNode>> graph, int source, Vector<Punct> listaPuncte, int target) {
        int[] distance = new int[V];
        for (int i = 0; i < V; i++)
            distance[i] = Integer.MAX_VALUE;
        distance[0] = 0;

        PriorityQueue<AdjListNode> pq = new PriorityQueue<>((v1, v2) -> v1.getWeight() - v2.getWeight());
        pq.add(new AdjListNode(source, 0));

        while (pq.size() > 0) {
            AdjListNode current = pq.poll();

            for (AdjListNode n :
                    graph.get(current.getVertex())) {
                if (distance[current.getVertex()] + n.getWeight() < distance[n.getVertex()]) //parinte n vertex current vertex
                {
                    distance[n.getVertex()] = n.getWeight() + distance[current.getVertex()];
                    listaPuncte.get(n.getVertex()).setParent(current.getVertex());
                    pq.add(new AdjListNode(n.getVertex(), distance[n.getVertex()]));
                }
            }
        }
    }
}
