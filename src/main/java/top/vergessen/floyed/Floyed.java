package top.vergessen.floyed;

import top.vergessen.weight.WeightGraph;

import java.util.Arrays;

// Floyed算法 求所有点对的最短路径 时间复杂度O(V*V*V)
public class Floyed {

    private WeightGraph G;
    private int[][] dis;
    private boolean hasNegativeCycle = false;

    public Floyed(WeightGraph G){

        this.G = G;

        dis = new int[G.getV()][G.getV()];
        for (int i = 0; i < G.getV(); i++){
            Arrays.fill(dis[i],Integer.MAX_VALUE);
        }

        for (int v = 0; v < G.getV(); v++){
            dis[v][v] = 0;
            for (int w : G.adj(v))
                dis[v][w] = G.getWeight(v,w);
        }

        // Floyed算法主体
        for (int t = 0; t < G.getV(); t++)
            for (int v = 0; v < G.getV(); v++)
                for (int w = 0; w < G.getV(); w++)
                    if (dis[v][t] != Integer.MAX_VALUE
                            && dis[t][w] != Integer.MAX_VALUE
                            &&dis[v][t] + dis[t][w] < dis[v][w])
                        dis[v][w] = dis[v][t] + dis[t][w];

        // 判断是否有负权环
        for (int v = 0; v < G.getV(); v++)
            if (dis[v][v] < 0) hasNegativeCycle = true;
    }

    public boolean hasNeg(){
        return hasNegativeCycle;
    }

    public boolean isConnectedTo(int v, int w){
        G.validateVertex(v);
        G.validateVertex(w);
        return dis[v][w] != Integer.MAX_VALUE;
    }

    public int disTo(int v, int w){
        G.validateVertex(v);
        G.validateVertex(w);
        return dis[v][w];
    }

    public static void main(String[] args) {
        WeightGraph g = new WeightGraph("src/main/g8.txt");
        Floyed floyed = new Floyed(g);
        if (!floyed.hasNegativeCycle){
            for (int v = 0; v < g.getV(); v++) {
                for (int w = 0; w < g.getV(); w++)
                    System.out.print(floyed.disTo(v,w) + " ");
                System.out.println();
            }
        }else
            System.out.println("exit negative cycle.");
    }
}















