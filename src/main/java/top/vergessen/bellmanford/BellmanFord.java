package top.vergessen.bellmanford;

import top.vergessen.weight.WeightGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// BellmanFord算法求解有负权边图的单源最短路径  时间复杂度O(V*E)
public class BellmanFord {

    private WeightGraph G;
    private int s;
    private int[] dis;
    private boolean hasNegativeCycle = false;
    private int[] pre;

    public BellmanFord(WeightGraph G,int s){

        this.G= G;
        G.validateVertex(s);

        this.s = s;
        dis = new int[G.getV()];
        Arrays.fill(dis,Integer.MAX_VALUE);
        dis[s] = 0;

        pre = new int[G.getV()];
        Arrays.fill(pre, -1);
        pre[s] = s;

        for (int pass = 1; pass < G.getV(); pass++){
            // 进行V-1轮松弛操作
            for (int v = 0; v < G.getV(); v++){
                for (int w : G.adj(v))
                    if (dis[v] != Integer.MAX_VALUE &&
                            dis[v] + G.getWeight(v,w) < dis[w]) {
                        dis[w] = dis[v] + G.getWeight(v, w);
                        pre[w] = v;
                    }
            }
        }

        // 判断是否存在负权环
        for (int v = 0; v < G.getV(); v++){
            for (int w : G.adj(v))
                if (dis[v] != Integer.MAX_VALUE &&
                        dis[v] + G.getWeight(v,w) < dis[w])
                    hasNegativeCycle = true;
        }
    }

    // 判断是否存在负权环，无向图只要有负权边就一定有负权环
    public boolean hasNeg(){
        return hasNegativeCycle;
    }

    public boolean isConnectTo(int v){
        G.validateVertex(v);
        return dis[v] != Integer.MAX_VALUE;
    }

    public int distTo(int v){
        G.validateVertex(v);
        if (hasNegativeCycle) throw new RuntimeException("exit negative cycle.");
        return dis[v];
    }

    // 返回当前原点到传入节点的最短路径
    public Iterable<Integer> path(int t){

        ArrayList<Integer> res = new ArrayList<>();
        if (!isConnectTo(t)) return res;

        int cur = t;
        while (cur != s){
            res.add(cur);
            cur = pre[cur];
        }
        res.add(s);

        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args) {

        WeightGraph g = new WeightGraph("src/main/g8.txt");
        BellmanFord bf = new BellmanFord(g,0);
        if (!bf.hasNeg()){
            for (int v = 0; v < g.getV(); v++)
                System.out.print(bf.distTo(v) + " ");
            System.out.println();

            System.out.println(bf.path(3));
        }else {
            System.out.println("exit negative cycle.");
        }
    }
}






























