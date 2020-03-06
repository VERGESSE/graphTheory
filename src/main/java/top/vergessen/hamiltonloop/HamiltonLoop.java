package top.vergessen.hamiltonloop;

import top.vergessen.graph.Graph;

import java.util.ArrayList;
import java.util.Collections;

public class HamiltonLoop {

    private Graph G;
//    private boolean[] visited;
    private int visited;       // 状态压缩
    private int[] pre;
    private int end;

    public HamiltonLoop(Graph G){
        this.G = G;
//        visited = new boolean[G.getV()];
        visited = 0;
        pre = new int[G.getV()];
        end = -1;

        dfs(0,0,G.getV());
    }

    // 深度优先遍历的值,节点的父节点，剩余多少节点未遍历
    private boolean dfs(int v, int parent, int left){

//        visited[v] = true;
        visited += (1 << v);
        pre[v] = parent;
        left--;
        if (left == 0 && G.hasEdge(v, 0)){
            end = v;
            return true;
        }

        for (int w : G.adj(v))
//            if (!visited[w]) {
            if ((visited & (1 << w)) == 0) {
                if (dfs(w, v,left)) return true;
            }
//        visited[v] = false;
        visited -= (1 << v);
        return false;
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
