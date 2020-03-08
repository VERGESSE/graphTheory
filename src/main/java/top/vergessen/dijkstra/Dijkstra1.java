package top.vergessen.dijkstra;

import top.vergessen.weight.WeightGraph;

import java.util.Arrays;

// Dijkstra算法  时间复杂度O(V^2)
public class Dijkstra1 {

    private WeightGraph G;
    private int s;
    private int[] dis;    // 当前节点距离原点的距离
    private boolean[] visited;

    private Dijkstra1(WeightGraph G, int s){

        this.G = G;

        G.validateVertex(s);
        this.s = s;

        dis = new int[G.getV()];
        Arrays.fill(dis,Integer.MAX_VALUE);
        dis[s] = 0;

        visited = new boolean[G.getV()];

        while (true){

            // 寻找当前距离原点距离最小的节点，因为没有负权边
            // 当前节点到原点的距离已经最小
            int curdis = Integer.MAX_VALUE, cur = -1;
            for (int v = 0; v < G.getV(); v++){
                if (!visited[v] && dis[v] < curdis){
                    curdis = dis[v];
                    cur = v;
                }
            }

            if (cur == -1) break;

            visited[cur] = true;
            // 更新与当前距离原点最短节点相邻的所有节点距离的dis
            for (int w : G.adj(cur)){
                if (!visited[w])
                    dis[w] =Math.min(dis[cur] + G.getWeight(cur,w), dis[w]);
            }
        }
    }

    // 判断当前节点是否与图联通
    public boolean isConnected(int v){
        G.validateVertex(v);
        return visited[v];
    }

    // 返回传入节点与原点的最短距离
    public int disTo(int v){
        G.validateVertex(v);
        return dis[v];
    }

    public static void main(String[] args) {
        WeightGraph weightGraph = new WeightGraph("src/main/g8.txt");
        Dijkstra1 dijkstra = new Dijkstra1(weightGraph,0);
        for (int v = 0; v < weightGraph.getV(); v++)
            System.out.print(dijkstra.disTo(v) + " ");
        System.out.println();
    }
}
