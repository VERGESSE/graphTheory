package top.vergessen.dfs;

import top.vergessen.graph.Graph;

import java.util.ArrayList;

public class GraphDFS {

    private Graph G;
    private boolean[] visited;
    private ArrayList<Integer> pre = new ArrayList<>();
    private ArrayList<Integer> post = new ArrayList<>();

    public GraphDFS(Graph G){
        this.G = G;
        visited = new boolean[G.getV()];

        for (int v = 0; v < G.getV(); v++)
            if (!visited[v])
                dfs(v);
    }

    // 时间复杂度 O(V+E)
    private void dfs(int v){

        visited[v] = true;
        pre.add(v);          // 先序遍历
        for (int w : G.adj(v))
            if (!visited[w])
                dfs(w);
        post.add(v);          // 后序遍历
    }

    public Iterable<Integer> pre(){
        return pre;
    }

    public Iterable<Integer> post(){
        return post;
    }

    public static void main(String[] args) {
        Graph graph = new Graph("src/main/g.txt");
        GraphDFS graphDFS = new GraphDFS(graph);
        System.out.println(graphDFS.pre());
        System.out.println(graphDFS.post());
    }
}
