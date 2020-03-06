package top.vergessen.hamiltonloop;

import top.vergessen.graph.Graph;

import java.util.ArrayList;
import java.util.Collections;

public class HamiltonLoop {

    private Graph G;
    private boolean[] visited;
    private int[] pre;
    private int end;

    public HamiltonLoop(Graph G){
        this.G = G;
        visited = new boolean[G.getV()];
        pre = new int[G.getV()];
        end = -1;

        dfs(0,0,G.getV());
    }

    // 深度优先遍历的值,节点的父节点，剩余多少节点未遍历
    private boolean dfs(int v, int parent, int left){

        visited[v] = true;
        pre[v] = parent;
        left--;
        if (left == 0 && G.hasEdge(v, 0)){
            end = v;
            return true;
        }

        for (int w : G.adj(v))
            if (!visited[w]) {
                if (dfs(w, v,left)) return true;
            }
        visited[v] = false;
        return false;
    }

    private boolean allVisited() {
        for (int v = 0; v < G.getV(); v++){
            if (!visited[v])
                return false;
        }
        return true;
    }

    public ArrayList<Integer> result(){
        ArrayList<Integer> res = new ArrayList<>();
        if (end == -1) return res;
        int cur = end;
        while (cur != 0){
            res.add(cur);
            cur = pre[cur];
        }
        res.add(0);
        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args) {
        Graph graph = new Graph("src/main/g4.txt");
        HamiltonLoop hamiltonLoop = new HamiltonLoop(graph);
        System.out.println(hamiltonLoop.result());
    }
}
