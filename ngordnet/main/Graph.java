package ngordnet.main;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final int V;
    private ArrayList<Integer>[] adj;


    public Graph(int V) {
        this.V = V;
        adj =  new ArrayList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<Integer>();
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    public ArrayList<Integer> adj(int v) {
        return adj[v];
    }


}
