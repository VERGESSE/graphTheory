package top.vergessen.path;

import top.vergessen.graph.Graph;

import java.util.*;

// Unweighted Single Source Shortest Path
// 添加dis方法，使得可以返回无权图最短路径的长度
public class USSSPath {

    private Graph G;
    private int s;
    private boolean[] visited;
    private int[] pre;
    private int[] dis;

    public USSSPath(Graph G, int s){

        this.G = G;
        this.s = s;
        visited = new boolean[G.getV()];
        pre = new int[G.getV()];
        dis = new int[G.getV()];
        Arrays.fill(pre,-1);
        Arrays.fill(dis,-1);

        bfs(s);
    }

    private void bfs(int s) {

        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        pre[s] = s;
        dis[s] = 0;

        while (!queue.isEmpty()) {
            int v = queue.remove();

            for (int w : G.adj(v)) {
                if (!visited[w]) {
                    queue.add(w);
                    visited[w] = true;
                    pre[w] = v;
                    dis[w] = dis[v] + 1;
                }
            }
        }
    }

    public int dis(int t){
        G.validateVertex(t);
        return dis[t];
    }

    public Iterable<Integer> path(int t){
        ArrayList<Integer> res = new ArrayList<>();
        if (!isConnectedTo(t)) return res;

        int cur = t;
        while (cur != s){
            res.add(cur);
            cur = pre[cur];
        }
        res.add(s);

        Collections.reverse(res);
        return res;
    }

    public boolean isConnectedTo(int t){
        G.validateVertex(t);
        return visited[t];
    }

    public static void main(String[] args) {
        Graph graph = new Graph("src/main/g.txt");
        USSSPath usssPath = new USSSPath(graph,0);
        System.out.println("0 -> 6 : " + usssPath.dis(6));
    }
}
