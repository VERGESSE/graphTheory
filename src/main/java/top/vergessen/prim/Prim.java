package top.vergessen.prim;

import top.vergessen.kruskal.WeightCC;
import top.vergessen.kruskal.WeightEdge;
import top.vergessen.weight.WeightGraph;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

//优化 Prim算法求解最小生成树 时间复杂度O(ElogE)
public class Prim {

    private WeightGraph G;
    private ArrayList<WeightEdge> mst;

    public Prim(WeightGraph G){

        this.G = G;
        mst = new ArrayList<>();

        WeightCC cc = new WeightCC(G);
        if (cc.count() > 1) return;

        // Prim
        boolean[] visited = new boolean[G.getV()];
        visited[0] = true;
        Queue<WeightEdge> pq = new PriorityQueue<>();
        for (int w : G.adj(0))
            pq.add(new WeightEdge(0,w,G.getWeight(0,w)));
        while (!pq.isEmpty()){

            WeightEdge minEdge = pq.remove();
            if (visited[minEdge.getW()] && visited[minEdge.getV()])
                continue;
            mst.add(minEdge);

            int newV = visited[minEdge.getV()] ? minEdge.getW() : minEdge.getV();
            visited[newV] = true;
            for (int w : G.adj(newV))
                if (!visited[w])
                    pq.add(new WeightEdge(newV,w,G.getWeight(newV,w)));
        }
    }

    public ArrayList<WeightEdge> result(){
        return mst;
    }

    public static void main(String[] args) {
        WeightGraph weightGraph = new WeightGraph("src/main/g7.txt");
        Prim prim = new Prim(weightGraph);
        System.out.println(prim.result());
    }
}

// Prim算法求解最小生成树 时间复杂度O((V-1)*(V+E)) = O(VE) 高于Kruskal
class Prim0 {

    private WeightGraph G;
    private ArrayList<WeightEdge> mst;

    public Prim0(WeightGraph G){

        this.G = G;
        mst = new ArrayList<>();

        WeightCC cc = new WeightCC(G);
        if (cc.count() > 1) return;

        // Prim
        boolean[] visited = new boolean[G.getV()];
        visited[0] = true;
        for (int i = 1; i < G.getV(); i++){
            WeightEdge minEdge = new WeightEdge(-1,-1,Integer.MAX_VALUE);
            for (int v = 0; v < G.getV(); v++)
                if (visited[v])
                    for (int w: G.adj(v))
                        if (!visited[w] && G.getWeight(v,w) < minEdge.getWeight())
                            minEdge = new WeightEdge(v,w,G.getWeight(v,w));
            mst.add(minEdge);
            visited[minEdge.getV()] = true;
            visited[minEdge.getW()] = true;
        }
    }

    public ArrayList<WeightEdge> result(){
        return mst;
    }
}
