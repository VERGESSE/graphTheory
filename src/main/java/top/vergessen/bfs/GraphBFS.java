package top.vergessen.bfs;

import top.vergessen.graph.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

//广度优先遍历
public class GraphBFS {

    private Graph G;
    private boolean[] visited;
    private ArrayList<Integer> order = new ArrayList<>();

    public GraphBFS(Graph G){

        this.G = G;
        visited = new boolean[G.getV()];
        for (int v = 0; v < G.getV(); v++){
            if (!visited[v])
                bfs(v);
        }
    }

    private void bfs(int v) {

        Queue<Integer> queue = new LinkedList<>();
        queue.add(v);
        visited[v] = true;
        while (!queue.isEmpty()){
            int w = queue.remove();
            order.add(w);

            for(int s : G.adj(w)){
                if (!visited[s]) {
                    queue.add(s);
                    visited[s] = true;
                }
            }
        }
    }

    public Iterable<Integer> order(){
        return order;
    }

    public static void main(String[] args) {
        Graph graph = new Graph("src/main/g.txt");
        GraphBFS graphBFS = new GraphBFS(graph);
        System.out.println(graphBFS.order());
    }
}
