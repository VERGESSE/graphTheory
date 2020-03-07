package top.vergessen.eulerloop;

import top.vergessen.graph.Graph;

import java.util.ArrayList;
import java.util.Stack;

public class EulerLoop {

    public Graph G;

    public EulerLoop(Graph G){
        this.G = G;
    }

    // 判断图中是否存在欧拉回路
    public boolean hasEulerLoop(){

        CC cc = new CC(G);
        if (cc.count() > 1) return false;

        for (int v = 0; v < G.getV(); v++){
            if (G.degree(v) % 2 == 1) return false;
        }
        return true;
    }

    // Hierholzer 算法
    public ArrayList<Integer> result(){

        ArrayList<Integer> res = new ArrayList<>();
        if (!hasEulerLoop()) return res;

        Graph g = (Graph) G.clone();
        Stack<Integer> stack = new Stack<>();
        int curv = 0;
        stack.push(curv);
        while (!stack.isEmpty()){
            if (g.degree(curv) != 0){
                stack.push(curv);
                int w = g.adj(curv).iterator().next();
                g.removeEdge(curv,w);
                curv = w;
            }else {
                res.add(curv);
                curv = stack.pop();
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Graph graph = new Graph("src/main/g5.txt");
        EulerLoop eulerLoop = new EulerLoop(graph);
        System.out.println(eulerLoop.result());

        Graph graph2 = new Graph("src/main/g6.txt");
        EulerLoop eulerLoop2 = new EulerLoop(graph2);
        System.out.println(eulerLoop2.result());
    }
}
