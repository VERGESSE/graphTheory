package top.vergessen.kruskal;

import top.vergessen.weight.WeightGraph;

import java.util.ArrayList;
import java.util.Collections;

// Kruskal 算法求解最小生成树   时间复杂度O(ElogE)
public class Kruskal {

    private WeightGraph G;
    private ArrayList<WeightEdge> mst;

    public Kruskal(WeightGraph G){

        this.G = G;
        mst = new ArrayList<>();

        WeightCC cc = new WeightCC(G);
        if (cc.count() > 1) return;

        //Kruskal
        ArrayList<WeightEdge> edges = new ArrayList<>();
        for (int v = 0; v < G.getV(); v++)
            for (int w : G.adj(v))
                if (v < w)
                    edges.add(new WeightEdge(v,w,G.getWeight(v,w)));
        Collections.sort(edges);

        UF uf = new UF(G.getV());
        for (WeightEdge edge : edges) {
            int v = edge.getV();
            int w = edge.getW();
            if (!uf.isConnected(v,w)){
                mst.add(edge);
                uf.unionElements(v,w);
            }
        }
    }

    public ArrayList<WeightEdge> result(){
        return mst;
    }

    public static void main(String[] args) {
        WeightGraph weightGraph = new WeightGraph("src/main/g7.txt");
        Kruskal kruskal = new Kruskal(weightGraph);
        System.out.println(kruskal.result());
    }
}
