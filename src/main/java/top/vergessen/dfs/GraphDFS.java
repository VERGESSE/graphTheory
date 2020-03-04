package top.vergessen.dfs;

import top.vergessen.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class GraphDFS {

    private Graph G;
    private boolean[] visited;
    private ArrayList<Integer> pre = new ArrayList<>();
    private ArrayList<Integer> pre2 = new ArrayList<>();
    private ArrayList<Integer> post = new ArrayList<>();

    public GraphDFS(Graph G){
        this.G = G;
        visited = new boolean[G.getV()];

        for (int v = 0; v < G.getV(); v++)
            if (!visited[v])
                dfs(v);

        Arrays.fill(visited,false);
        for (int v = 0; v < G.getV(); v++)
            if (!visited[v])
                dfs2(v);
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

    //深度优先遍历非递归
    private void dfs2(int s) {

        Stack<Integer> stack = new Stack<>();
        stack.push(s);
        visited[s] = true;

        while (!stack.isEmpty()) {
            int v = stack.pop();
            pre2.add(v);

            for (int w : G.adj(v)) {
                if (!visited[w]) {
                    stack.push(w);
                    visited[w] = true;
                }
            }
        }
    }

    public Iterable<Integer> pre(){
        return pre;
    }
    public Iterable<Integer> pre2(){
        return pre2;
    }

    public Iterable<Integer> post(){
        return post;
    }

    public static void main(String[] args) {
        Graph graph = new Graph("src/main/g.txt");
        GraphDFS graphDFS = new GraphDFS(graph);
        System.out.println(graphDFS.pre());
        System.out.println(graphDFS.post());
        System.out.println(graphDFS.pre2());
    }
}
