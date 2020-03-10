package top.vergessen.digraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

// 有向图欧拉回路
public class DirectedEulerLoop {

    public Graph G;

    public DirectedEulerLoop(Graph G){
        if (!G.isDirected())
            throw new IllegalArgumentException(
                    "DirectedEulerLoop only works in directed graph");
        this.G = G;
    }

    // 判断图中是否存在欧拉回路
    public boolean hasEulerLoop(){

//        CC cc = new CC(G);
//        if (cc.count() > 1) return false;

        for (int v = 0; v < G.getV(); v++){
            if (G.indegree(v) != G.outdegree(v)) return false;
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
            if (g.outdegree(curv) != 0){
                stack.push(curv);
                int w = g.adj(curv).iterator().next();
                g.removeEdge(curv,w);
                curv = w;
            }else {
                res.add(curv);
                curv = stack.pop();
            }
        }
        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args) {
        Graph graph = new Graph("src/main/ug.txt",true);
        DirectedEulerLoop directedEulerLoop = new DirectedEulerLoop(graph);
        System.out.println(directedEulerLoop.result());

        Graph graph2 = new Graph("src/main/ug3.txt",true);
        DirectedEulerLoop directedEulerLoop2 = new DirectedEulerLoop(graph2);
        System.out.println(directedEulerLoop2.result());
    }
}
